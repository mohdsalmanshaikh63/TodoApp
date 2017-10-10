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
	@Transactional
	public Token generateToken() {
		
		return tokenDao.generateToken();
	}

	@Override
	public boolean verifyToken(String token) {

		return tokenDao.verifyToken(token);
	}

}
