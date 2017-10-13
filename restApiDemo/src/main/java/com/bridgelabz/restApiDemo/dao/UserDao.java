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
	public boolean login(User user);

	public boolean activate(int id);

	public User getUser(int id);

	public int getUserId(String email);
	
	public boolean checkUser(String email);

}
