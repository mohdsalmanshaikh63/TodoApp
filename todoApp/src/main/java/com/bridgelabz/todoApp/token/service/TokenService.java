package com.bridgelabz.todoApp.token.service;

import com.bridgelabz.todoApp.token.entity.Token;

public interface TokenService {
	
	public Token generateToken(String tokenType, int uid);
	
	public int verifyToken(String tokenValue);

}
