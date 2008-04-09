package blms.facade

public class BlmsFacade {
	// from tests/us-standings.txt:112,115,118,145,146,147 tests/us-win-loss.txt:656,659,662,703,731,741,742,743,761,765,794,795,796,797,798,799,800,801,802,803,804,805,806,812,813,814,815,816,817,818,819,820,821,822,823,825,826,827,829,830,831,833,834,835,836,838,839,840,841,870,882,897,914,935,945,955,965,975,985,995,1032 tests/us-history.txt:40,41,42,58 
	public String addMatchResult(String leagueId, String date, String winner, String loser, String length, String score, String longestRunForWinner, String longestRunForLoser) throws Exception {
		return "";
	}

	// from tests/us-leagues.txt:547,548,549,552,553,561,562,563,564,565,568 
	public void changeLeagueAttribute(String id, String value) throws Exception {
		
	}

	// from tests/us-users.txt:275,276,277,280,281,282,283,284,285,286,298,299,300,301,302,303,306,307,308,311,312,313,316,317 
	public void changeUserAttribute(String id, String value) throws Exception {
		
	}

	// from tests/us-standings.txt:102 tests/us-join.txt:369,370 tests/us-win-loss.txt:646 tests/us-leagues.txt:504,505,506,533,534,535,536,537,540,541 tests/us-history.txt:35 
	public String createLeague(String name, String operator) throws Exception {
		return "";
	}

	// from tests/us-standings.txt:99,100,101 tests/us-join.txt:364,366,367,368 tests/us-users.txt:221,223,246,248,250,252,254,256,259,260,261,262,266,268 tests/us-win-loss.txt:643,644,645 tests/us-leagues.txt:500,502 tests/us-history.txt:32,33,34 
	public String createUser(String firstName, String lastName, String homePhone, String workPhone, String cellPhone, String email, String picture) throws Exception {
		return "";
	}

	// from tests/us-standings.txt:92 tests/us-win-loss.txt:636,763,767,1040,1041,1042 tests/us-history.txt:25 
	public void dateFormat(String format) throws Exception {
		
	}

	// from tests/us-standings.txt:107,150,153,156,167,168,169,170,171,184 
	public void defineStandingsExpression(String leagueId, String expression) throws Exception {
		
	}

	// from tests/us-leagues.txt:587,590,591 
	public void deleteLeague(String id) {
		
	}

	// from tests/us-standings.txt:132,135,138 tests/us-win-loss.txt:733,1033,1034,1035 
	public void deleteMatch(String matchId) throws Exception {
		
	}

	// from tests/us-users.txt:323,325 tests/us-leagues.txt:573,589,594,595 
	public void deleteUser(String id) throws Exception {
		
	}

	// from tests/us-leagues.txt:513,519,525,557 
	public void expectEqual() {
		
	}

	// from tests/us-leagues.txt:495,496,578,579,580,586,588,592 
	public String findLeague(String name) throws Exception {
		return "";
	}

	// from tests/us-users.txt:216,217,322,324,326,332,333,335,337,338 
	public String findUserByLastName(String lastName) throws Exception {
		return "";
	}

	// from tests/us-leagues.txt:511,512,515,517,518,521,523,524,527,555,556 
	public String getLeagueAttribute(String id, String attribute) {
		return "";
	}

