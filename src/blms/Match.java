package blms;

import java.util.Date;

public class Match {
	Date date;
	League league;
	User winningPlayer;
	User losingPlayer;
	int raceLength;
	int finalScore;
	int longestRun[];
	
	
	public League getLeague() {
		return league;
	}
}
