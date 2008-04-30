package blms;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import blms.exceptions.BlmsException;

import com.db4o.Db4o;
import com.db4o.ObjectSet;
import com.db4o.ext.ExtObjectContainer;

// Invariant: there aren't two users with the same email address. 
public class Registry {
	BlmsDataStore store;
	String databaseName;

	ExtObjectContainer db = null;

	public Registry() {
		Db4o.configure().updateDepth(20);
	}

	public void useDatabase(String databaseName) {
		if (db != null && !db.isClosed())
			db.close();
		db = (ExtObjectContainer) Db4o.openFile(databaseName);
		store = new BlmsDataStore();
		ObjectSet<BlmsDataStore> result = db.get(BlmsDataStore.class);
		if (result.isEmpty()) {
			store = new BlmsDataStore();
			db.set(store);
		} 
		else
			store = result.next();
	}

	private void updateDb() {
		if (db != null && !db.isClosed())
			db.set(store);
	}

	/**
	 * Returns the object associated with the given ID.
	 * @param id the ID of object (as returned by 
	 * {@link Registry#getId(Object)}).
	 * @return the object associated with the given ID or <code>null</code>, 
	 *         if no object is associated with the given ID.
	 */
	public Object getObject(String id) {
		try {
			long longId = Long.parseLong(id);
			return db.getByID(longId);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	// TODO: should we use UUID?
	/**
	 * Returns the ID for a given object.
	 * @param object the object.
	 * @return the ID.
	 */
	public String getId(Object object) {
		long id = db.getID(object);
		if (id != 0)
			return "" + id;
		else
			return null;
	}

	public User[] findUserByLastName(String lastName) {
		Pattern pat = Pattern.compile(lastName, Pattern.CASE_INSENSITIVE);
		Collection<User> ret = new LinkedList<User>();
		for (User user : store.users) {
			Matcher m = pat.matcher(user.getLastName());
			if (m.matches())
				ret.add(user);
		}
		return ret.toArray(new User[] {});
	}

	public League[] findLeague(String name) {
		Pattern pat = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
		TreeSet<League> ret = new TreeSet<League>();
		for (League league : store.leagues) {
			Matcher m = pat.matcher(league.getName());
			if (m.matches())
				ret.add(league);
		}
		return ret.toArray(new League[] {});
	}

	public User createUser(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, String picture)
			throws BlmsException {
		User user = new User(firstName, lastName, homePhone, workPhone,
				cellPhone, email, picture);
		if (store.users.contains(user))
			throw new BlmsException("User with this email exists");
		store.users.add(user);
		updateDb();
		return user;
	}

	public void deleteUser(User user) throws BlmsException {
		for (League l : store.leagues)
			if (l.getOperator() == user)
				throw new BlmsException("Cannot remove league operator");
		LinkedList<Join> auxJoins = new LinkedList<Join>();
		for (Join j : store.joins) {
			if ((j.user).equals(user)) {
				auxJoins.add(j);
			}
		}
		store.joins.removeAll(auxJoins);
		store.users.remove(user);
		updateDb();
	}

	public void deleteLeague(League league) {
		store.leagues.remove(league);
		for (Match m : league.getMatches())
			deleteMatch(m);
		LinkedList<Join> auxJoins = new LinkedList<Join>();
		for (Join j : store.joins) {
			if ((j.league).equals(league))
				auxJoins.add(j);
		}
		store.joins.removeAll(auxJoins);
		updateDb();
	}

	// --------------------------------------

	public League createLeague(String name, User operator) throws BlmsException {
		League league = new League(name, operator);

		if (store.leagues.contains(league))
			throw new BlmsException("This league already exists");
		Join join = new Join(operator, league, 0);
		store.leagues.add(league);
		store.joins.add(join);
		updateDb();
		return league;
	}

	// --------------------------------------

	public String getUserLeagues(User user) throws Exception {
		String stringLeagues = "";

		if (user == null)
			throw new BlmsException("Unknown user");
		for (Join join : store.joins) {
			User userJoin = join.user;
			if (user.equals(userJoin)) {
				if (stringLeagues.length() > 0) {
					stringLeagues += ", " + (join.league).name;
				} else
					stringLeagues += (join.league).name;
			}
		}
		return stringLeagues;
	}

	public String getLeagueUsers(League league) throws Exception {
		if (league == null)
			throw new BlmsException("Unknown league");
		String stringMembers = "";

		for (Join join : store.joins) {
			League leagueJoin = join.league;
			if (league.equals(leagueJoin)) {
				if (stringMembers.length() > 0) {
					stringMembers += ", " + (join.user).lastName;
				} else
					stringMembers += (join.user).lastName;
			}
		}
		return stringMembers;
	}

	public void userJoinLeague(User user, League league, String initialHandicap)
			throws Exception {
		if (user == null) {
			throw new BlmsException("Unknown user");
		}
		if (league == null) {
			throw new BlmsException("Unknown league");
		}
		if (initialHandicap == null || initialHandicap.equals("")) {
			throw new BlmsException("Must provide initial player handicap");
		}
		int handicap = Integer.parseInt(initialHandicap);
		if (handicap < 0) {
			throw new BlmsException("Handicap cant be negative");
		}
		Join existentJoin = findJoin(user, league);
		if (existentJoin == null) {
			Join join = new Join(user, league, handicap);
			store.joins.add(join);
			updateDb();
		} else
			throw new BlmsException("User is already a league member");
	}

	public void userLeaveLeague(User user, League league) throws Exception {
		if (user == null) {
			throw new BlmsException("Unknown user");
		}
		if (league == null) {
			throw new BlmsException("Unknown league");
		}
		if (!this.isUserLeague(user, league)) {
			throw new BlmsException("User is not a league member");
		}
		if ((league.operator).equals(user)) {
			throw new BlmsException("Operator cannot leave league");
		}
		Join join = findJoin(user, league);
		if (join != null) {
			store.joins.remove(join);
		} else
			throw new BlmsException("User/league is null");
		store.leagues.remove(league);
		
		updateDb();
	}

	public boolean isUserLeague(User user, League league) throws BlmsException {
		if (league == null)
			throw new BlmsException("Unknown league");
		if (user == null)
			throw new BlmsException("Unknown user");
		Join join = new Join(user, league, 200);
		for (Join auxJoin : store.joins)
			if (join.equals(auxJoin))
				return true;
		return false;
	}

	/**
	 * Changes an attribute by calling on the target object a setter method
	 * which takes one parameter of the type valueType.
	 * Example: <code>changeAttribute(obj, "name", "John", String.class)</code>
	 * will call <code>obj.setName("John")</code> (provided that the method
	 * exists and receives one String parameter). Currently this method handles
	 * User and League objects.
	 * 
	 * @param target the target object.
	 * @param attribute the attribute to change.
	 * @param value the new value of the attribute.
	 * @param valueType the type of the new value.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws BlmsException if the attribute can't be changed without
	 * violating constraints of the target object.
	 */
	public void changeAttribute(Object target, String attribute, Object value,
			Class valueType) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, BlmsException {
		Class targetClass = target.getClass();
		String s = attribute.substring(0, 1).toUpperCase()
				+ attribute.substring(1);
		Method met = targetClass
				.getMethod("set" + s, new Class[] { valueType });

		// XXX: this code is redundant with code in equals.
		if (targetClass == User.class && attribute.equals("email")) {
			for (User u : store.users)
				if (u.getEmail().equalsIgnoreCase((String)value) && !u.equals(target))
					throw new BlmsException("User with this email exists");
		}

		if (targetClass == League.class && attribute.equals("name")) {
			for (League l : store.leagues)
				if (l.getName().equalsIgnoreCase((String)value) && !l.equals(target))
					throw new BlmsException("This league already exists");
		}

		met.invoke(target, new Object[] { value });
		
		updateDb();
	}

	/**
	 * Returns the value of the given attribute for the given target object.
	 * Example: <code>getAttribute(obj, "name")</code> will return the value
	 * of <code>obj.getName()</code> (provided that the method exists).
	 * 
	 * @param target the object to get the attribute from.
	 * @param attribute the name of the attribute to get.
	 * @return the value of the given attribute for the given target object.
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public Object getAttribute(Object target, String attribute)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Class targetClass = target.getClass();
		String s = attribute.substring(0, 1).toUpperCase()
				+ attribute.substring(1);
		Method met = targetClass.getMethod("get" + s, new Class[] {});
		Method[] methods = targetClass.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if ((methods[i].getName()).equals(met.getName())) {
				return met.invoke(target, new Object[] {});
			}
		}
		return null;
	}

	public void removeAllUsers() {
		store.users.clear();
		updateDb();
	}

	public void removeAllLeagues() {
		// for (League l : store.leagues)
		// for (Match m : l.getMatches())
		// deleteMatch(m);
		store.leagues.clear();
		store.matches.clear();
		store.joins.clear();
		updateDb();

	}

	public void removeAllMatches() {
		store.matches.clear();
		for (User u : store.users)
			u.matches.clear();
		for (League l : store.leagues)
			l.matches.clear();

		updateDb();
	}

	public Match addMatchResult(League league, Date date, User winner,
			User loser) throws BlmsException {
		return addMatchResult(league, date, winner, loser, Match.UNDEFINED,
				Match.UNDEFINED, Match.UNDEFINED, Match.UNDEFINED);
	}

	public Match addMatchResult(League league, Date date, User winner,
			User loser, int length, int score, int longestRunForWinner,
			int longestRunForLoser) throws BlmsException {
		Match m = new Match(league, date, winner, loser, length, score,
				longestRunForWinner, longestRunForLoser);

		league.addMatch(m);
		winner.addMatch(m);
		loser.addMatch(m);

		store.matches.add(m);
		updateDb();

		return m;
	}

	public Join findJoin(User user, League league) throws BlmsException {
		if (user == null) {
			throw new BlmsException("Unknown user");
		}
		if (league == null) {
			throw new BlmsException("Unknown league");
		}
		for (Join join : store.joins)
			if ((join.league).equals(league) && (join.user).equals(user))
				return join;

		return null;
	}

	public void deleteMatch(Match m) {
		store.matches.remove(m);
		m.getLeague().matches.remove(m);
		m.getWinner().matches.remove(m);
		m.getLoser().matches.remove(m);
		updateDb();
	}

	public void updateMatchResult(Match match, Date date, User winner,
			User loser, int length, int score, int longestRunForWinner,
			int longestRunForLoser) throws BlmsException {
		User oldLoser = match.getLoser();
		User oldWinner = match.getWinner();
		
		if (loser != oldLoser) {
			oldLoser.removeMatch(match);
			loser.addMatch(match);
		}
		if (winner != oldWinner) {
			oldWinner.removeMatch(match);
			winner.addMatch(match);
		}
		
		match.update(date, winner, loser, length, score, longestRunForWinner,
				longestRunForLoser);

		updateDb();
	}
}
