package blms;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import blms.exceptions.BlmsException;

public class Registry {
	Collection<User> users;
	Map<String, Object> idToObj;
	Map<Object, String> objToId;
	long lastId = 0;
	
	private String generateId(Object obj) {
		String s = "" + lastId;
		idToObj.put(s, obj);
		objToId.put(obj, s);
		lastId++;
		return s;
	}
	
	private void removeFromTables(Object obj) {
		idToObj.remove(objToId.get(obj));
		objToId.remove(obj);
	}
	
	public Object getObject(String id) {
		return idToObj.get(id);
	}
	
	public String getId(Object o) {
		return objToId.get(o);
	}
	
	
	public Registry() {
		users = new LinkedList<User>();
		idToObj = new HashMap<String, Object>();
		objToId = new HashMap<Object, String>();
	}
	
	public User[] findUserByLastName(String lastName) {
		Pattern pat = Pattern.compile(lastName, Pattern.CASE_INSENSITIVE);
		Collection<User> ret = new LinkedList<User>();
		for (User user : users) {
			Matcher m = pat.matcher(user.getLastName());
			if (m.matches())
				ret.add(user);
		}
		return ret.toArray(new User[] {});
	}

	public User createUser(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, String picture) throws BlmsException {
		User user = new User(firstName, lastName, homePhone, workPhone, cellPhone, email, picture);
		if (users.contains(user))
			throw new BlmsException("User with this email exists");
		users.add(user);
		generateId(user);
		return user;
	}

	public void deleteUser(User user) {
		users.remove(user);
		removeFromTables(user);
	}
}
