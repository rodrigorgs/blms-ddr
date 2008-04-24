package blms;

import java.util.Date;

public class Match {
	Date date;
	League league;
	User winner;
	User loser;
	int raceLength;
	int finalScore;
	int longestRun[];
	public enum Role {WINNER, LOSER};
	
	public Match(League league, Date date, User winner, User loser) {
		this.league = league;
		this.date = date;
		this.winner = winner;
		this.loser = loser;
	}

	public League getLeague() {
		return league;
	}
	
	public Role getUserRole(User user) {
		return (user == winner) ? Role.WINNER : Role.LOSER;
	}

	public User getWinner() {
		return winner;
	}

	public User getLoser() {
		return loser;
	}

	public Date getDate() {
		return date;
	}
}
