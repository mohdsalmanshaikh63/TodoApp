package com.bridgelabz.restApiDemo.dao;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

	private final String key = "token:";

	Logger logger = Logger.getLogger(TokenDaoImpl.class);

	
	private RedisTemplate<String, Object> redisTemplate;
		
	RedissonClient redissonClient;

	private HashOperations<String, String, Token> hashOps;
	private BoundValueOperations<String, Object> valueOperations;
	
	public TokenDaoImpl() {
		Config config = null;
		try {
			config = Config.fromJSON("{\n" + 
					"   \"singleServerConfig\":{\n" + 
					"      \"idleConnectionTimeout\":10000,\n" + 
					"      \"pingTimeout\":1000,\n" + 
					"      \"connectTimeout\":10000,\n" + 
					"      \"timeout\":3000,\n" + 
					"      \"retryAttempts\":3,\n" + 
					"      \"retryInterval\":1500,\n" + 
					"      \"reconnectionTimeout\":3000,\n" + 
					"      \"failedAttempts\":3,\n" + 
					"      \"password\":null,\n" + 
					"      \"subscriptionsPerConnection\":5,\n" + 
					"      \"clientName\":null,\n" + 
					"      \"address\": \"redis://127.0.0.1:6379\",\n" + 
					"      \"subscriptionConnectionMinimumIdleSize\":1,\n" + 
					"      \"subscriptionConnectionPoolSize\":50,\n" + 
					"      \"connectionMinimumIdleSize\":10,\n" + 
					"      \"connectionPoolSize\":64,\n" + 
					"      \"database\":0,\n" + 
					"      \"dnsMonitoring\":false,\n" + 
					"      \"dnsMonitoringInterval\":5000\n" + 
					"   },\n" + 
					"   \"threads\":0,\n" + 
					"   \"nettyThreads\":0,\n" + 
					"   \"codec\":null,\n" + 
					"   \"useLinuxNativeEpoll\":false\n" + 
					"}");
		} catch (IOException e1) {
			logger.info("******Inside Config Catch");
			e1.printStackTrace();
		}
		
		/*config.useSingleServer().setAddress("redis://127.0.0.1:6379")
		.setReconnectionTimeout(0);*/
		
		logger.info("**************Config Object"+config);
		
		redissonClient = Redisson.create(config);
		
		try {

			 RMapCache<Integer, Token> map = redissonClient.getMapCache("TestMap");

			 Token myToken = new Token();
			 
			 myToken.setAccessToken("access2");
			 myToken.setRefreshToken("refresh2");

			 map.put(2, myToken, 1, TimeUnit.MINUTES);

			 System.out.println("Stored value with key 1 is: " + map.get(2));						

		} catch (Exception e) {
			logger.info("***********"+e);
		}

		/*finally {

			redissonClient.shutdown();
		}*/

	}

	@Override
	public Token generateToken() {

		// first generate the token
		String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
		logger.info("******Generated access token is " + accessToken);

		// add constructors here later for cleaner code
		Token token = new Token();
		token.setAccessToken(accessToken);

		// save the token in redis cache also!
		// hashOps = redisTemplate.opsForHash();
		// hashOps.put(key, accessToken, token);

		valueOperations = redisTemplate.boundValueOps(accessToken);
		valueOperations.set(true, 1, TimeUnit.MINUTES);

		return token;
	}

	@Override
	public boolean verifyToken(String accessToken) {

		 hashOps = redisTemplate.opsForHash();
		 Token token = hashOps.get(key, accessToken);
		
		

		logger.info("******Token from redis for verification" + token);

		if (token != null) {
			return true;
		}

		return false;
	}

}
