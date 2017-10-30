package com.bridgelabz.redissonApp;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class App {
	public static void main(String[] args) {
		Config config = new Config();

		config.useSingleServer().setAddress("127.0.0.1:6379");

		// LocalCachedMapOptions localCachedMapOptions =
		// LocalCachedMapOptions.defaults()
		// .evictionPolicy(EvictionPolicy.LFU);

		RedissonClient redisson = Redisson.create(config);

		try {

			RMapCache<Integer, Token> map = redisson.getMapCache("TestMap");

			Token myToken = new Token("iqbal", 2);

			map.put(2, myToken, 30, TimeUnit.SECONDS);

			System.out.println("Stored value with key 1 is: " + map.get(2));

		}

		finally {

			redisson.shutdown();

		}

	}
}
