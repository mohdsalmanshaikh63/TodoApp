package com.bridgelabz.restApiDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.restApiDemo.dao.TokenDao;
import com.bridgelabz.restApiDemo.entity.Token;

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
