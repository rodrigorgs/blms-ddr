package blms;

import java.text.Collator;
import java.util.Date;

import blms.exceptions.BlmsException;

// invariant: name and operator are non-empty
public class League implements Comparable<League> {
	String name;
	User operator;
	Date creationDate;
	
	public League(String name, User operator) throws BlmsException {
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
	
	@Override
	public String toString() {
		return name;
	}


	@Override
	public int compareTo(League other) {
		return Collator.getInstance().compare(name, other.getName());
	}
}
