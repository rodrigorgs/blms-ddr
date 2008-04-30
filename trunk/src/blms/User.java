package blms;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.cheffo.jeplite.JEP;

import blms.Match.Role;
import blms.exceptions.BlmsException;
import blms.util.Util;

// Invariant: the user has a firstName, a lastName and a email (not null and not empty).
// Invariant: the user has at least one phone number.
/**
 * A user of the system. It has first name, last name, home phone, work phone,
 * cell phone, email and picture.
 */
public class User {
	String firstName, lastName, homePhone, workPhone, cellPhone, email;
	String picture;
	Collection<Match> matches;

	/**
	 * Creates a user with the given parameters. At least one phone number
	 * must be provided. The parameters firstName, lastName and email are
	 * mandatory.
	 * @param firstName user's first name. Mandatory.
	 * @param lastName user's last name. Mandatory.
	 * @param homePhone user's home phone.
	 * @param workPhone user's work phone.
	 * @param cellPhone user's cell phone.
	 * @param email user's email address. Mandatory.
	 * @param picture user's picture.
	 * @throws BlmsException if a mandatory field is missing or if no
	 * phone number is provided.
	 */
	public User(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, String picture)
			throws BlmsException {
		Validator.validateAttributes(firstName, lastName, email, homePhone,
				workPhone, cellPhone);
		matches = new LinkedList<Match>();
		this.firstName = firstName;
		this.lastName = lastName;
		this.homePhone = homePhone;
		this.workPhone = workPhone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.picture = picture;
	}

	void removeMatch(Match m) {
		matches.remove(m);
	}

	void addMatch(Match m) {
		matches.add(m);
	}
	
	/**
	 * Two users are equal if and only if they have the same email address
	 * (case-insensitive comparison).
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User))
			return false;
		User other = (User) o;
		return email.equalsIgnoreCase(other.getEmail());
	}

	@Override
	public String toString() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public String getEmail() {
		return email;
	}

	public String getPicture() {
		return picture;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setFirstName(String firstName) throws Exception {
		Validator.validateNameAndEmail(firstName, this.lastName, this.email);
		this.firstName = firstName;
	}

	public void setLastName(String lastName) throws Exception {
		Validator.validateNameAndEmail(this.firstName, lastName, this.email);
		this.lastName = lastName;
	}

	/**
	 * Must not be used directly. Please use
	 * {@link Registry#changeAttribute(Object, String, String)} instead.
	 * 
	 * @param email
	 * @throws Exception
	 */
	public void setEmail(String email) throws Exception {
		Validator.validateNameAndEmail(this.firstName, this.lastName, email);
		this.email = email;
	}

	public void setHomePhone(String homePhone) throws Exception {
		Validator.validatePhones(homePhone, this.workPhone, this.cellPhone);
		this.homePhone = homePhone;
	}

	public void setWorkPhone(String workPhone) throws Exception {
		Validator.validatePhones(this.homePhone, workPhone, this.cellPhone);
		this.workPhone = workPhone;
	}

	public void setCellPhone(String cellPhone) throws Exception {
		Validator.validatePhones(this.homePhone, this.workPhone, cellPhone);
		this.cellPhone = cellPhone;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * Returns matches played by this player in a league, sorted by date.
	 * @param league the league
	 * @return matches played by this player in a league, sorted by date.
	 */
	public Match[] getMatches(League league) {
		Collection<Match> col = new LinkedList<Match>();
		for (Match m : matches)
			if (m.getLeague() == league)
				col.add(m);

		Match[] ret = col.toArray(new Match[] {});
		Arrays.sort(ret);
		return ret;
	}

	/**
	 * Returns the index-th match played by this player in a league.
	 * @param league the league
	 * @param index index to the match (e.g., 1 returns the first match, 
	 * 2 returns the second, etc.)
	 * @return the index-th match played by this player in the given league.
	 * @throws BlmsException if index is invalid.
	 */
	public Match getMatch(League league, int index) throws BlmsException {
		Match[] m = getMatches(league);
		try {
			return m[index - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new BlmsException("Invalid index");
		}
	}
	
	/**
	 * Returns the number of wins (if role == {@link Role#WINNER}) or losses
	 * (if role == {@link Role#LOSER}) of this player in the given league. 
	 * @param league the league.
	 * @param role one of {@link Role#WINNER}, {@link Role#LOSER}
	 * @return how many matches the user has won (or lost) in the given league.
	 */
	public int getNumberOfWinsOrLosses(League league, Match.Role role) {
		int count = 0;
		for (Match m : matches)
			if (m.getUserRole(this) == role)
				count++;
		return count;
	}

	/**
	 * Get standing for a given league.
	 * @param league the league.
	 * @return the standing for the player in the given league.
	 * @throws BlmsException if there's an error while computing the
	 * standing (e.g. division by zero).
	 */
	public double getStanding(League league) throws BlmsException {
		String standingsExpression = league.getStandingsExpression();

		int wins = 0;
		int losses = 0;
		for (Match m : getMatches(league)) {
			if (m.getWinner() == this)
				wins++;
			else if (m.getLoser() == this)
				losses++;
			else
				throw new BlmsException(
						"Implementation error in User.getStanding()");
		}

		JEP jep = new JEP();
		jep.addVariable("seasonWins", (double) wins);
		jep.addVariable("seasonLosses", (double) losses);
		jep.parseExpression(standingsExpression);

		double ret;
		try {
			ret = jep.getValue();
		} catch (Throwable e) {
			throw new BlmsException("Exception in Java Expression Parser.");
		}
		if (Double.isInfinite(ret))
			throw new BlmsException("Division by zero in standings expression");
		return ret;
	}
	
	/**
	 * Contains helper methods that validate user parameters.
	 */
	static class Validator {
		private static void validateAttributes(String firstName,
				String lastName, String email, String homePhone,
				String workPhone, String cellPhone) throws BlmsException {
			validateNameAndEmail(firstName, lastName, email);
			validatePhones(homePhone, workPhone, cellPhone);
		}

		private static void validateNameAndEmail(String firstName,
				String lastName, String email) throws BlmsException {
			String[] missing = missingAttributes(firstName, lastName, email);
			if (missing.length > 0)
				throw new BlmsException("Required data: "
						+ Util.join(missing, ", "));
		}

		private static void validatePhones(String homePhone, String workPhone,
				String cellPhone) throws BlmsException {
			if (Util.isBlank(homePhone) && Util.isBlank(workPhone)
					&& Util.isBlank(cellPhone))
				throw new BlmsException("Need at least one phone");
		}

		private static String[] missingAttributes(String firstName,
				String lastName, String email) {
			Collection<String> list = new LinkedList<String>();
			if (Util.isBlank(firstName))
				list.add("first name");
			if (Util.isBlank(lastName))
				list.add("last name");
			if (Util.isBlank(email))
				list.add("email");
			return list.toArray(new String[] {});
		}
	}
}
