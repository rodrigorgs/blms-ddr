package blms;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Contains references to every data object in the system: users, leagues and
 * matches.
 * 
 */
public class BlmsDataStore {
	public Collection<User> users;
	public Collection<League> leagues;
	public Collection<Join> joins;
	public Collection<Match> matches;

	public BlmsDataStore() {
		users = new LinkedList<User>();
		leagues = new LinkedList<League>();
		matches = new LinkedList<Match>();
		joins = new LinkedList<Join>();
	}
}
