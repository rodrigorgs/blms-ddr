package blms;

import java.util.Date;

import blms.exceptions.BlmsException;

public class League {
	String name;
	User operator;
	Date creationDate;
	
	public League(String name, User operator) throws BlmsException {
		
		this.name = name;
		this.operator = operator;
		this.creationDate = new Date();
	}
	

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof League && ((League)obj).getName() == this.name);
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
	
	
}
