package com.bridgelabz.todoApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.dao.UserDao;
import com.bridgelabz.todoApp.entity.User;

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
	public int login(User user) {
		
		return userDao.login(user);
	}

	@Override
	@Transactional
	public boolean activate(int id) {
		
		return userDao.activate(id);
	}

	@Override
	@Transactional
	public User getUser(int id) {
		
		return userDao.getUser(id);
	}

	@Override
	@Transactional
	public int getUserId(String email) {
		return userDao.getUserId(email);
	}

	@Override
	@Transactional
	public int checkUser(String email) {
		
		return userDao.checkUser(email);
	}

}
