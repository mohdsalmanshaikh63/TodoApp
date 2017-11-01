package com.bridgelabz.todoApp.dao;

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
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.entity.Token;

@Repository
public class TokenDaoImpl implements TokenDao {

	Logger logger = Logger.getLogger(TokenDaoImpl.class);

	RedissonClient redissonClient;

	RMapCache<String, Token> tokenMap;

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
			tokenMap.put(tokenValue, token, 2, TimeUnit.MINUTES);
			break;

		case "refreshToken":
			tokenMap.put(tokenValue, token, 5, TimeUnit.MINUTES);
			break;

		case "forgotToken":
			tokenMap.put(tokenValue, token, 5, TimeUnit.MINUTES);
			break;

		default:
			logger.info("**********Please specify correct token type!");
			break;
		}

		return token;
	}

	@Override
	public int verifyToken(String tokenValue) {

		Token token = tokenMap.get(tokenValue);

		logger.info("******Token from redis for verification" + token);

		if (token != null) {
			return token.getUid();
		}

		return -1;
	}

}
