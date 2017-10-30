package com.bridgelabz.restApiDemo.dao;

import com.bridgelabz.restApiDemo.entity.Token;

public interface TokenDao {

	public Token generateToken(String tokenType, int uid);

	public boolean verifyToken(String token);
}
