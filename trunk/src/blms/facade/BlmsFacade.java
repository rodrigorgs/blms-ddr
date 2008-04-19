package blms.facade;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import blms.League;
import blms.Registry;
import blms.User;
import blms.Util;
import blms.exceptions.BlmsException;

public class BlmsFacade {
	Registry registry;
	SimpleDateFormat dateFormat;

	public BlmsFacade() {
		registry = new Registry();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	}
	
	private void changeAttribute(String id, String attribute, String value)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException, BlmsException {

		Object target = registry.getObject(id);
		registry.changeAttribute(target, attribute, value);
	}
	
	private Object getAttribute(String id, String attribute) 
			throws SecurityException, NoSuchMethodException, IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Object target = registry.getObject(id);
		return registry.getAttribute(target, id, attribute);
	}
	
	// from us-standings.txt:340,343,346,373,374,375 us-history.txt:453,454,455,471 us-win-loss.txt:535,538,541,582,610,620,621,622,640,644,673,674,675,676,677,678,679,680,681,682,683,684,685,691,692,693,694,695,696,697,698,699,700,701,702,704,705,706,708,709,710,712,713,714,715,717,718,719,720,749,761,776,793,814,824,834,844,854,864,874,911 
	public String addMatchResult(String leagueId, String date, String winner, String loser) throws Exception {
		return "";
	}

	// from us-leagues.txt:88,89,90,93,94,102,103,104,105,106,109 
	public void changeLeagueAttribute(String id, String value) throws Exception {
		
	}

	// from us-users.txt:231,232,233,236,237,238,239,240,241,242,254,255,256,257,258,259,262,263,264,267,268,269,272,273 
	public void changeUserAttribute(String id, String attribute, String value) throws Throwable {
		if (attribute == null || attribute.length() == 0)
			throw new Exception("Must provide an attribute to be changed");
		try {
			changeAttribute(id, attribute, value);
		} catch (NoSuchMethodException e) {
			throw new Exception("Unknown user attribute");
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	// from us-leagues.txt:45,46,47,74,75,76,77,78,81,82 us-standings.txt:330 us-history.txt:448 us-join.txt:952,953 us-win-loss.txt:525 
	public String createLeague(String name, String operator) throws Exception {
		if (Util.isNullOrEmpty(operator))
			throw new BlmsException("Required data: league operator");
		User user = (User)registry.getObject(operator);
		if (user == null)
			throw new BlmsException("Unknown user");
		League league = registry.createLeague(name, user);
		return registry.getId(league);
	}

	/**
	 * Creates a new user
	 * @param firstName The first name of the user.
	 * @param lastName The last name of the user.
	 * @param homePhone The home phone number of the user.
	 * @param workPhone The work phone number of the user
	 * @param cellPhone The cell phone number of the user.
	 * @param email The email of the user.
	 * @param picture Name of the user picture.
	 * @return User id number.
	 */ 
	public String createUser(String firstName, String lastName, String homePhone, String workPhone, String cellPhone, String email, String picture) throws Exception {
		User user = registry.createUser(firstName, lastName, homePhone, workPhone, cellPhone, email, picture);
		return registry.getId(user);
	}

	// from us-standings.txt:320 us-history.txt:438 us-win-loss.txt:515,642,646,919,920,921 
	public void dateFormat(String format) throws Exception {
		
	}

	// from us-standings.txt:335,378,381,384,395,396,397,398,399,412 
	public void defineStandingsExpression(String leagueId, String expression) throws Exception {
		
	}

	// from us-leagues.txt:128,131,132 
	public void deleteLeague(String id) {
		
	}

	// from us-standings.txt:360,363,366 us-win-loss.txt:612,912,913,914 
	public void deleteMatch(String matchId) throws Exception {
		
	}

	// from us-leagues.txt:114,130,135,136 us-users.txt:279,281 
	public void deleteUser(String id) throws Exception {
		registry.deleteUser((User)registry.getObject(id));
	}

	// from us-leagues.txt:54,60,66,98 
	public String echo() {
		return "";
	}

	// from us-leagues.txt:36,37,119,120,121,127,129,133 
	public String findLeague(String name) throws Exception {
		throw new BlmsException("Could not find league " + name);
	}

	/**
	 * Finds a user by his/her last name.
	 * @param lastName The last name of the user.
	 * @throws UnknownLastNameException If no user exists with given last name. 
	 */ 
	public String findUserByLastName(String lastName) throws Exception {
		User[] users = registry.findUserByLastName(lastName);
		if (users.length == 0) 
			throw new UnknownLastNameException("Could not find user " + lastName);
		else
			return Arrays.toString(users);
	}

	// from us-leagues.txt:52,53,56,58,59,62,64,65,68,96,97 
	public String getLeagueAttribute(String id, String attribute) throws Exception {
		if (attribute.equals("operator"))
			return registry.getId(getAttribute(id, attribute));
		else if (attribute.equals("creationDate"))
			return dateFormat.format(getAttribute(id, attribute));
		else
			return (String)getAttribute(id, attribute);
	}

	// from us-join.txt:962,967,974,982,989,990,1009 
	public String getLeagueMembers(String leagueId) throws Exception {
		return registry.getLeagueUsers(leagueId);
	}

	// from us-win-loss.txt:551,559,567,594,623,624,625,626,628,629,750,751,752,753,754,762,763,764,765,766,767,768,769 
	public String getMatch(String leagueId, String index) throws Exception {
		return "";
	}

	// from us-history.txt:456,457,458,459,460,461,472,473,474,475,476,477,478,479 
	public String getMatchAsString(String userId, String leagueId, String index) throws Exception {
		return "";
	}

	// from us-win-loss.txt:634,635,777,778,779,780,781,782,783,784,785,786,787,794,795,796,797,798,799,800,801,802,803,804,805,806,807 
	public String getMatchByDate(String leagueId, String startDate, String endDate, String index) throws Exception {
		return "";
	}

	// from us-win-loss.txt:552,560,568,583,596,641,643,645,647 
	public String getMatchDate(String matchId) {
		return "";
	}

	// from us-win-loss.txt:555,563,571,586,599,835,836,837 
	public String getMatchLength(String matchId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:558,566,574,589,602,865,866,867 
	public String getMatchLongestRunForLoser(String matchId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:557,565,573,588,601,855,856,857 
	public String getMatchLongestRunForWinner(String matchId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:554,562,570,585,598,825,826,827 
	public String getMatchLoser(String matchId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:556,564,572,587,600,845,846,847 
	public String getMatchScore(String matchId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:553,561,569,584,597,815,816,817 
	public String getMatchWinner(String matchId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:547,548,737,738,739,740,741,742 
	public String getNumberOfLosses(String id, String leagueId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:530,531,532,536,537,539,540,542,543,609,611,613,656,662,663,664,665,666,667 
	public String getNumberOfMatches(String leagueId) throws Exception {
		return "";
	}

	// from us-win-loss.txt:545,546,726,727,728,729,730,731 
	public String getNumberOfWins(String id, String leagueId) throws Exception {
		return "";
	}

	// from us-join.txt:961,966,973,988,1005 
	public String getPlayerLeagues(String userId) throws Exception {
		return registry.getUserLeagues(userId);
	}

	// from us-standings.txt:338,339,341,342,344,345,347,348,354,355,361,362,364,365,367,368,376,377,379,380,382,383,385,386,405,406,407,408,409,410,413 
	public String getPlayerStanding(String id, String leagueId) throws Exception {
		return "";
	}

	/**
	 * Returns some attribute from some user.
	 * @param id The user id.
	 * @param attribute Name of the attribute.
	 * @return The value of intended attribute for this user.
	 */ 
	public String getUserAttribute(String id, String attribute) throws Exception {
		return (String)getAttribute(id, attribute);
	}

	// from us-join.txt:969,970,991,992,1026,1028,1030,1032 
	public String getUserLeagueAttribute(String userId, String id, String attribute) throws Exception {
		return "";
	}

	// from us-join.txt:957,958,963,968,975,999,1001 
	public boolean isLeagueMember(String userId, String leagueId) throws Exception {
		return registry.isUserLeague(userId, leagueId);
	}

	// from us-standings.txt:331,332 us-history.txt:449,450 us-join.txt:965,980,981,987,1013,1015,1017,1019,1020,1022 us-win-loss.txt:526,527 
	public void joinLeague(String id, String leagueId, String initialHandicap) throws Exception {
		
	}

	// from us-join.txt:972,1036,1038,1040,1042 
	public void leaveLeague(String id, String leagueId) throws Exception {
		
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

	// from us-standings.txt:353 us-win-loss.txt:595,875,876,877,878,879,880,881,882,883,884,885,886,888,889,890,892,893,894,896,897,898,899,901,902,903,904 
	public void updateMatchResult(String matchId, String date, String winner, String loser, String length, String score, String longestRunForWinner, String longestRunForLoser) throws Exception {
		
	}

	/**
	 * Selects the dataBase used.
	 * @param databaseName The name of database instance.
	 */ 
	public void useDatabase(String databaseName) {
		
	}

}

