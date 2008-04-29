package blms;

import java.util.Date;

/**
 * Represents the association between a user and a league (including the
 * date when the user joined the league and his/her handicap in the league).
 */
public class Join {

	User user;
	League league;
	int handicap;
	Date joinDate;

	/**
	 * States that user "user" has joined the league "league" and has a
	 * initial handicap of "initialHandicap". The date of this join is
	 * set to the date of the creation of this object.
	 * @param user
	 * @param league
	 * @param initialHandicap
	 */
	public Join(User user, League league, int initialHandicap){
		this.user = user;
		this.league = league;
		this.handicap = initialHandicap;
		this.joinDate = new Date();
	}
	
	
	/**
	 * Two joins are equal if and only if they have the same user and the same league.
	 */
	public boolean equals(Join join){
		return (this.user).equals(join.user) && (this.league).equals(join.league);
		        //&& (this.initialHandicap == join.initialHandicap) && (this.joinDate).equals(join.joinDate);
	}
	public User getUser(){
		return this.user;
	}
	
	public League getLeague(){
		return this.league;
	}

	/*
	 * It sets the league value, e. g. when the league name is changed.
	 */
	public void setLeague(League newLeague){
		this.league = newLeague;
	}
	
	public int getCurrentHandicap(){
		return this.handicap;
	}
	
	public Date getJoinDate(){
		return this.joinDate;
	}
	
	
}
