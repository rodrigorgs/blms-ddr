package blms;

import java.text.Collator;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import blms.exceptions.BlmsException;

// invariant: name and operator are non-empty
public class League implements Comparable<League> {
	String name;
	User operator;
	Date creationDate;
	Vector<Match> matches;
	
	public League(String name, User operator) throws BlmsException {
		matches = new Vector<Match>();
		if (Util.isNullOrEmpty(name))
			throw new BlmsException("Required data: league name");
		if (operator == null)
			throw new BlmsException("Required data: league operator");
		this.name = name;
		this.operator = operator;
		this.creationDate = new Date();
	}
	

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof League && ((League)obj).getName().equals(this.name));
	}

	public String getName() {
		return name;
	}

	// TODO: check if name is available
	public void setName(String name) {
		this.name = name;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}

	public Date getCreationDate() {
		return creationDate;
	}
	
	public Match[] getMatches() {
		return matches.toArray(new Match[] {});
	}
	
	public Match getMatch(int index) {
		return matches.elementAt(index - 1);
	}
	
	@Override
	public String toString() {
		return name;
	}

	public int compareTo(League other) {
		return Collator.getInstance().compare(name, other.getName());
	}


	public void addMatch(Match m) {
//		Match m = new Match(this, date, winner, loser);
		matches.add(m);
//		winner.addMatch(m);
//		loser.addMatch(m);
//		return m;
	}
}
