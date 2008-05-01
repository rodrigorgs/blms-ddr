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
/**
 * Handle object collections and access the database. 
 */
// Invariant: there aren't two users with the same email address. 
public class Registry {
	BlmsDataStore store;
	String databaseName;

	ExtObjectContainer db = null;

	/**
	 * Constructor default.
	 */
	public Registry() {
		Db4o.configure().updateDepth(20);
	}

	/**
	 * Load a database in memory. (can change in future: loading directly in DB) 
	 * @param databaseName the database name.
	 */
        public void closeDatabase() {
            if (db != null && !db.isClosed())
                db.close();
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

	/**
	 * Updates the DB.
	 */
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

	/**
	 * Finds users with the last name given.
	 * @param lastName the last name of user (s).
	 * @return the users list (possibly empty).
	 */
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

	/**
	 * Finds a league with the given name .
	 * @param name the name of league (s).
	 * @return the leagues list (possibly empty).
	 */
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


	/**
	* Creates a user with the given parameters. At least one phone number
	* must be provided. The parameters firstName, lastName and email are
	* mandatory.
	* @param firstName user's first name. Mandatory.
	* @param lastName user's last name. Mandatory.
	* @param homePhone user's home phone.
	* @param workPhone user's work phone.
	* @param cellPhone user's cell phone.
	* @param email user's email address. Mandatory.
	* @param picture user's picture.
	* @return a user
	* @throws BlmsException if a mandatory field is missing or if no
	* phone number is provided.
	*/	 
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

	/**
	 * Deletes the given user.
	 * @param user the user to be deleted.
	 * @throws BlmsException if the user doesn't exist in the leagues. 
	 */
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

	/**
	 * Deletes the given league.
	 * @param league the league to be deleted.
	 */
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

	/**
	 * Creates a league with the given name and the given operator. The creation
	 * date is recorded.
	 * 
	 * @param name
	 *            the league's name.
	 * @param operator
	 *            user that operates this league.
	 * @return league a league
	 * @throws BlmsException
	 *             if name is null or empty or if operator is null.
	 */
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

	/**
	 * Gets a String with the leagues which the user joined. 
	 * @param user
	 *            user that joined in the leagues returned.
	 * @return stringLeagues a list with the leagues' names which the user joined.
	 * @throws BlmsException
	 *             if user is null. 
	 */
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

	/**
	 * Gets a String with the users of the given league.
	 * @param league the given league.
	 * @return a list with users' names of the given league.
	 * @throws Exception if the given league is null.
	 */
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

	/**
	 * Joins the user in the league, with the initial handicap.
	 * @param user the given user.
	 * @param league the given league.
	 * @param initialHandicap the given initial handicap.
	 * @throws Exception if the user is null, or the league is null, or the initial 
	 *         handicap is null or negative.
	 */
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

	/**
	 * Removes the given user of the given league (the user leaves the league).
	 * @param user the given user.
	 * @param league the given league.
	 * @throws Exception if the user is null or the league is null, or if the league doesn't
	 *         contain the user, or if the user is the league's operator.
	 */
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

	/**
	 * Verify if the given user is a member of the league.
	 * @param user the given user.
	 * @param league the given league.
	 * @return true if the user is a member of the league or false, otherwise.
	 * @throws BlmsException if the league or the user are null.
	 */
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

	/**
	 * Removes all users from DB (in this case, persistence file).
	 */
	public void removeAllUsers() {
		store.users.clear();
		updateDb();
	}

	/**
	 * Removes all leagues from DB (in this case, persistence file).
	 */
	public void removeAllLeagues() {
		// for (League l : store.leagues)
		// for (Match m : l.getMatches())
		// deleteMatch(m);
		store.leagues.clear();
		store.matches.clear();
		store.joins.clear();
		updateDb();
	}

	/**
	 * Removes all matches from DB (in this case, persistence file)
	 */
	public void removeAllMatches() {
		store.matches.clear();
		for (User u : store.users)
			u.matches.clear();
		for (League l : store.leagues)
			l.matches.clear();

		updateDb();
	}

	/**
	 * Adds a result of a match.
	 * @param league the league which the match happened.
	 * @param date the date of the match.
	 * @param winner the match's winner.
	 * @param loser the match's loser.
	 * @return a match.
	 * @throws BlmsException see {@link Match#Match(League, Date, User, User, int, int, int, int)}. 
	 */
	public Match addMatchResult(League league, Date date, User winner,
			User loser) throws BlmsException {
		return addMatchResult(league, date, winner, loser, Match.UNDEFINED,
				Match.UNDEFINED, Match.UNDEFINED, Match.UNDEFINED);
	}

	/**
	 * see {@link Match#Match(League, Date, User, User, int, int, int, int)}.
	 */
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

	/**
	 * Finds a join with the given user and league: the user joined the league, in any moment.
	 * @param user the given user.
	 * @param league the given league.
	 * @return the join found.
	 * @throws BlmsException if the user or the league are null.
	 */
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

	/**
	 * Deletes the given match.
	 * @param m the given match.
	 */
	public void deleteMatch(Match m) {
		store.matches.remove(m);
		m.getLeague().matches.remove(m);
		m.getWinner().matches.remove(m);
		m.getLoser().matches.remove(m);
		updateDb();
	}

	/**
	 * Updates the match result.
	 * @param match the given (current) match.
	 * @param date the given date (date of the match).
	 * @param winner the match's winner.
	 * @param loser the match's loser.
	 * @param length number of balls to be made by highest-ranked player or
	 *            {@link Match#UNDEFINED}.
	 * @param score the final score or {@link Match#UNDEFINED}.
	 * @param longestRunForWinner longest run for the winner or {@link Match#UNDEFINED}.
	 * @param longestRunForLoser longest run for the loser or {@link Match#UNDEFINED}.
	 * @throws BlmsException if winner equals loser, or see {@link Match#setLongestRunForLoser(int)}
	 *         and see {@link Match#setLongestRunForWinner(int)}.
	 */
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
