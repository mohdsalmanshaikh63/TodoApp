package com.bridgelabz.todoApp.token.dao;

import com.bridgelabz.todoApp.token.entity.Token;

public interface TokenDao {

	public Token generateToken(String tokenType, int uid);

	public int verifyToken(String token);
}
