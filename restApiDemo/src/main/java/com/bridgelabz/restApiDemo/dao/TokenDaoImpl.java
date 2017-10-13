package com.bridgelabz.restApiDemo.dao;

import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgelabz.restApiDemo.entity.Token;

@Repository
public class TokenDaoImpl implements TokenDao {
	
	private final String key = "token:"; 
	
	Logger logger = Logger.getLogger(TokenDaoImpl.class);

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	private HashOperations<String, String, Token> hashOps;
	
	@Override
	public Token generateToken() {
		
		// first generate the token
		String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
		logger.info("******Generated access token is "+accessToken);
		
		// add constructors here later for cleaner code
		Token token = new Token();
		token.setAccessToken(accessToken);
														
		// save the token in redis cache also!
		hashOps = redisTemplate.opsForHash();
		hashOps.put(key, accessToken, token);
		
		return token;
	}

	@Override
	public boolean verifyToken(String accessToken) {
		
		hashOps = redisTemplate.opsForHash();
		Token token = hashOps.get(key, accessToken);
		
		logger.info("******Token from redis for verification"+token);
		
		if(token != null) {
			return true;
		}
		
		return false;
	}

}
