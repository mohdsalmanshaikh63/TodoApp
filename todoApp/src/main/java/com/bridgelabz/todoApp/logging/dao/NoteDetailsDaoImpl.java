package com.bridgelabz.todoApp.logging.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.logging.entity.NoteDetails;

@Repository
public class NoteDetailsDaoImpl implements NoteDetailsDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public NoteDetails saveNoteDetails(NoteDetails noteDetails) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.save(noteDetails);
		
		return noteDetails;
	}

	@Override
	public NoteDetails getNoteDetails(int noteId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		NoteDetails noteDetails = session.get(NoteDetails.class, noteId);
				
		return noteDetails;
	}

	
}
