package blms.facade;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import blms.Join;
import blms.League;
import blms.Match;
import blms.Registry;
import blms.User;
import blms.exceptions.BlmsException;
import blms.util.Util;

/**
 * Facade for acceptance tests run by 
 * <a href="http://www.easyaccept.org">EasyAccept</a>.
 * Objects are refered by their IDs.
 */
public class BlmsFacade {
	Registry registry;
	SimpleDateFormat dateFormat;
	HashMap<Class, String> classToString;

	public BlmsFacade() {
		registry = new Registry();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		classToString = new HashMap<Class, String>();
		classToString.put(User.class, "user");
		classToString.put(League.class, "league");
		classToString.put(Match.class, "match");
	}

	private <T> T getObject(String id, Class<T> klass) throws Exception {
		String commonName = classToString.get(klass);
		if (Util.isBlank(id))
			throw new Exception("Unknown " + commonName);
		// throw new Exception("Required data: " + commonName);
		// multiple requires
		T obj = (T) registry.getObject(id);
		if (obj == null)
			throw new Exception("Unknown " + commonName);
		return obj;
	}

	private int parseInt(String number, String exception) throws Exception {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			throw new Exception(exception);
		}
	}

	private int parseMatchLength(String number) throws Exception {
		return parseInt(number, "Invalid match length");
	}

	private int parseScore(String number) throws Exception {
		return parseInt(number, "Invalid score");
	}

	private int parseRun(String number) throws Exception {
		return parseInt(number, "Invalid run");
	}

	private int parseIndex(String index) throws Exception {
		String str = "Invalid index";
		int i = parseInt(index, str);
		if (i < 1)
			throw new Exception(str);
		return i;
	}

	private Date parseDate(String date) throws Exception {
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			throw new Exception("Invalid date");
		} catch (NullPointerException e) {
			throw new Exception("Invalid date");
		}
	}

	private void changeAttribute(String id, String attribute, Object value,
			String type, Class valueType) throws Throwable {
		if (Util.isBlank(attribute))
			throw new Exception("Must provide an attribute to be changed");
		try {
			Object target = registry.getObject(id);
			registry.changeAttribute(target, attribute, value, valueType);
		} catch (NoSuchMethodException e) {
			throw new Exception("Unknown " + type + " attribute");
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	private Object getAttribute(String id, String attribute)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Object target = registry.getObject(id);
		return registry.getAttribute(target, attribute);
	}

	// from us-standings.txt:340,343,346,373,374,375
	// us-history.txt:453,454,455,471
	// us-win-loss.txt:535,538,541,582,610,620,621,622,640,644,673,674,675,676,677,678,679,680,681,682,683,684,685,691,692,693,694,695,696,697,698,699,700,701,702,704,705,706,708,709,710,712,713,714,715,717,718,719,720,749,761,776,793,814,824,834,844,854,864,874,911
	public String addMatchResult(String leagueId, String date, String winner,
			String loser) throws Throwable {
		String x = "" + Match.UNDEFINED;
		return addMatchResult(leagueId, date, winner, loser, x, x, x, x);
	}

	public String addMatchResult(String leagueId, String date, String winner,
			String loser, String length, String score,
			String longestRunForWinner, String longestRunForLoser)
			throws Throwable {
		League league = getObject(leagueId, League.class);
		Date parsedDate = parseDate(date);
		User userWinner = getObject(winner, User.class);
		User userLoser = getObject(loser, User.class);
		int intLength = parseMatchLength(length);
		int intScore = parseScore(score);
		int intLongestRunForWinner = parseRun(longestRunForWinner);
		int intLongestRunForLoser = parseRun(longestRunForLoser);

		Match m = registry.addMatchResult(league, parsedDate, userWinner,
				userLoser, intLength, intScore, intLongestRunForWinner,
				intLongestRunForLoser);
		return registry.getId(m);
	}

	// from us-leagues.txt:88,89,90,93,94,102,103,104,105,106,109
	public void changeLeagueAttribute(String id, String attribute, String value)
			throws Throwable {
		Object objValue = value;
		Class<? extends Object> klass = String.class;

		if (attribute != null) {
			// TODO: this kind of logic should be in League
			if (Util.isBlank(value)
					&& (attribute.equals("operator") || attribute
							.equals("name"))) {
				throw new Exception("Required data: league " + attribute);
			}

			if (attribute.equals("operator")) {
				klass = User.class;
				objValue = registry.getObject(value);
				if (objValue == null)
					throw new Exception("Unknown user");
			}
		}

		changeAttribute(id, attribute, objValue, "league", klass);
	}

	// from
	// us-users.txt:231,232,233,236,237,238,239,240,241,242,254,255,256,257,258,259,262,263,264,267,268,269,272,273
	public void changeUserAttribute(String id, String attribute, String value)
			throws Throwable {
		changeAttribute(id, attribute, value, "user", String.class);
	}

	// from us-leagues.txt:45,46,47,74,75,76,77,78,81,82 us-standings.txt:330
	// us-history.txt:448 us-join.txt:952,953 us-win-loss.txt:525
	public String createLeague(String name, String operator) throws Exception {
		if (Util.isBlank(operator))
			throw new BlmsException("Required data: league operator");
		User user = (User) registry.getObject(operator);
		if (user == null)
			throw new BlmsException("Unknown user");
		League league = registry.createLeague(name, user);
		return registry.getId(league);
	}

	/**
	 * Creates a new user.
	 * 
	 * @param firstName
	 *            The first name of the user.
	 * @param lastName
	 *            The last name of the user.
	 * @param homePhone
	 *            The home phone number of the user.
	 * @param workPhone
	 *            The work phone number of the user
	 * @param cellPhone
	 *            The cell phone number of the user.
	 * @param email
	 *            The email of the user.
	 * @param picture
	 *            Name of the user picture.
	 * @return User id number.
	 */
	public String createUser(String firstName, String lastName,
			String homePhone, String workPhone, String cellPhone, String email,
			String picture) throws Exception {
		User user = registry.createUser(firstName, lastName, homePhone,
				workPhone, cellPhone, email, picture);
		return registry.getId(user);
	}

	// from us-standings.txt:320 us-history.txt:438
	// us-win-loss.txt:515,642,646,919,920,921
	public void dateFormat(String format) throws Exception {
		try {
			if (format.trim().length() == 0)
				throw new Exception("Unknown date format");
			dateFormat.applyPattern(format.replaceAll("m", "M"));
		} catch (Exception e) {
			throw new Exception("Unknown date format");
		}
	}

	// from us-standings.txt:335,378,381,384,395,396,397,398,399,412
	public void defineStandingsExpression(String leagueId, String expression)
			throws Exception {
		League league = getObject(leagueId, League.class);
		// try {
		league.setStandingsExpression(expression);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	// from us-leagues.txt:128,131,132
	public void deleteLeague(String id) {
		registry.deleteLeague((League) registry.getObject(id));
	}

	// from us-standings.txt:360,363,366 us-win-loss.txt:612,912,913,914
	public void deleteMatch(String matchId) throws Exception {
		registry.deleteMatch(getObject(matchId, Match.class));
	}

	// from us-leagues.txt:114,130,135,136 us-users.txt:279,281
	public void deleteUser(String id) throws Exception {
		registry.deleteUser((User) registry.getObject(id));
	}

	// from us-leagues.txt:36,37,119,120,121,127,129,133
	public String findLeague(String name) throws Exception {
		League[] leagues = registry.findLeague(name);
		if (leagues.length == 0)
			throw new BlmsException("Could not find league " + name);
		else
			return Arrays.toString(leagues);
	}

	/**
	 * Finds a user by his/her last name.
	 * 
	 * @param lastName
	 *            The last name of the user.
	 * @throws UnknownLastNameException
	 *             If no user exists with given last name.
	 */
	public String findUserByLastName(String lastName) throws Exception {
		User[] users = registry.findUserByLastName(lastName);
		if (users.length == 0)
			throw new UnknownLastNameException("Could not find user "
					+ lastName);
		else
			return Arrays.toString(users);
	}

	// from us-leagues.txt:52,53,56,58,59,62,64,65,68,96,97
	public String getLeagueAttribute(String id, String attribute)
			throws Exception {
		if (attribute.equals("operator"))
			return registry.getId(getAttribute(id, attribute));
		else if (attribute.equals("creationDate"))
			return dateFormat.format(getAttribute(id, attribute));
		else
			return (String) getAttribute(id, attribute);
	}

	// from us-join.txt:962,967,974,982,989,990,1009
	public String getLeagueMembers(String leagueId) throws Exception {
		League league = (League) registry.getObject(leagueId);
		return registry.getLeagueUsers(league);
	}

	// from
	// us-win-loss.txt:551,559,567,594,623,624,625,626,628,629,750,751,752,753,754,762,763,764,765,766,767,768,769
	public String getMatch(String leagueId, String index) throws Exception {
		League league = getObject(leagueId, League.class);
		Match match = league.getMatch(parseIndex(index));
		return registry.getId(match);
	}

	public String getMatch(String userId, String leagueId, String index)
			throws Exception {
		User user = getObject(userId, User.class);
		League league = getObject(leagueId, League.class);
		int i = parseIndex(index);

		Match m = user.getMatch(league, i);
		return registry.getId(m);
	}

	// from
	// us-history.txt:456,457,458,459,460,461,472,473,474,475,476,477,478,479
	public String getMatchAsString(String userId, String leagueId, String index)
			throws Exception {
		return "not implemented yet";
	}

	// from
	// us-win-loss.txt:634,635,777,778,779,780,781,782,783,784,785,786,787,794,795,796,797,798,799,800,801,802,803,804,805,806,807
	public String getMatchByDate(String leagueId, String startDate,
			String endDate, String index) throws Exception {
		League league = getObject(leagueId, League.class);
		Date start = parseDate(startDate);
		Date end = parseDate(endDate);
		int _index = parseIndex(index);

		int i = 0;
		Match[] matches = league.getMatches();
		for (Match match : matches) {
			if (match.getDate().compareTo(start) >= 0
					&& match.getDate().compareTo(end) <= 0) {
				i++;
				if (i == _index)
					return registry.getId(match);
			}
		}

		if (_index > i)
			throw new BlmsException("Invalid index");

		return "";
	}

	public String getMatchByDate(String userId, String leagueId,
			String startDate, String endDate, String index) throws Exception {
		User user = getObject(userId, User.class);
		League league = getObject(leagueId, League.class);
		;
		Date start = parseDate(startDate);
		Date end = parseDate(endDate);
		int _index = parseIndex(index);

		int i = 0;
		Match[] matches = user.getMatches(league);
		for (Match match : matches) {
			if (match.getDate().compareTo(start) >= 0
					&& match.getDate().compareTo(end) <= 0) {
				i++;
				if (i == _index)
					return registry.getId(match);
			}
		}

		if (_index > i)
			throw new BlmsException("Invalid index");

		return "";
	}

	// expect ${matchId2} getMatchByDate leagueId=${leagueId1}
	// startDate=2/12/2007 endDate=2/12/2007 index=1
	// expect ${matchId2} getMatchByDate userId=${userId1} leagueId=${leagueId1}
	// startDate=2/12/2007 endDate=2/12/2007 index=1

	// from us-win-loss.txt:552,560,568,583,596,641,643,645,647
	public String getMatchDate(String matchId) throws Exception {
		Match m = getObject(matchId, Match.class);
		return dateFormat.format(m.getDate());
	}

	// from us-win-loss.txt:555,563,571,586,599,835,836,837
	public String getMatchLength(String matchId) throws Exception {
		Match m = getObject(matchId, Match.class);
		int x = m.getLength();
		return x == Match.UNDEFINED ? "" : "" + x;
	}

	// from us-win-loss.txt:558,566,574,589,602,865,866,867
	public String getMatchLongestRunForLoser(String matchId) throws Exception {
		Match m = getObject(matchId, Match.class);
		int x = m.getLongestRunForLoser();
		return x == Match.UNDEFINED ? "" : "" + x;
	}

	// from us-win-loss.txt:557,565,573,588,601,855,856,857
	public String getMatchLongestRunForWinner(String matchId) throws Exception {
		Match m = getObject(matchId, Match.class);
		int x = m.getLongestRunForWinner();
		return x == Match.UNDEFINED ? "" : "" + x;
	}

	// from us-win-loss.txt:554,562,570,585,598,825,826,827
	public String getMatchLoser(String matchId) throws Exception {
		Match m = getObject(matchId, Match.class);
		return registry.getId(m.getLoser());
	}

	// from us-win-loss.txt:556,564,572,587,600,845,846,847
	public String getMatchScore(String matchId) throws Exception {
		Match m = getObject(matchId, Match.class);
		int x = m.getScore();
		return x == Match.UNDEFINED ? "" : "" + x;
	}

	// from us-win-loss.txt:553,561,569,584,597,815,816,817
	public String getMatchWinner(String matchId) throws Exception {
		Match m = getObject(matchId, Match.class);
		return registry.getId(m.getWinner());
	}

	// from us-win-loss.txt:547,548,737,738,739,740,741,742
	public String getNumberOfLosses(String id, String leagueId)
			throws Exception {
		User user = getObject(id, User.class);
		League league = getObject(leagueId, League.class);
		return "" + user.getNumberOfWinsOrLosses(league, Match.Role.LOSER);
	}

	// from
	// us-win-loss.txt:530,531,532,536,537,539,540,542,543,609,611,613,656,662,663,664,665,666,667
	public String getNumberOfMatches(String leagueId) throws Exception {
		League league = getObject(leagueId, League.class);
		return "" + league.getMatches().length;
	}

	public String getNumberOfMatches(String id, String leagueId)
			throws Exception {
		User user = getObject(id, User.class);
		League league = getObject(leagueId, League.class);
		;
		return "" + user.getMatches(league).length;
	}

	// from us-win-loss.txt:545,546,726,727,728,729,730,731
	public String getNumberOfWins(String id, String leagueId) throws Exception {
		User user = getObject(id, User.class);
		League league = getObject(leagueId, League.class);
		return "" + user.getNumberOfWinsOrLosses(league, Match.Role.WINNER);
	}

	// from us-join.txt:961,966,973,988,1005
	public String getPlayerLeagues(String userId) throws Exception {
		User user = (User) registry.getObject(userId);
		return registry.getUserLeagues(user);
	}

	// from
	// us-standings.txt:338,339,341,342,344,345,347,348,354,355,361,362,364,365,367,368,376,377,379,380,382,383,385,386,405,406,407,408,409,410,413
	public String getPlayerStanding(String id, String leagueId)
			throws Exception {
		User user = getObject(id, User.class);
		League league = getObject(leagueId, League.class);
		return "" + (int) user.getStanding(league);
	}

	/**
	 * Returns some attribute from some user.
	 * 
	 * @param id
	 *            The user id.
	 * @param attribute
	 *            Name of the attribute.
	 * @return The value of intended attribute for this user.
	 */
	public String getUserAttribute(String id, String attribute)
			throws Exception {
		return (String) getAttribute(id, attribute);
	}

	// from us-join.txt:969,970,991,992,1026,1028,1030,1032
	public String getUserLeagueAttribute(String userId, String id,
			String attribute) throws Exception {
		User user = (User) registry.getObject(userId);
		League league = (League) registry.getObject(id);
		if (!registry.isUserLeague(user, league)) {
			throw new BlmsException("User is not a league member");
		}
		Join join = registry.findJoin(user, league);

		try {
			Object value = registry.getAttribute(join, attribute);

			if (value instanceof Date)
				return dateFormat.format((Date) value);
			else
				return value.toString();
		} catch (NoSuchMethodException e) {
			throw new BlmsException("Unknown user attribute");
		}
	}

	// from us-join.txt:957,958,963,968,975,999,1001
	public boolean isLeagueMember(String userId, String leagueId)
			throws Exception {
		User user = (User) registry.getObject(userId);
		League league = (League) registry.getObject(leagueId);
		return registry.isUserLeague(user, league);

	}

	// from us-standings.txt:331,332 us-history.txt:449,450
	// us-join.txt:965,980,981,987,1013,1015,1017,1019,1020,1022
	// us-win-loss.txt:526,527
	public void joinLeague(String idUser, String idLeague,
			String initialHandicap) throws Exception {
		User user = (User) registry.getObject(idUser);
		League league = (League) registry.getObject(idLeague);
		registry.userJoinLeague(user, league, initialHandicap);
	}

	// from us-join.txt:972,1036,1038,1040,1042
	public void leaveLeague(String id, String leagueId) throws Exception {
		User user = (User) registry.getObject(id);
		League league = (League) registry.getObject(leagueId);
		registry.userLeaveLeague(user, league);
	}

	/**
	 * Removes all existing leagues.
	 */
	public void removeAllLeagues() {
		registry.removeAllLeagues();
	}

	/**
	 * Removes all existing matches.
	 */

	public void removeAllMatches() {
		registry.removeAllMatches();
	}

	/**
	 * Removes all existing users.
	 */
	public void removeAllUsers() {
		registry.removeAllUsers();
	}

	// from us-leagues.txt:55,61,67 us-join.txt:954
	public String todaysDate() {
		return dateFormat.format(new Date());
	}

	// from us-standings.txt:353
	// us-win-loss.txt:595,875,876,877,878,879,880,881,882,883,884,885,886,888,889,890,892,893,894,896,897,898,899,901,902,903,904
	public void updateMatchResult(String matchId, String date, String winner,
			String loser, String length, String score,
			String longestRunForWinner, String longestRunForLoser)
			throws Exception {
		Match m = getObject(matchId, Match.class);

		Date parsedDate = parseDate(date);
		User userWinner = getObject(winner, User.class);
		User userLoser = getObject(loser, User.class);
		int intLength = parseMatchLength(length);
		int intScore = parseScore(score);
		int intLongestRunForWinner = parseRun(longestRunForWinner);
		int intLongestRunForLoser = parseRun(longestRunForLoser);

		registry.updateMatchResult(m, parsedDate, userWinner, userLoser,
				intLength, intScore, intLongestRunForWinner,
				intLongestRunForLoser);
	}

	/**
	 * Selects the dataBase used.
	 * 
	 * @param databaseName
	 *            The name of database instance.
	 */
	public void useDatabase(String databaseName) {
		registry.useDatabase(databaseName);
	}

	public void print(String x) {
		System.out.println(x);
	}
}
