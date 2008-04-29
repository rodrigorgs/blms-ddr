package blms;

import java.text.Collator;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cheffo.jeplite.JEP;

import blms.exceptions.BlmsException;

// invariant: name and operator are non-empty
/**
 * A league. It has a name, an user which operates it, a creation date and
 * a list of matches. The standings of its players are computed from a expression
 * based on the results of its matches. This expression can be changed per league.
 */
public class League implements Comparable<League> {
	String name;
	User operator;
	Date creationDate;
	Vector<Match> matches;
	String standingsExpression;
	
	/**
	 * Creates a league with the given name and the given operator. The creation
	 * date is recorded.
	 * @param name the league's name.
	 * @param operator user that operates this league.
	 * @throws BlmsException if name is null or empty or if operator is null. 
	 */
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
	

	/**
	 * Two leagues are equal if they have the same name (the comparison is
	 * case-insensitive).
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof League && ((League)obj).getName().equalsIgnoreCase(this.name));
	}

	
	public String getName() {
		return name;
	}

	// TODO: check for non-emptiness
	public void setName(String name) {
		this.name = name;
	}

	public User getOperator() {
		return operator;
	}

	// TODO: check for non-emptiness
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

	/**
	 * Leagues are ordered by its names (case-insensitively).
	 */
	public int compareTo(League other) {
		return Collator.getInstance().compare(name, other.getName());
	}


	public void addMatch(Match m) {
		matches.add(m);
	}

	/**
	 * Provide a way of entering an expression to calculate player standing in current season.
	 * @param expression an arithmetic expression that assigns an integer to each player.
	 *   It may use 4 arithmetic operators with the usual precedence and associativity
	 *   rules as well as parentheses. It may refer to the variables <code>seasonWins</code>
	 *   and <code>seasonLosses</code> that mean, respectively, the number of wins and losses
	 *   of the player in the league. Example: "3 * seasonWins + seasonLosses".
	 * @throws BlmsException if the expression is not well-formed or if it uses variable names
	 * other than <code>seasonWins</code> and <code>seasonLosses</code>.
	 */
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
