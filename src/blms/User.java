package blms;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import blms.exceptions.BlmsException;

import util.StringUtil;

// Invariant: the user has a firstName, a lastName and a email (not null and not empty).
// Invariant: the user has at least one phone number.
public class User {
	String firstName, lastName, homePhone, workPhone, cellPhone, email;
	String picture;
	
	public User(String firstName, String lastName, String homePhone,
			String workPhone, String cellPhone, String email, 
			String picture) throws BlmsException {
		Validator.validateAttributes(firstName, lastName, email, homePhone, workPhone, cellPhone);
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.homePhone = homePhone;
		this.workPhone = workPhone;
		this.cellPhone = cellPhone;
		this.email = email;
		this.picture = picture;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User))
			return false;
		User other = (User)o;
		return email.equals(other.getEmail());
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
	 * Must not be used directly. Please use {@link Registry#changeAttribute(Object, String, String)}.
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
	
	static class Validator {
		private static void validateAttributes(String firstName, String lastName, 
				String email, String homePhone, String workPhone, 
				String cellPhone) throws BlmsException {
			validateNameAndEmail(firstName, lastName, email);
			validatePhones(homePhone, workPhone, cellPhone);
		}
		
		private static void validateNameAndEmail(String firstName, String lastName, 
				String email) throws BlmsException {
			String[] missing = missingAttributes(firstName, lastName, email);
			if (missing.length > 0)
				throw new BlmsException("Required data: " + Util.join(missing, ", "));		
		}
		
		private static void validatePhones(String homePhone, String workPhone, 
				String cellPhone) throws BlmsException {
			if (Util.isNullOrEmpty(homePhone)
					&& Util.isNullOrEmpty(workPhone)
					&& Util.isNullOrEmpty(cellPhone))
				throw new BlmsException("Need at least one phone");
		}
		
		private static String[] missingAttributes(String firstName, String lastName, 
				String email) {
			Collection<String> list = new LinkedList<String>();
			if (Util.isNullOrEmpty(firstName))
				list.add("first name");
			if (Util.isNullOrEmpty(lastName))
				list.add("last name");
			if (Util.isNullOrEmpty(email))
				list.add("email");
			return list.toArray(new String[] {});
		}	
	}
}