	// from tests/us-join.txt:379,384,391,399,406,407,426 
	public String getLeagueMembers(String leagueId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:672,680,688,715,744,745,746,747,749,750,871,872,873,874,875,883,884,885,886,887,888,889,890 
	public String getMatch(String leagueId, String index) throws Exception {
		return "";
	}

	// from tests/us-history.txt:43,44,45,46,47,48,59,60,61,62,63,64,65,66 
	public String getMatchAsString(String userId, String leagueId, String index) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:755,756,898,899,900,901,902,903,904,905,906,907,908,915,916,917,918,919,920,921,922,923,924,925,926,927,928 
	public String getMatchByDate(String leagueId, String startDate, String endDate, String index) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:673,681,689,704,717,762,764,766,768 
	public String getMatchDate(String matchId) {
		return "";
	}

	// from tests/us-win-loss.txt:676,684,692,707,720,956,957,958 
	public String getMatchLength(String matchId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:679,687,695,710,723,986,987,988 
	public String getMatchLongestRunForLoser(String matchId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:678,686,694,709,722,976,977,978 
	public String getMatchLongestRunForWinner(String matchId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:675,683,691,706,719,946,947,948 
	public String getMatchLoser(String matchId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:677,685,693,708,721,966,967,968 
	public String getMatchScore(String matchId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:674,682,690,705,718,936,937,938 
	public String getMatchWinner(String matchId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:668,669,858,859,860,861,862,863 
	public String getNumberOfLosses(String id, String leagueId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:651,652,653,657,658,660,661,663,664,730,732,734,777,783,784,785,786,787,788 
	public String getNumberOfMatches(String leagueId) throws Exception {
		return "";
	}

	// from tests/us-win-loss.txt:666,667,847,848,849,850,851,852 
	public String getNumberOfWins(String id, String leagueId) throws Exception {
		return "";
	}

	// from tests/us-join.txt:378,383,390,405,422 
	public String getPlayerLeagues(String userId) throws Exception {
		return "";
	}

	// from tests/us-standings.txt:110,111,113,114,116,117,119,120,126,127,133,134,136,137,139,140,148,149,151,152,154,155,157,158,177,178,179,180,181,182,185 
	public String getPlayerStanding(String id, String leagueId) throws Exception {
		return "";
	}

	// from tests/us-users.txt:224,225,226,227,228,229,230,232,233,234,235,236,237,238,288,289,290,291,292,293,294 
	public String getUserAttribute(String id, String attribute) {
		return "";
	}

	// from tests/us-join.txt:386,387,408,409,443,445,447,449 
	public String getUserLeagueAttribute(String userId, String id, String attribute) throws Exception {
		return "";
	}

	// from tests/us-join.txt:374,375,380,385,392,416,418 
	public String isLeagueMember(String userId, String leagueId) throws Exception {
		return "";
	}

	// from tests/us-standings.txt:103,104 tests/us-join.txt:382,397,398,404,430,432,434,436,437,439 tests/us-win-loss.txt:647,648 tests/us-history.txt:36,37 
	public void joinLeague(String id, String leagueId, String initialHandicap) throws Exception {
		
	}

	// from tests/us-join.txt:389,453,455,457,459 
	public void leaveLeague(String id, String leagueId) throws Exception {
		
	}

	// from tests/us-standings.txt:88 tests/us-join.txt:356 tests/us-users.txt:209 tests/us-win-loss.txt:632 tests/us-leagues.txt:488 tests/us-history.txt:21 
	public void removeAllLeagues() {
		
	}

	// from tests/us-standings.txt:87,183 tests/us-join.txt:355 tests/us-users.txt:208 tests/us-win-loss.txt:631,701,729,740,869,881,896,913,934,944,954,964,974,984,994,1031 tests/us-leagues.txt:487 tests/us-history.txt:20,57 
	public void removeAllMatches() {
		
	}

	// from tests/us-standings.txt:89 tests/us-join.txt:357 tests/us-users.txt:210 tests/us-win-loss.txt:633 tests/us-leagues.txt:489 tests/us-history.txt:22 
	public void removeAllUsers() {
		
	}

	// from tests/us-join.txt:371 tests/us-leagues.txt:514,520,526 
	public String todaysDate() {
		return "";
	}

	// from tests/us-standings.txt:125 tests/us-win-loss.txt:716,996,997,998,999,1000,1001,1002,1003,1004,1005,1006,1007,1009,1010,1011,1013,1014,1015,1017,1018,1019,1020,1022,1023,1024,1025 
	public void updateMatchResult(String matchId, String date, String winner, String loser, String length, String score, String longestRunForWinner, String longestRunForLoser) throws Exception {
		
	}

	// from tests/us-standings.txt:84 tests/us-join.txt:352 tests/us-users.txt:205 tests/us-win-loss.txt:628 tests/us-leagues.txt:484 tests/us-history.txt:17 
	public void useDatabase(String databaseName) {
		
	}

}

