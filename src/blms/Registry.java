package blms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.util.calendar.BaseCalendar.Date;

import blms.exceptions.BlmsException;

// Invariant: there aren't two users with the same email address. 
public class Registry {
	Collection<User> users;
	Collection<League> leagues;
	
	Map<String, Object> idToObj;
	Map<Object, String> objToId;
	Map<String, Date> datesMap;
	Map<String, Integer> handicaps;
	Map<League, LinkedList<User>> leaguesMap;
	long nextId = 0;

	public Registry() {
		users = new LinkedList<User>();
		leagues = new LinkedList<League>();
		
		idToObj = new HashMap<String, Object>();
		objToId = new HashMap<Object, String>();
		datesMap = new HashMap<String, Date>();
		handicaps = new HashMap<String, Integer>();
		leaguesMap = new HashMap<League, LinkedList<User>>();
	}
	
	private String insertIntoTables(Object obj) {
		String s = "" + nextId;
		idToObj.put(s, obj);
		objToId.put(obj, s);
		nextId++;
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

	public User createUser(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, String picture) throws BlmsException {
		User user = new User(firstName, lastName, homePhone, workPhone, cellPhone, email, picture);
		if (users.contains(user))
			throw new BlmsException("User with this email exists");
		users.add(user);
		insertIntoTables(user);
		return user;
	}

	public void deleteUser(User user) {
		users.remove(user);
		removeFromTables(user);
	}
	
	// --------------------------------------
	
	public League createLeague(String name, User operator) throws BlmsException {
		League league = new League(name, operator);
		if (leagues.contains(league))
			throw new BlmsException("This league already exists");
		leagues.add(league);
		LinkedList<User> usersList = new LinkedList<User>();
		usersList.add(operator);
		leaguesMap.put(league, usersList);
		insertIntoTables(league);
		return league;
	}
	
	// --------------------------------------
	
	public String getUserLeagues(String userId) throws Exception{
		String stringLeagues = "";
		User user = (User) this.getObject(userId);
		if (user == null)
			throw new BlmsException("Required data: league user");
		for (Iterator it = leagues.iterator(); it.hasNext(); ){
			League league = (League)it.next();
			LinkedList<User> usersList = leaguesMap.get(league);
			if (usersList.contains(user)){
				if (stringLeagues.length() > 0){
					stringLeagues += ", " + league.name;
				} else stringLeagues += league.name;
			}
		}		
		return stringLeagues;
	}
	
	public String getLeagueUsers(String leagueId) throws Exception{
		if (Util.isNullOrEmpty(leagueId))
			throw new BlmsException("Required data: league id");
		String stringMembers = "";
		League league = (League) this.getObject(leagueId);
		LinkedList<User> usersList = leaguesMap.get(league);
		for (Iterator it = usersList.iterator(); it.hasNext(); ){
			User user = (User) it.next();
			String userId = this.getId(user);
			if (stringMembers.length() > 0){
				stringMembers += ", " + user.lastName;
			} else stringMembers += user.lastName;				
			
		}		
		return stringMembers;
	}
	
	public void userJoinLeague(String idUser, String leagueId, int initialHandicap) throws Exception{
		User user = (User) this.getObject(idUser);
		League league = (League) this.getObject(leagueId);
		LinkedList<User> usersList = new LinkedList<User>();
		//Date joinDate = new
		if (!leaguesMap.containsKey(league)){
			throw new BlmsException("This league was not created!");
		}
		usersList = leaguesMap.get(league);
		usersList.add(user);
		
		leaguesMap.put(league, usersList);
		handicaps.put(league.name+user.email, initialHandicap);
	}
	
	public boolean isUserLeague(String userId, String leagueId) throws BlmsException {
		if (Util.isNullOrEmpty(leagueId))
			throw new BlmsException("Required: league");
		if (userId == null)
			throw new BlmsException("Required data: league operator");
		User user = (User) this.getObject(userId);
		League league = (League) this.getObject(leagueId);
		
		return (leaguesMap.get(league)).contains(user);
	}
	
	public void changeAttribute(Object target, String attribute, String value)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, BlmsException {
		Class clas = target.getClass();
		String s = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
		Method met = clas.getMethod("set" + s, new Class[] {String.class});
		
		if (clas == User.class && attribute.equals("email")) {
			for (User u : users)
				if (u.email.equals(value) && !u.equals(target))
					throw new BlmsException("User with this email exists");
		}
		
		met.invoke(target, new Object[] {value});
	}
	
	public Object getAttribute(Object target, String id, String attribute) 
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Class clas = target.getClass();
		String s = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
		Method met = clas.getMethod("get" + s, new Class[] {});
		return met.invoke(target, new Object[] {});
	}

	public void removeAllUsers() {
		for (User u : users)
			removeFromTables(u);
		users.clear();
	}

	// TODO: remove seasons, win/loss...
	public void removeAllLeagues() {
		for (League l : leagues)
			removeFromTables(l);
		leagues.clear();
		
	}
}
