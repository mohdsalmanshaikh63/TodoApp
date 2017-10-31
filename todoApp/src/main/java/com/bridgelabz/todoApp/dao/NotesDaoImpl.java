package com.bridgelabz.todoApp.dao;


import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.query.Query;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.entity.Note;
import com.bridgelabz.todoApp.entity.User;
import com.bridgelabz.todoApp.service.UserService;

@Repository
public class NotesDaoImpl implements NotesDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired UserService userService;
	
	private static Logger logger = Logger.getLogger(NotesDaoImpl.class);

	@Override
	public void createNote(Note note, int uId) {
		
		note.setCreateTime(getLocalDateTime());
		
		Session session = sessionFactory.getCurrentSession();
		
		// optimize this if possible
		
		User user = session.get(User.class, uId);
		
		note.setUser(user);
		
		session.save(note);
				

	}

	@Override
	public void updateNote(Note note) {
		
		note.setCreateTime(getLocalDateTime());
		
		Session session = sessionFactory.getCurrentSession();
		
		session.update(note);

	}

	@Override
	public void deleteNote(int noteId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Note> deleteNote = session.createQuery("delete from Note where noteId=:noteId",Note.class);
		
		deleteNote.setParameter("noteId", noteId);
		
		deleteNote.executeUpdate();

	}

	@Override
	public Note getNote(int noteId) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Note note = session.get(Note.class, noteId);	
		
		session.detach(note);
		
		note.setUser(null);
		
		return note;
	}
	
	public List<Note> getAllNotes(int userId) {
		
		/*User user = userService.getUser(userId);
		
		logger.debug("Got the user: "+ user);*/		
		
		Session session = sessionFactory.getCurrentSession();				
		
		Query<Note> getAllNotes = session.createQuery("from Note where user_id=:userId",Note.class);
					
		getAllNotes.setParameter("userId",userId);
		
		List<Note> notesList = getAllNotes.getResultList();
		
		logger.info("*****Getting Notes List:"+notesList);
		
		return notesList;
	}
	
	public LocalDateTime getLocalDateTime() {
		return LocalDateTime.now();
	}

}
