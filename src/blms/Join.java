package blms;

import java.util.Date;

/**
 * Represents the association between a user and a league (including the date
 * when the user joined the league and his/her handicap in the league).
 */
public class Join {

	User user;
	League league;
	int handicap;
	Date joinDate;

	/**
	 * States that user "user" has joined the league "league" and has an initial
	 * handicap of "initialHandicap". The date of this join is set to the date
	 * of the creation of this object.
	 * 
	 * @param user the user who joins the league.
	 * @param league the league which the user joins.
	 * @param initialHandicap the initial handicap of the user.
	 */
	public Join(User user, League league, int initialHandicap) {
		this.user = user;
		this.league = league;
		this.handicap = initialHandicap;
		this.joinDate = new Date();
	}

	/**
	 * Two joins are equal if and only if they have the same user and the same
	 * league.
	 */
	public boolean equals(Join join) {
		return (this.user).equals(join.user)
				&& (this.league).equals(join.league);
		// && (this.initialHandicap == join.initialHandicap) &&
		// (this.joinDate).equals(join.joinDate);
	}

	/**
	 * @return the user of the "join".
	 */
	public User getUser() {
		return this.user;
	}

	/**
	 * @return the league of the join (in which the user joined). 
	 */
	public League getLeague() {
		return this.league;
	}

	/**
	 * Sets the league value, e. g. when the league name is changed.
	 * @param newLeague the new league.
	 */
	public void setLeague(League newLeague) {
		this.league = newLeague;
	}

	/**
	 * @return the current handicap of the user associated to join.
	 */
	public int getCurrentHandicap() {
		return this.handicap;
	}

	/**
	 * @return the creation date of the join (the date in which the user joined in the league).
	 */
	public Date getJoinDate() {
		return this.joinDate;
	}

}
