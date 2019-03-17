package com.exercise.model;

import java.util.Date;

/**
 * 
 * @author VIPR
 * 
 *         This class contains the user model that we receive through the txt
 *         file
 * 
 *
 */

public class User implements Comparable<User> {

	private String name;
	private String surname;
	private boolean active;
	private String email;
	private String city;
	private Date creationDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Method to order data by date
	 */
	
	@Override
	public int compareTo(User u) {
		if (getCreationDate() == null || u.getCreationDate() == null) {
			return 0;
		}
		return getCreationDate().compareTo(u.getCreationDate());
	}

}
