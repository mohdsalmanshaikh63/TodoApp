package com.bridgelabz.restApiDemo.dao;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.restApiDemo.entity.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean registerUser(User user) {
		
		/*// since our annotations will take care of the remaining fields
		// check password and confirm password
		
		String password = user.getPassword();
		String confirmPassword = user.getConfirmPassword();
		
		// if both are same then register the user
		if(password.equals(confirmPassword)) {*/
			user.setValid(true);
			
			Session session = sessionFactory.getCurrentSession();
			
			session.save(user);
			
			return true;
			
		//}
		
		
	}

	@Override
	public boolean login(String email, String password) {		
		
		// hibernate code here;
		
		Session session = sessionFactory.getCurrentSession();
		
		// get user object from db
		Query query = session.createQuery("from User where email= :email and password=:password", User.class)
						.setParameter("email", email)
						.setParameter("password", password);
						
		// check this what it returns or throws an exception
		// handle this properly later if any problem
		
		User user = (User) query.getSingleResult();
		
		if(user != null) {
			if(user.isValid()) {
				return true;
			}
			
		}
				
		return false;
	}

}
