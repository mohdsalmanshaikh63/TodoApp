package com.bridgelabz.restApiDemo.service;

import com.bridgelabz.restApiDemo.entity.User;

public interface UserService {

	public boolean createUser(User user);
	
	public boolean login(String userName, String password);

	public boolean activate(int id);
	
	public User getUser(int id);
	
	public int getUserId(String email);
}
