package blms;

import java.text.Collator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cheffo.jeplite.JEP;
import org.cheffo.jeplite.ParseException;

import blms.exceptions.BlmsException;

// invariant: name and operator are non-empty
public class League implements Comparable<League> {
	String name;
	User operator;
	Date creationDate;
	Vector<Match> matches;
	String standingsExpression;
	
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
		Match[] ret = matches.toArray(new Match[] {});
		Arrays.sort(ret);
		return ret;
	}
	
	public Match getMatch(int index) throws BlmsException {
		try {
			return matches.elementAt(index - 1);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BlmsException("Invalid index");
		}
	}
	
	@Override
	public String toString() {
		return name;
	}

	public int compareTo(League other) {
		return Collator.getInstance().compare(name, other.getName());
	}


	public void addMatch(Match m) {
		matches.add(m);
	}


	public void setStandingsExpression(String expression) throws BlmsException {
		Pattern regex = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*)");
		Matcher m = regex.matcher(expression);
		while (m.find()) {
			String s = m.group();
			if (!s.equals("seasonWins") && !s.equals("seasonLosses"))
				throw new BlmsException("Unknown variable in standings expression");
		}
		
		JEP jep = new JEP();
		jep.addVariable("seasonWins", 0.0);
		jep.addVariable("seasonLosses", 0.0);
		jep.parseExpression(expression);
		try {
			jep.getValue();
		} catch (Throwable e) {
			throw new BlmsException("Syntax error in standings expression");
		}
		
		standingsExpression = expression;
	}

	public String getStandingsExpression() {
		return standingsExpression;
	}
}