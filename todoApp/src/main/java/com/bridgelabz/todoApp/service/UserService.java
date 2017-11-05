package com.bridgelabz.todoApp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import com.bridgelabz.todoApp.entity.User;

public interface UserService {

	public int createUser(User user, String link) throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException;
	
	public int login(User user);

	public boolean activate(String activateToken);
	
	public User getUser(int id);
	
	public int getUserId(String email);
	
	public int checkUser(String email);

}
