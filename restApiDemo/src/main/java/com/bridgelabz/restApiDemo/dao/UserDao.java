package com.bridgelabz.restApiDemo.dao;

import com.bridgelabz.restApiDemo.entity.User;

/**
 * @author Salman
 *
 */
public interface UserDao {
	
	/**
	 * @param user
	 * @return whether the user was successfully registered or not
	 */
	public boolean registerUser(User user);
	
	/**
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean login(String email, String password);

}
