package com.bridgelabz.todoApp.user.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.user.entity.User;

import jodd.util.BCrypt;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public int registerUser(User user) {

		// first check if the user already exists
		if (checkUser(user.getEmail()) != -1) {
			return -1;
		} else {

			String password = user.getPassword();

			if (password != null) {

				String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

				user.setPassword(hashedPassword);

			}

			return saveUser(user);

		}

	}

	// changed this from save to save or update check if it works properly
	@Override
	public int saveUser(User user) {
		Session session = sessionFactory.getCurrentSession();

		session.saveOrUpdate(user);
		
		// check if this fires another query or not if yes the revert to save and make seperate
		// method for update

		return user.getUserId();
	}

	@Override
	public int login(User user) {

		String email = user.getEmail();
		String password = user.getPassword();

		// basic checks
		if (email == null || email == "" || password == null || password == "") {
			return -1;
		}

		// hibernate code here;

		Session session = sessionFactory.getCurrentSession();

		// get user object from db
		Query<User> query = session.createQuery("from User where email= :email", User.class).setParameter("email",
				user.getEmail());

		// check this what it returns or throws an exception
		// handle this properly later if any problem DONE!

		try {
			User aUser = (User) query.getSingleResult();

			if (aUser.isValid() == false) {
				return -1;
			} else {

				String hashedPassword = aUser.getPassword();

				if (BCrypt.checkpw(password, hashedPassword)) {
					logger.info("Passwords match!");
					return aUser.getUserId();
				} else {
					logger.info("Passwords do not match");
					return -1;
				}
			}

		} catch (Exception e) {

			logger.info("No such user exists");
			return -1;

		}
	}

	@Override
	public boolean activate(int id) {

		User theUser = getUser(id);

		if (theUser != null) {

			// change the isValid field
			theUser.setValid(true);

			// get current hibernate session
			Session currentSession = sessionFactory.getCurrentSession();

			// save/upate the User
			currentSession.saveOrUpdate(theUser);

			return true;
		}

		logger.debug("No such user exists. Failed to activate");

		return false;
	}

	@Override
	public User getUser(int id) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// now retrieve/read from database using the primary key
		User theUser = currentSession.get(User.class, id);

		logger.debug("Got user " + theUser);

		return theUser;

	}

	@Override
	public int getUserId(String email) {

		Session session = sessionFactory.getCurrentSession();

		// get user object from db
		org.hibernate.query.Query<User> query = session.createQuery("from User where email= :email", User.class)
				.setParameter("email", email);

		// check this what it returns or throws an exception
		// handle this properly later if any problem
		User user;

		try {
			user = query.getSingleResult();
			return user.getUserId();
		} catch (Exception e) {
			return -1;
		}

	}

	@Override
	public int checkUser(String email) {

		Session session = sessionFactory.getCurrentSession();

		// get user object from db
		Query<User> query = session.createQuery("from User where email= :email", User.class).setParameter("email",
				email);

		User user = null;

		try {
			user = (User) query.getSingleResult();
		} catch (Exception e) {
			// do nothing here
		}

		if (user != null) {

			return user.getUserId();
		}

		return -1;
	}

	@Override
	public boolean changePassword(int id, User pUser) {
		
		User user = getUser(id);
		
		String password = pUser.getPassword();
		
		if (password != null) {

			String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

			user.setPassword(hashedPassword);
			
			int result = saveUser(user);
			
			if(result != -1) {
			
				return true;
			}
						
		}
			
		
		return false;
	}

}
