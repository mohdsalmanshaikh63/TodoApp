package com.bridgelabz.todoApp.token.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.todoApp.token.dao.TokenDao;
import com.bridgelabz.todoApp.token.entity.Token;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private TokenDao tokenDao;

	@Override
	public Token generateToken(String tokenType, int uid) {
		
		return tokenDao.generateToken(tokenType, uid);
	}

	@Override
	public int verifyToken(String token) {

		return tokenDao.verifyToken(token);
	}

}
