package com.bridgelabz.restApiDemo.dao;


import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.restApiDemo.entity.Note;
import com.bridgelabz.restApiDemo.entity.User;

public class NotesDaoImpl implements NotesDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(NotesDaoImpl.class);

	@Override
	public void createNote(Note note, int uId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		// optimize this if possible
		
		User user = session.get(User.class, uId);
		
		note.setUser(user);
		
		session.save(note);
				

	}

	@Override
	public void updateNote(Note note) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(note);

	}

	@Override
	public void deleteNote(int noteId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		
		Query deleteNote = session.createQuery("delete from Notes where noteId=:noteId");
		
		deleteNote.setParameter("noteId", noteId);
		
		deleteNote.executeUpdate();

	}

	@Override
	public Note getNote(int noteId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Note note = session.get(Note.class, noteId);
		
		return note;
	}

}
