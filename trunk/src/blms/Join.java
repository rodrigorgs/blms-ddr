package blms;

import java.util.Date;

public class Join {

	public User user;
	public League league;
	public int initialHandicap;
	public Date joinDate;
	
	public Join(User user, League league, int initialHandicap){
		this.user = user;
		this.league = league;
		this.initialHandicap = initialHandicap;
		this.joinDate = new Date();
	}
	
	
	/*
	 * For our tests, just interest us if the user and league attributes are equals.
	 * The joinDate and initialHandicap attributes don't matter.
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
		return this.initialHandicap;
	}
	
	public Date getJoinDate(){
		return this.joinDate;
	}
	
	
}
