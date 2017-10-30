package com.bridgelabz.restApiDemo.service;

import com.bridgelabz.restApiDemo.entity.Token;

public interface TokenService {
	
	public Token generateToken(String tokenType, int uid);
	
	public boolean verifyToken(String tokenValue);

}
