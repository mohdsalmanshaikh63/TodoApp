package com.bridgelabz.restApiDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.restApiDemo.dao.UserDao;
import com.bridgelabz.restApiDemo.entity.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private  UserDao userDao;

	@Override
	@Transactional
	public boolean register(User user) {
		
		return userDao.registerUser(user);
	}

	@Override
	@Transactional
	public boolean login(String userName, String password) {
		
		return userDao.login(userName, password);
	}

}
