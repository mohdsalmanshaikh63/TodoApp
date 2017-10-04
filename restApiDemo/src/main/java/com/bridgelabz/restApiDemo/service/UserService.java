package com.bridgelabz.restApiDemo.service;

import com.bridgelabz.restApiDemo.entity.User;

public interface UserService {

	public boolean register(User user);
	
	public boolean login(String userName, String password);
}
