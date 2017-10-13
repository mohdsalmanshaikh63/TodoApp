package com.bridgelabz.restApiDemo.service;

import com.bridgelabz.restApiDemo.entity.User;

public interface UserService {

	public boolean createUser(User user);
	
	public boolean login(User user);

	public boolean activate(int id);
	
	public User getUser(int id);
	
	public int getUserId(String email);
	
	public boolean checkUser(String email);

}
