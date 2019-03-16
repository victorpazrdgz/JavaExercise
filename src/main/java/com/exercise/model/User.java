package com.exercise.model;

import java.util.Date;

/**
 * 
 * @author VIPR
 * 
 *  	This class contains the user model that we receive through the txt file
 *       
 *
 */

public class User {
	
	private String name;
	private String surname;
	private boolean active;
	private String email;
	private String city;
	private String creationDate;
	
//	public User() {}
//	public User(String name, String surname,boolean active, String email, String city, String creationDate) {
//		this.name=name;
//		this.surname=surname;
//		this.active=active;
//		this.email=email;
//		this.city=city;
//		this.creationDate=creationDate;
//		
//	}
//	public String toString() {
//	    return "[" + name +"" + surname +"" + active +"" + email +" " + city +"" + creationDate +"]";
//	}
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
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	
	

}
