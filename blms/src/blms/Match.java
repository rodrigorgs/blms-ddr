package blms;

import java.util.Date;

import blms.exceptions.BlmsException;

// TODO: check invariant: 0 <= score < length && 1 <= longestRunForWinner <= length && 0 <= longestRunForLoser <= score
/**
 * The result of a match between two players. A match result can be entered with
 * the following required info: date, winning player, losing player. The
 * following optional information can also be informed: race length (number of
 * balls to be made by highest-ranked player), the final score, the match's
 * longest run for each player
 */
public class Match implements Comparable<Match> {
	Date date;
	League league;
	User winner;
	User loser;
	int length;
	int score;
	int longestRunForWinner;
	int longestRunForLoser;

	public enum Role {
		WINNER, LOSER
	};

	public static final int UNDEFINED = Integer.MIN_VALUE;

	/**
	 * See {@link Match#Match(League, Date, User, User, int, int, int, int)}.
	 * 
	 * @param league
	 * @param date
	 * @param winner
	 * @param loser
	 * @throws BlmsException
	 */
	Match(League league, Date date, User winner, User loser)
			throws BlmsException {
		this(league, date, winner, loser, UNDEFINED, UNDEFINED, UNDEFINED,
				UNDEFINED);
	}

	/**
	 * Creates a match result. Can't be invoked directly. A match should be
	 * created by the method
	 * {@link Registry#addMatchResult(League, Date, User, User, int, int, int, int)}.
	 * Some parameters can assume the value {@link Match#UNDEFINED}, meaning
	 * that the value is not known.
	 * 
	 * @param league
	 *            the league to which this match is part.
	 * @param date
	 *            the date of the match.
	 * @param winner
	 *            the player who won the match.
	 * @param loser
	 *            the player who lost the match.
	 * @param length
	 *            number of balls to be made by highest-ranked player or
	 *            {@link Match#UNDEFINED}.
	 * @param score
	 *            the final score or {@link Match#UNDEFINED}.
	 * @param longestRunForWinner
	 *            longest run for the winner or {@link Match#UNDEFINED}.
	 * @param longestRunForLoser
	 *            longest run for the loser or {@link Match#UNDEFINED}.
	 * @throws BlmsException
	 *             if (i) winner and loser are the same player, or (ii) score is
	 *             invalid (see {@link Match#setScore(int)}, or (iii) losers's
	 *             longest run is invalid (see
	 *             {@link Match#setLongestRunForLoser(int)}), or (iv) winner's
	 *             longest run is invalid (see
	 *             {@link Match#setLongestRunForWinner(int)}).
	 */
	Match(League league, Date date, User winner, User loser, int length,
			int score, int longestRunForWinner, int longestRunForLoser)
			throws BlmsException {
		this.league = league;
		update(date, winner, loser, length, score, longestRunForWinner,
				longestRunForLoser);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Match) {
			Match other = (Match)obj;
			return date.equals(other.date) && winner == other.winner &&
					loser == other.loser && league == other.league;
//			winner.equals(other.winner) && loser.equals(other.loser) && league.equals(other.league);
		}
		else
			return false;
	}

	/**
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
	public void update(Date date, User winner, User loser, int length,
			int score, int longestRunForWinner, int longestRunForLoser)
			throws BlmsException {
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

	/**
	 * @return the league.
	 */
	public League getLeague() {
		return league;
	}

	/**
	 * 
	 * @param user the given user.
	 * @return the role of user.
	 */
	public Role getUserRole(User user) {
		return (user == winner) ? Role.WINNER : Role.LOSER;
	}

	/**
	 * @return the match's winner.
	 */
	public User getWinner() {
		return winner;
	}

	/**
	 * @return the match's loser.
	 */
	public User getLoser() {
		return loser;
	}

	/**
	 * @return the date of match.
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the number of balls to be made by highest-ranked player
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @return the final score or {@link Match#UNDEFINED}.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return longest run for the winner or {@link Match#UNDEFINED}.
	 */
	public int getLongestRunForWinner() {
		return longestRunForWinner;
	}

	/**
	 * @return longest run for the loser or {@link Match#UNDEFINED}.
	 */
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

	/**
	 * Sets the number of balls to be made by highest-ranked player or
	 *            {@link Match#UNDEFINED}.
	 * @param length the new length.
	 * @throws BlmsException if length is undefined or less than one.
	 */
	protected void setLength(int length) throws BlmsException {
		if (length != UNDEFINED && length < 1)
			throw new BlmsException("Invalid match length");
		this.length = length;
	}

	/**
	 * Sets the final score or {@link Match#UNDEFINED}.
	 * @param score the new score. 
	 * @throws BlmsException
	 *             if score < 0 or score >= the match's length (number of balls
	 *             to be made by highest-ranked player).
	 */
	protected void setScore(int score) throws BlmsException {
		if (score != UNDEFINED && !(score >= 0 && score < length))
			throw new BlmsException("Invalid score");
		this.score = score;
	}

	/**
	 * Sets the longest run for the winner or {@link Match#UNDEFINED}.
	 * @param longestRunForWinner the new longestRunForWinner 
	 * @throws BlmsException
	 *             unless 1 <= longestRunForWinner <= length.
	 */
	protected void setLongestRunForWinner(int longestRunForWinner)
			throws BlmsException {
		if (longestRunForWinner != UNDEFINED
				&& !(longestRunForWinner >= 1 && longestRunForWinner <= length))
			throw new BlmsException("Invalid run");
		this.longestRunForWinner = longestRunForWinner;
	}

	/**
	 * Sets the run for the loser or {@link Match#UNDEFINED}.
	 * @param longestRunForLoser the new longestRunForLoser
	 * @throws BlmsException
	 *             unless 0 <= longestRunForLoser <= score.
	 */
	protected void setLongestRunForLoser(int longestRunForLoser)
			throws BlmsException {
		if (longestRunForLoser != UNDEFINED
				&& !(longestRunForLoser >= 0 && longestRunForLoser <= score))
			throw new BlmsException("Invalid run");
		this.longestRunForLoser = longestRunForLoser;
	}

	/**
	 * Matches are ordered by their dates.
	 */
	public int compareTo(Match other) {
		return this.getDate().compareTo(other.getDate());
	}

	public String toString() {
		return String.format("Match at %s. Winner: %s. Loser: %s", date,
				winner, loser);
	}
}
