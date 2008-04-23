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
	public enum Role {WINNER, LOSER};
	
	public Match(League league, Date date, User winner, User loser) {
		this.league = league;
		this.date = date;
		this.winningPlayer = winner;
		this.losingPlayer = loser;
	}

	public League getLeague() {
		return league;
	}
	
	public Role getUserRole(User user) {
		return (user == winningPlayer) ? Role.WINNER : Role.LOSER;
	}
}
