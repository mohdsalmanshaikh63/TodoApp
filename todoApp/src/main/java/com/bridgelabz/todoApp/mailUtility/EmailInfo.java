package com.bridgelabz.todoApp.mailUtility;

import java.io.Serializable;

/**
 * @author Salman
 * Our utility class to store and retrieve email details
 *
 */
public class EmailInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String email;
	
	private String password;

	public EmailInfo(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "EmailInfo [email=" + email + ", password=" + password + "]";
	}
	
	

}
