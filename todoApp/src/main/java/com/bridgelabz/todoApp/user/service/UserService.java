package com.bridgelabz.todoApp.user.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import com.bridgelabz.todoApp.token.entity.Token;
import com.bridgelabz.todoApp.user.entity.User;

public interface UserService {

	public int createUser(User user, String link) throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException;

	public Map<String, Token> login(User user);

	public int saveUser(User user);

	public boolean activate(String activateToken);

	public User getUser(int id);

	public int getUserId(String email);

	public int checkUser(String email);

	public boolean changePassword(int id, User user);

	public Token forgotPassword(User user, String link) throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException;
	
	public boolean resetPassword(String token, User user);

	Map<String, Token> socialLogin(String token);

}
