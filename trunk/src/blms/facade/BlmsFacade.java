package blms.facade;

public class BlmsFacade {
	// from us-standings.txt:340,343,346,373,374,375 us-history.txt:453,454,455,471 us-win-loss.txt:535,538,541,582,610,620,621,622,640,644,673,674,675,676,677,678,679,680,681,682,683,684,685,691,692,693,694,695,696,697,698,699,700,701,702,704,705,706,708,709,710,712,713,714,715,717,718,719,720,749,761,776,793,814,824,834,844,854,864,874,911 
	public String addMatchResult(String leagueId, String date, String winner, String loser) throws Exception {
		return "";
	}

	// from us-leagues.txt:88,89,90,93,94,102,103,104,105,106,109 
	public void changeLeagueAttribute(String id, String value) throws Exception {
		
	}

	// from us-users.txt:231,232,233,236,237,238,239,240,241,242,254,255,256,257,258,259,262,263,264,267,268,269,272,273 
	public void changeUserAttribute(String id, String value) throws Exception {
		
	}

	// from us-leagues.txt:45,46,47,74,75,76,77,78,81,82 us-standings.txt:330 us-history.txt:448 us-join.txt:952,953 us-win-loss.txt:525 
	public String createLeague(String name, String operator) throws Exception {
		return "";
	}

	// from us-leagues.txt:41,43 us-standings.txt:327,328,329 us-users.txt:177,179,202,204,206,208,210,212,215,216,217,218,222,224 us-history.txt:445,446,447 us-join.txt:947,949,950,951 us-win-loss.txt:522,523,524 
	public String createUser(String firstName, String lastName, String homePhone, String workPhone, String cellPhone, String email, String picture) throws Exception {
		return "";
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
		
	}

	// from us-leagues.txt:54,60,66,98 
	public String echo() {
		return "";
	}

	// from us-leagues.txt:36,37,119,120,121,127,129,133 
	public String findLeague(String name) throws Exception {
		return "";
	}

	// from us-users.txt:172,173,278,280,282,288,289,291,293,294 
	public String findUserByLastName(String lastName) throws Exception {
		return "";
	}

	// from us-leagues.txt:52,53,56,58,59,62,64,65,68,96,97 
	public String getLeagueAttribute(String id, String attribute) {
		return "";
	}

	// from us-join.txt:962,967,974,982,989,990,1009 
	public String getLeagueMembers(String leagueId) throws Exception {
		return "";
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
		return "";
	}

	// from us-standings.txt:338,339,341,342,344,345,347,348,354,355,361,362,364,365,367,368,376,377,379,380,382,383,385,386,405,406,407,408,409,410,413 
	public String getPlayerStanding(String id, String leagueId) throws Exception {
		return "";
	}

	// from us-users.txt:180,181,182,183,184,185,186,188,189,190,191,192,193,194,244,245,246,247,248,249,250 
	public String getUserAttribute(String id, String attribute) {
		return "";
	}

	// from us-join.txt:969,970,991,992,1026,1028,1030,1032 
	public String getUserLeagueAttribute(String userId, String id, String attribute) throws Exception {
		return "";
	}

	// from us-join.txt:957,958,963,968,975,999,1001 
	public String isLeagueMember(String userId, String leagueId) throws Exception {
		return "";
	}

	// from us-standings.txt:331,332 us-history.txt:449,450 us-join.txt:965,980,981,987,1013,1015,1017,1019,1020,1022 us-win-loss.txt:526,527 
	public void joinLeague(String id, String leagueId, String initialHandicap) throws Exception {
		
	}

	// from us-join.txt:972,1036,1038,1040,1042 
	public void leaveLeague(String id, String leagueId) throws Exception {
		
	}

	// from us-leagues.txt:29 us-standings.txt:316 us-users.txt:165 us-history.txt:434 us-join.txt:939 us-win-loss.txt:511 
	public void removeAllLeagues() {
		
	}

	// from us-leagues.txt:28 us-standings.txt:315,411 us-users.txt:164 us-history.txt:433,470 us-join.txt:938 us-win-loss.txt:510,580,608,619,748,760,775,792,813,823,833,843,853,863,873,910 
	public void removeAllMatches() {
		
	}

	// from us-leagues.txt:30 us-standings.txt:317 us-users.txt:166 us-history.txt:435 us-join.txt:940 us-win-loss.txt:512 
	public void removeAllUsers() {
		
	}

	// from us-leagues.txt:55,61,67 us-join.txt:954 
	public String todaysDate() {
		return "";
	}

	// from us-standings.txt:353 us-win-loss.txt:595,875,876,877,878,879,880,881,882,883,884,885,886,888,889,890,892,893,894,896,897,898,899,901,902,903,904 
	public void updateMatchResult(String matchId, String date, String winner, String loser, String length, String score, String longestRunForWinner, String longestRunForLoser) throws Exception {
		
	}

	// from us-leagues.txt:25 us-standings.txt:312 us-users.txt:161 us-history.txt:430 us-join.txt:935 us-win-loss.txt:507 
	public void useDatabase(String databaseName) {
		
	}

}

