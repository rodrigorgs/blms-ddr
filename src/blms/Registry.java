package blms;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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
	
	public Collection<User> findUserByLastName(String lastName) {
		Collection<User> ret = new LinkedList<User>();
		for (User user : users)
			if (user.getLastName().equals(lastName))
				ret.add(user);
		return ret;
	}

	public User createUser(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, String picture) throws Exception {
		User user = new User(firstName, lastName, homePhone, workPhone, cellPhone, email, picture);
		users.add(user);
		generateId(user);
		return user;
	}
}
