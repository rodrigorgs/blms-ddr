package blms;

import java.util.Date;

public class Match {
	Date date;
	League league;
	User winner;
	User loser;
	int length;
	int score;
	int longestRunForWinner;
	int longestRunForLoser;
	public enum Role {WINNER, LOSER};
	public static final int UNDEFINED = -1;
	
	public Match(League league, Date date, User winner, User loser) {
		this(league, date, winner, loser, UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED);
	}

	public Match(League league, Date date, User winner, User loser,
			int length, int score, int longestRunForWinner,
			int longestRunForLoser) {
		this.league = league;
		this.date = date;
		this.winner = winner;
		this.loser = loser;
		this.length = length;
		this.score = score;
		this.longestRunForWinner = longestRunForWinner;
		this.longestRunForLoser = longestRunForLoser;
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

	public int getLength() {
		return length;
	}

	public int getScore() {
		return score;
	}

	public int getLongestRunForWinner() {
		return longestRunForWinner;
	}

	public int getLongestRunForLoser() {
		return longestRunForLoser;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public void setWinner(User winner) {
		this.winner = winner;
	}

	public void setLoser(User loser) {
		this.loser = loser;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setLongestRunForWinner(int longestRunForWinner) {
		this.longestRunForWinner = longestRunForWinner;
	}

	public void setLongestRunForLoser(int longestRunForLoser) {
		this.longestRunForLoser = longestRunForLoser;
	}
}
