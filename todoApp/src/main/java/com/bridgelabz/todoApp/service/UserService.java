package com.bridgelabz.todoApp.service;

import com.bridgelabz.todoApp.entity.User;

public interface UserService {

	public boolean createUser(User user);
	
	public int login(User user);

	public boolean activate(int id);
	
	public User getUser(int id);
	
	public int getUserId(String email);
	
	public int checkUser(String email);

}
