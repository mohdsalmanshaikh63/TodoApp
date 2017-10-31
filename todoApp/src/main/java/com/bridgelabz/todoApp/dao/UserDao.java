package com.bridgelabz.todoApp.dao;

import com.bridgelabz.todoApp.entity.User;

/**
 * @author Salman
 *
 */
public interface UserDao {
	
	/**
	 * @param user
	 * @return whether the user was successfully registered or not
	 */
	public int registerUser(User user);
	
	/**
	 * @param email
	 * @param password
	 * @return
	 */
	public int login(User user);

	public boolean activate(int id);

	public User getUser(int id);

	public int getUserId(String email);
	
	public int checkUser(String email);

}
