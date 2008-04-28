package blms;

import java.util.Collection;
import java.util.LinkedList;

public class BlmsDataStore {
	// TODO: remove public
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
	
//	public void clear() {
//		users.clear();
//		leagues.clear();
//		matches.clear();
//		joins.clear();
//	}
}
