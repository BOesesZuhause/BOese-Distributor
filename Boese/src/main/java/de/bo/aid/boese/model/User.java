/*             
 * 			  (                       
 *			 ( )\         (        (   
 *			 )((_)  (    ))\ (    ))\  
 *			((_)_   )\  /((_))\  /((_) 
 *			 | _ ) ((_)(_)) ((_)(_))   
 *			 | _ \/ _ \/ -_)(_-</ -_)  
 *			 |___/\___/\___|/__/\___|
 *       
 *           			;            
 *		      +        ;;;         + 
 *			  +       ;;;;;        + 
 *			  +      ;;;;;;;       + 
 *			  ++    ;;;;;;;;;     ++ 
 *			  +++++;;;;;;;;;;;+++++  
 *			   ++++;;;;;;;;;;;+++++  
 *				++;;;;;;;;;;;;;++    
 *			     ;;;;;;;;;;;;;;;     
 *			    ;;;;;;;;;;;;;;;;;     
 *				:::::::::::::::::    
 * 				:::::::::::::::::      
 *  			:::::::::::::::::    
 *   			::::::@@@@@::::::    
 *				:::::@:::::@:::::    
 *				::::@:::::::@::::    
 * 				:::::::::::::::::    
 *  			:::::::::::::::::      
 */



package de.bo.aid.boese.model;
// Generated 30.07.2015 11:14:41 by Hibernate Tools 4.3.1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * User generated by hbm2java.
 */
public class User implements java.io.Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The us id. */
	private int usId;
	
	/** The surname. */
	private String surname;
	
	/** The first name. */
	private String firstName;
	
	/** The password. */
	private String password;
	
	/** The gender. */
	private Boolean gender;
	
	/** The birthdate. */
	private Date birthdate;
	
	/** The user name. */
	private String userName;
	
	/** The email. */
	private String email;
	
	/** The group users. */
	private Set<GroupUser> groupUsers = new HashSet<GroupUser>(0);

	/**
	 * Instantiates a new user.
	 */
	public User() {
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param usId the us id
	 * @param surname the surname
	 * @param firstName the first name
	 * @param password the password
	 * @param userName the user name
	 */
	public User(int usId, String surname, String firstName, String password, String userName) {
		this.usId = usId;
		this.surname = surname;
		this.firstName = firstName;
		this.password = password;
		this.userName = userName;
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param usId the us id
	 * @param surname the surname
	 * @param firstName the first name
	 * @param password the password
	 * @param gender the gender
	 * @param birthdate the birthdate
	 * @param userName the user name
	 * @param email the email
	 * @param groupUsers the group users
	 */
	public User(int usId, String surname, String firstName, String password, Boolean gender, Date birthdate,
			String userName, String email, Set<GroupUser> groupUsers) {
		this.usId = usId;
		this.surname = surname;
		this.firstName = firstName;
		this.password = password;
		this.gender = gender;
		this.birthdate = birthdate;
		this.userName = userName;
		this.email = email;
		this.groupUsers = groupUsers;
	}

	/**
	 * Instantiates a new user for DB Insert.
	 *
	 * @param surname the surname
	 * @param firstName the first name
	 * @param password the password
	 * @param gender the gender
	 * @param birthdate the birthdate
	 * @param userName the user name
	 * @param email the email
	 */
	public User(String surname, String firstName, String password, Boolean gender, Date birthdate,
			String userName, String email) {
		this.surname = surname;
		this.firstName = firstName;
		this.password = password;
		this.gender = gender;
		this.birthdate = birthdate;
		this.userName = userName;
		this.email = email;
	}

	/**
	 * Gets the us id.
	 *
	 * @return the us id
	 */
	public int getUsId() {
		return this.usId;
	}

	/**
	 * Sets the us id.
	 *
	 * @param usId the new us id
	 */
	public void setUsId(int usId) {
		this.usId = usId;
	}

	/**
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname the new surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Boolean getGender() {
		return this.gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	/**
	 * Gets the birthdate.
	 *
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return this.birthdate;
	}

	/**
	 * Sets the birthdate.
	 *
	 * @param birthdate the new birthdate
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the group users.
	 *
	 * @return the group users
	 */
	public Set<GroupUser> getGroupUsers() {
		return this.groupUsers;
	}

	/**
	 * Sets the group users.
	 *
	 * @param groupUsers the new group users
	 */
	public void setGroupUsers(Set<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (birthdate == null) {
			if (other.birthdate != null)
				return false;
		} else if (!birthdate.equals(other.birthdate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (groupUsers == null) {
			if (other.groupUsers != null)
				return false;
		} else if (!groupUsers.equals(other.groupUsers))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (usId != other.usId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
