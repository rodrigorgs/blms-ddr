package blms;

import java.util.Date;

import blms.exceptions.BlmsException;

// TODO: check invariant: 0 <= score < length && 1 <= longestRunForWinner <= length && 0 <= longestRunForLoser <= score  
public class Match implements Comparable<Match> {
	Date date;
	League league;
	User winner;
	User loser;
	int length;
	int score;
	int longestRunForWinner;
	int longestRunForLoser;
	public enum Role {WINNER, LOSER};
	public static final int UNDEFINED = Integer.MIN_VALUE;
	
	public Match(League league, Date date, User winner, User loser) throws BlmsException {
		this(league, date, winner, loser, UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED);
	}

	public Match(League league, Date date, User winner, User loser,
			int length, int score, int longestRunForWinner,
			int longestRunForLoser) throws BlmsException {
		this.league = league;
		update(date, winner, loser, length, score, 
				longestRunForWinner, longestRunForLoser);
	}

	public void update(Date date, User winner, User loser,
			int length, int score, int longestRunForWinner,
			int longestRunForLoser) throws BlmsException {
		if (winner == loser)
			throw new BlmsException("Users must be different");
		
		this.date = date;
		this.winner = winner;
		this.loser = loser;
		setLength(length);
		setScore(score);
		setLongestRunForWinner(longestRunForWinner);
		setLongestRunForLoser(longestRunForLoser);		
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

	protected void setDate(Date date) {
		this.date = date;
	}

	protected void setLeague(League league) {
		this.league = league;
	}

	protected void setWinner(User winner) {
		this.winner = winner;
	}

	protected void setLoser(User loser) {
		this.loser = loser;
	}

	protected void setLength(int length) throws BlmsException {
		if (length != UNDEFINED && length < 1)
			throw new BlmsException("Invalid match length");
		this.length = length;
	}

	protected void setScore(int score) throws BlmsException {
		if (score != UNDEFINED && !(score >= 0 && score < length))
			throw new BlmsException("Invalid score");
		this.score = score;
	}
	
	protected void setLongestRunForWinner(int longestRunForWinner) throws BlmsException {
		if (longestRunForWinner != UNDEFINED && !(longestRunForWinner >= 1 && longestRunForWinner <= length))
			throw new BlmsException("Invalid run");
		this.longestRunForWinner = longestRunForWinner;
	}

	protected void setLongestRunForLoser(int longestRunForLoser) throws BlmsException {
		if (longestRunForLoser != UNDEFINED && !(longestRunForLoser >= 0 && longestRunForLoser <= score))
			throw new BlmsException("Invalid run");
		this.longestRunForLoser = longestRunForLoser;
	}

	public int compareTo(Match other) {
		return this.getDate().compareTo(other.getDate());
	}
	
	public String toString() {
		return String.format("Match at %s. Winner: %s. Loser: %s", date, winner, loser);
	}
}
