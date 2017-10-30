package com.bridgelabz.todoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.dao.TokenDao;
import com.bridgelabz.todoApp.entity.Token;

@Service
public class TokenServiceImpl implements TokenService {
	
	@Autowired
	private TokenDao tokenDao;

	@Override
	public Token generateToken(String tokenType, int uid) {
		
		return tokenDao.generateToken(tokenType, uid);
	}

	@Override
	public boolean verifyToken(String token) {

		return tokenDao.verifyToken(token);
	}

}