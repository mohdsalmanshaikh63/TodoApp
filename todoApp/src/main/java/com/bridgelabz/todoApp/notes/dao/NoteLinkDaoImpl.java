package com.bridgelabz.todoApp.notes.dao;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.entity.NoteLink;

@Repository
public class NoteLinkDaoImpl implements NoteLinkDao {
	
	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveNoteLinks(Set<NoteLink> noteLinks, Note note) {
		
		Session session = sessionFactory.getCurrentSession();
		
		// Java 8 forEach Loop!
		noteLinks.forEach(noteLink -> {
			noteLink.setNote(note);
			session.save(noteLink);
		});
		
	}

}
