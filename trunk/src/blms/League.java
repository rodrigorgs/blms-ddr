package blms;

import java.util.Date;

public class League {
	String name;
	User operator;
	Date creationDate;
	
	public League(String name, User operator, Date creationDate) {
		super();
		this.name = name;
		this.operator = operator;
		this.creationDate = creationDate;
	}
	
	
}
