package com.bridgelabz.restApiDemo.service;

import com.bridgelabz.restApiDemo.entity.Token;

public interface TokenService {
	
	public Token generateToken();
	
	public boolean verifyToken(String token);

}
