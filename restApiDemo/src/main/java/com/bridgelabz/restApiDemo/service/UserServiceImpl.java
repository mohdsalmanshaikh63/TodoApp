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
	public boolean createUser(User user) {
		
		return userDao.registerUser(user);
	}

	@Override
	@Transactional
	public boolean login(String userName, String password) {
		
		return userDao.login(userName, password);
	}

	@Override
	@Transactional
	public boolean activate(int id) {
		
		return userDao.activate(id);
	}

	@Override
	public User getUser(int id) {
		
		return userDao.getUser(id);
	}

	@Override
	public int getUserId(String email) {
		return userDao.getUserId(email);
	}

}
