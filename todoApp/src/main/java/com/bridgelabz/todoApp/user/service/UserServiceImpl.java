package com.bridgelabz.todoApp.user.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.mailUtility.MailUtility;
import com.bridgelabz.todoApp.token.entity.Token;
import com.bridgelabz.todoApp.token.service.TokenService;
import com.bridgelabz.todoApp.user.dao.UserDao;
import com.bridgelabz.todoApp.user.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	MailUtility mailUtility;

	@Autowired
	private TokenService tokenService;

	Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Override
	@Transactional
	public int createUser(User user, String link)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		int userId = userDao.registerUser(user);

		// This is for normal registration through our app
		// if user is being created by facebook no need to send email

		if (link != null) {
			// generate Token in redis and attach it to the url
			Token activationToken = tokenService.generateToken("activateToken", userId);

			// Finally send the mail!
			String messageBody = link + activationToken.getValue() + " This link will expirein one day.";
			mailUtility.sendMail(user.getEmail(), "Activate your account", messageBody);

		}

		return userId;
	}

	@Override
	@Transactional
	public Map<String, Token> login(User user) {

		int uid = userDao.login(user);

		if (uid != -1) {

			// generate and save access and refresh tokens in redis
			Token accessToken = tokenService.generateToken("accessToken", uid);
			Token refreshToken = tokenService.generateToken("refreshToken", uid);

			Map<String, Token> tokenMap = new HashMap<>();
			tokenMap.put("accessToken", accessToken);
			tokenMap.put("refreshToken", refreshToken);
			
			return tokenMap;

		}

		return null;
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
	@Transactional(readOnly = true)
	public User getUser(int id) {

		return userDao.getUser(id);
	}

	@Override
	@Transactional(readOnly = true)
	public int getUserId(String email) {
		return userDao.getUserId(email);
	}

	@Override
	@Transactional(readOnly = true)
	public int checkUser(String email) {

		return userDao.getUserId(email);
	}

	@Override
	@Transactional
	public boolean changePassword(int id, User user) {

		return userDao.changePassword(id, user);
	}

	@Override
	@Transactional
	public int saveUser(User user) {

		return userDao.saveUser(user);
	}

	@Override
	@Transactional(readOnly=true)
	public Token forgotPassword(User user, String link)
			throws FileNotFoundException, ClassNotFoundException, IOException, URISyntaxException {

		String email = user.getEmail();

		int uid = checkUser(email);

		// send email if the user exists
		if (uid != -1) {

			// Generate a token and send in the email
			Token token = tokenService.generateToken("forgotToken", uid);

			logger.info("********Token Generated: " + token + "for email: " + email);

			// send the email the user with the token in the url

			// prepare the url for sending activation mail

			String resultPath = link + "/#/resetpassword/" + token.getValue();

			String messageBody = "Click here to reset ur password " + resultPath;

			// Finally send the mail!
			mailUtility.sendMail(email, "Token Login", messageBody);

			return token;
		}

		return null;

	}

	@Override
	@Transactional
	public boolean resetPassword(String token, User user) {
		
		int userId = tokenService.verifyToken(token);

		if (userId != -1) {

			return changePassword(userId, user);

		}
		
		return false;
	}
	
	

}
