package com.bridgelabz.todoApp.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.dao.UserDao;
import com.bridgelabz.todoApp.entity.Token;
import com.bridgelabz.todoApp.entity.User;
import com.bridgelabz.todoApp.mailUtility.MailUtility;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	MailUtility mailUtitlity;

	@Autowired
	private TokenService tokenService;

	@Override
	@Transactional
	public int createUser(User user, String link)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		int userId = userDao.registerUser(user);

		// This is for normal registration through our app
		// if user is being created by facebook no need to send email
		
		if(link != null) {
		// generate Token in redis and attach it to the url
		Token activationToken = tokenService.generateToken("activateToken", userId);

		// Finally send the mail!
		String messageBody = link + activationToken.getValue() + " This link will expirein one day.";
		mailUtitlity.sendMail(user.getEmail(), "Activate your account", messageBody);
		
		}

		return userId;
	}

	@Override
	@Transactional
	public int login(User user) {

		return userDao.login(user);
	}

	@Override
	@Transactional
	public boolean activate(String token) {

		int userId = tokenService.verifyToken(token);

		if (userId == -1) {
			return false;
		} else {
			return userDao.activate(userId);
		}
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
