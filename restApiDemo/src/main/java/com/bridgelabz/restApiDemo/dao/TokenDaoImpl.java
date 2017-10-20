package com.bridgelabz.restApiDemo.dao;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.Logger;
import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.bridgelabz.restApiDemo.entity.Token;

@Repository
public class TokenDaoImpl implements TokenDao {

	// private final String key = "token:";

	Logger logger = Logger.getLogger(TokenDaoImpl.class);

	// private RedisTemplate<String, Object> redisTemplate;

	RedissonClient redissonClient;

	RMapCache<String, Token> tokenMap;

	// private HashOperations<String, String, Token> hashOps;

	@PostConstruct
	public void initRedisson() {

		Config config = null;
		try {
			config = Config.fromJSON("{\n" + "   \"singleServerConfig\":{\n"
					+ "      \"idleConnectionTimeout\":10000,\n" + "      \"pingTimeout\":1000,\n"
					+ "      \"connectTimeout\":10000,\n" + "      \"timeout\":3000,\n" + "      \"retryAttempts\":3,\n"
					+ "      \"retryInterval\":1500,\n" + "      \"reconnectionTimeout\":3000,\n"
					+ "      \"failedAttempts\":3,\n" + "      \"password\":null,\n"
					+ "      \"subscriptionsPerConnection\":5,\n" + "      \"clientName\":null,\n"
					+ "      \"address\": \"redis://127.0.0.1:6379\",\n"
					+ "      \"subscriptionConnectionMinimumIdleSize\":1,\n"
					+ "      \"subscriptionConnectionPoolSize\":50,\n" + "      \"connectionMinimumIdleSize\":10,\n"
					+ "      \"connectionPoolSize\":64,\n" + "      \"database\":0,\n"
					+ "      \"dnsMonitoring\":false,\n" + "      \"dnsMonitoringInterval\":5000\n" + "   },\n"
					+ "   \"threads\":0,\n" + "   \"nettyThreads\":0,\n" + "   \"codec\":null,\n"
					+ "   \"useLinuxNativeEpoll\":false\n" + "}");
		} catch (IOException e1) {
			logger.info("******Inside Config Catch");
			e1.printStackTrace();
		}

		logger.info("**************Config Object" + config);

		redissonClient = Redisson.create(config);

		tokenMap = redissonClient.getMapCache("tokenMap");

		/*
		 * finally {
		 * 
		 * redissonClient.shutdown(); }
		 */

	}
	
	@PreDestroy
	public void redissonShutdown() {
		redissonClient.shutdown();
	}

	public TokenDaoImpl() {

	}

	@Override
	public Token generateToken(String tokenType, int uid) {

		// first generate the token
		String tokenValue = UUID.randomUUID().toString().replaceAll("-", "");
		logger.info("******Generated access token is " + tokenValue);

		Token token = new Token(tokenType, tokenValue, uid);

		// save the token in redis cache with expiration depending upon tokenType
		switch (tokenType) {
		case "accessToken":
			tokenMap.put(tokenValue, token, 15, TimeUnit.MINUTES);
			break;

		case "refreshToken":
			tokenMap.put(tokenValue, token, 30, TimeUnit.MINUTES);
			break;

		case "forgotToken":
			tokenMap.put(tokenValue, token, 30, TimeUnit.MINUTES);
			break;

		default:
			logger.info("**********Please specify correct token type!");
			break;
		}

		return token;
	}

	@Override
	public boolean verifyToken(String tokenValue) {

		Token token = tokenMap.get(tokenValue);

		logger.info("******Token from redis for verification" + token);

		if (token != null) {
			return true;
		}

		return false;
	}

}
