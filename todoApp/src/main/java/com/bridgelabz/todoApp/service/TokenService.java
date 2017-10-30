package com.bridgelabz.todoApp.service;

import com.bridgelabz.todoApp.entity.Token;

public interface TokenService {
	
	public Token generateToken(String tokenType, int uid);
	
	public boolean verifyToken(String tokenValue);

}
