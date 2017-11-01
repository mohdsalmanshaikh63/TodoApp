package com.bridgelabz.todoApp.dao;

import com.bridgelabz.todoApp.entity.Token;

public interface TokenDao {

	public Token generateToken(String tokenType, int uid);

	public int verifyToken(String token);
}
