
package blms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;

import blms.exceptions.BlmsException;

// Invariant: there aren't two users with the same email address. 
public class Registry {
	Collection<User> users;
	Collection<League> leagues;
	Collection<Match> matches;
	Collection<Join> joins;
	
	Map<String, Object> idToObj;
	Map<Object, String> objToId;
	
	long nextId = 0;
	ObjectContainer db = null;

	public Registry() {
//		db = Db4o.openFile(database);
		
		users = new LinkedList<User>();
		leagues = new LinkedList<League>();
		matches = new LinkedList<Match>();
		joins = new LinkedList<Join>(); 
		
		idToObj = new HashMap<String, Object>();
		objToId = new HashMap<Object, String>();
	}
	
	public void useDatabase(String databaseName) {
		if (db != null)
			db.close();
		db = Db4o.openFile(databaseName);
	}
	
	private String insertIntoTables(Object obj) {
		String s = "" + nextId;
		idToObj.put(s, obj);
		objToId.put(obj, s);
		nextId++;
//		System.out.printf("---Object %s has id %s.\n", obj, getId(obj));
		return s;
	}
	
	private void removeFromTables(Object obj) {
		idToObj.remove(objToId.get(obj));
		objToId.remove(obj);
	}
	
	public Object getObject(String id) {
		return idToObj.get(id);
	}
	
	public String getId(Object o) {
		return objToId.get(o);
	}
	
	public User[] findUserByLastName(String lastName) {
		Pattern pat = Pattern.compile(lastName, Pattern.CASE_INSENSITIVE);
		Collection<User> ret = new LinkedList<User>();
		for (User user : users) {
			Matcher m = pat.matcher(user.getLastName());
			if (m.matches())
				ret.add(user);
		}
		return ret.toArray(new User[] {});
	}
	
	public League[] findLeague(String name) {
		Pattern pat = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
		TreeSet<League> ret = new TreeSet<League>();
		for (League league : leagues) {
			Matcher m = pat.matcher(league.getName());
			if (m.matches())
				ret.add(league);
		}
		return ret.toArray(new League[] {});
	}

	public User createUser(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, String picture) throws BlmsException {
		User user = new User(firstName, lastName, homePhone, workPhone, cellPhone, email, picture);
		if (users.contains(user))
			throw new BlmsException("User with this email exists");
		users.add(user);
		insertIntoTables(user);
		return user;
	}

	public void deleteUser(User user) throws BlmsException {
		for (League l : leagues)
			if (l.getOperator() == user)
				throw new BlmsException("Cannot remove league operator");
		LinkedList<Join> auxJoins = new LinkedList<Join>();
		for (Join j : joins){
			if ((j.user).equals(user)){
				auxJoins.add(j);
				removeFromTables(j);
			}
		}
		joins.removeAll(auxJoins);
		users.remove(user);
		removeFromTables(user);
	}
	
	public void deleteLeague(League league) {
		leagues.remove(league);
		removeFromTables(league);
		for (Match m : league.getMatches())
			deleteMatch(m);
		LinkedList<Join> auxJoins = new LinkedList<Join>();
		for (Join j : joins){
			if ((j.league).equals(league)){
				removeFromTables(j);
				auxJoins.add(j);
			}
		}
		joins.removeAll(auxJoins);
	}
	
	// --------------------------------------
	
	public League createLeague(String name, User operator) throws BlmsException {
		League league = new League(name, operator);
		
		if (leagues.contains(league))
			throw new BlmsException("This league already exists");
		Join join = new Join(operator, league, 0);
		leagues.add(league);
		joins.add(join);
		insertIntoTables(league);
		insertIntoTables(join);
		return league;
	}
	
	// --------------------------------------
	
	public String getUserLeagues(User user) throws Exception{
		String stringLeagues = "";
		
		if (user == null)
			throw new BlmsException("Unknown user");
		for (Iterator it = joins.iterator(); it.hasNext(); ){
			Join join = (Join)it.next();
			User userJoin = join.user;
			if (user.equals(userJoin)){
				if (stringLeagues.length() > 0){
					stringLeagues += ", " + (join.league).name;
				} else stringLeagues += (join.league).name;
			}
		}		
		return stringLeagues;
	}
	
	public String getLeagueUsers(League league) throws Exception{
		if (league == null)
			throw new BlmsException("Unknown league");
		String stringMembers = "";
		
		for (Iterator it = joins.iterator(); it.hasNext(); ){
			Join join = (Join) it.next();
			League leagueJoin = join.league;
			if (league.equals(leagueJoin)){
				if (stringMembers.length() > 0){
					stringMembers += ", " + (join.user).lastName;
				} else stringMembers += (join.user).lastName;				
			}
		}		
		return stringMembers;
	}
	
	public void userJoinLeague(User user, League league, String initialHandicap) throws Exception{
		if (user == null){
			throw new BlmsException("Unknown user");
		}
		if (league == null){
			throw new BlmsException("Unknown league");
		}
		if (initialHandicap == null || initialHandicap.equals("")){
			throw new BlmsException("Must provide initial player handicap");
		}
		int handicap = Integer.parseInt(initialHandicap);
		if (handicap < 0){
			throw new BlmsException("Handicap cant be negative");
		}
		Join existentJoin = findJoin(user, league);
		if (existentJoin == null){
			Join join = new Join(user, league, handicap);
			joins.add(join);
			insertIntoTables(join);
		} else throw new BlmsException("User is already a league member");
	}
	
