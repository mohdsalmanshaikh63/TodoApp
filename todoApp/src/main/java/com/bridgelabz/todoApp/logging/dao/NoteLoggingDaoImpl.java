package com.bridgelabz.todoApp.logging.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.logging.entity.NoteLog;

@Repository
public class NoteLoggingDaoImpl implements NoteLoggingDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveNoteLog(NoteLog noteLog) {		

		Session session = sessionFactory.getCurrentSession();
		
		session.save(noteLog);
	}

}
