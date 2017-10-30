package com.bridgelabz.todoApp.dao;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private static Logger logger = Logger.getLogger(UserDaoImpl.class);

	@Override
	public boolean registerUser(User user) {
		
		// first check if the user already exists
		if(checkUser(user.getEmail()) != -1) {
			return false;
		} else {

		Session session = sessionFactory.getCurrentSession();

		session.save(user);

		return true;
		
		}

	}

	@Override
	public int login(User user) {

		// basic checks
		if (user.getEmail() == null || user.getEmail() == "" || user.getPassword() == null
				|| user.getPassword() == "") {
			return -1;
		}

		// hibernate code here;

		Session session = sessionFactory.getCurrentSession();

		// get user object from db
		Query query = session.createQuery("from User where email= :email and password=:password", User.class)
				.setParameter("email", user.getEmail()).setParameter("password", user.getPassword());

		// check this what it returns or throws an exception
		// handle this properly later if any problem

		User aUser = (User) query.getSingleResult();

		if (aUser != null && aUser.isValid()) {
			return aUser.getUserId();
		}

		return -1;
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
		Query query = session.createQuery("from User where email= :email", User.class).setParameter("email", email);

		// check this what it returns or throws an exception
		// handle this properly later if any problem

		User user = (User) query.getSingleResult();

		return user.getUserId();
	}

	@Override
	public int checkUser(String email) {

		Session session = sessionFactory.getCurrentSession();

		// get user object from db
		Query query = session.createQuery("from User where email= :email", User.class).setParameter("email", email);
		
		User user = null;
		
		try {
		user = (User) query.getSingleResult();
		} catch (Exception e) {
			// do nothing here
		}
		
		if(user != null) {
			
			return user.getUserId();
		}

		return -1;
	}

}