	public void userLeaveLeague(User user, League league) throws Exception{
		if (user == null){
			throw new BlmsException("Unknown user");
		}
		if (league == null){
			throw new BlmsException("Unknown league");
		}
		if (!this.isUserLeague(user, league)){
			throw new BlmsException("User is not a league member");
		}
		if ((league.operator).equals(user)){
			throw new BlmsException("Operator cannot leave league");
		}
		Join join = findJoin(user, league);
		if (join != null){
			joins.remove(join);
		} else throw new BlmsException("User/league is null");
		leagues.remove(league);
	}
	
	public boolean isUserLeague(User user, League league) throws BlmsException {
		if (league == null)
			throw new BlmsException("Unknown league");
		if (user == null)
			throw new BlmsException("Unknown user");
		Join join = new Join(user, league, 200);
		for (Iterator it = joins.iterator(); it.hasNext(); ){
			Join auxJoin = (Join) it.next();
			if (join.equals(auxJoin)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Changes an attribute by calling on the target object a setter method
	 * which takes one parameter of the type valueType 
	 * @param target
	 * @param attribute
	 * @param value
	 * @param valueType
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws BlmsException
	 */
	public void changeAttribute(Object target, String attribute, Object value, Class valueType)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, BlmsException {
		//Class parameterClass = (value == null) ? String.class : value.getClass();
		Class clas = target.getClass();
		String s = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
		Method met = clas.getMethod("set" + s, new Class[] {valueType});
		
		/* TODO: generalize. Each class must define an unique attribute which
		   is to be checked */
		if (clas == User.class && attribute.equals("email")) {
			for (User u : users)
				if (u.getEmail().equals(value) && !u.equals(target))
					throw new BlmsException("User with this email exists");
		}
		
		if (clas == League.class && attribute.equals("name")) {
			for (League l : leagues)
				if (l.getName().equals(value) && !l.equals(target))
					throw new BlmsException("This league already exists");
		}
		
		met.invoke(target, new Object[] {value});
	}
	
	public Object getAttribute(Object target, String id, String attribute) 
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Class clas = target.getClass();
		String s = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
		Method met = clas.getMethod("get" + s, new Class[] {});
		Method[] methods = clas.getMethods();
		for (int i = 0; i < methods.length; i++){
			if ((methods[i].getName()).equals(met.getName())){
				return met.invoke(target, new Object[] {});
			}
		}
		return null; 
	}

	public void removeAllUsers() {
		for (User u : users)
			removeFromTables(u);
		users.clear();
	}

	// TODO: remove seasons, win/loss...
	public void removeAllLeagues() {
		for (League l : leagues) {
			removeFromTables(l);
			for (Match m : l.getMatches())
				deleteMatch(m);
		}
		for (Join j : joins){
			removeFromTables(j);
		}
		leagues.clear();
		joins.clear();
		
	}

	public Match addMatchResult(League league, Date date,
			User winner, User loser) throws BlmsException {
		return addMatchResult(league, date, winner, loser, Match.UNDEFINED,
				Match.UNDEFINED, Match.UNDEFINED, Match.UNDEFINED);
	}

	public Match addMatchResult(League league, Date date,
			User winner, User loser, int length, int score,
			int longestRunForWinner, int longestRunForLoser) 
			throws BlmsException {
		Match m = new Match(league, date, winner, loser, length,
				score, longestRunForWinner, longestRunForLoser);
		
		league.addMatch(m);
		winner.addMatch(m);
		loser.addMatch(m);
		
		matches.add(m);
		insertIntoTables(m);
		
		return m;
	}


	public Join findJoin(User user, League league) throws BlmsException {
		if (user == null){
			throw new BlmsException("Unknown user");
		}
		if (league == null){
			throw new BlmsException("Unknown league");
		}
		for (Iterator it = joins.iterator(); it.hasNext(); ){
			Join join = (Join) it.next();
			if ((join.league).equals(league) && (join.user).equals(user)){
				return join;
			}
		}
		return null;
	}

	public void removeAllMatches() {
		for (Match m : matches)
			removeFromTables(m);
		matches.clear();
		for (User u : users)
			u.matches.clear();
		for (League l : leagues)
			l.matches.clear();
//			matches.clear();
//		{
//			m.getLeague().matches.remove(m);
//			m.getWinner().matches.remove(m);
//			m.getWinner().matches.remove(m);
//			removeFromTables(m);
//		}
//		matches.clear();
	}


	// TODO: verify
	public void deleteMatch(Match m) {
		matches.remove(m);
		m.getLeague().matches.remove(m);
		m.getWinner().matches.remove(m);
		m.getLoser().matches.remove(m);
		removeFromTables(m);
	}


	public void updateMatchResult(Match match, Date date, User winner,
			User loser, int length, int score,
			int longestRunForWinner, int longestRunForLoser) throws BlmsException {
		match.update(date, winner, loser, length, score, longestRunForWinner, longestRunForLoser);
	}
}
