package blms;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import util.StringUtil;

public class User {
	String firstName, lastName, homePhone, workPhone, cellPhone, email;
	String picture;
	
	public User(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, 
			String picture) throws Exception {
		this.firstName = firstName;
		this.lastName = lastName;
		this.homePhone = homePhone;
		this.workPhone = workPhone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.picture = picture;
		
		validateAttributes();
	}
	
	private boolean isNullOrEmpty(String s) {
		return (s == null || s.trim().length() == 0);
	}
	
	private void validateAttributes() throws Exception {
		String[] missing = missingAttributes();
		if (missing.length > 0)
			throw new Exception("Required data: " + Util.join(missing, ", "));
		if (isNullOrEmpty(homePhone)
				&& isNullOrEmpty(workPhone)
				&& isNullOrEmpty(cellPhone))
			throw new Exception("Need at least one phone");
	}
	
	private String[] missingAttributes() {
		Collection<String> list = new LinkedList<String>();
		if (isNullOrEmpty(firstName))
			list.add("first name");
		if (isNullOrEmpty(lastName))
			list.add("last name");
		if (isNullOrEmpty(email))
			list.add("email");
		return list.toArray(new String[] {});
	}

	public String toString() {
		return lastName;
	}
	
	public int hashCode() {
		return 0;
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

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
