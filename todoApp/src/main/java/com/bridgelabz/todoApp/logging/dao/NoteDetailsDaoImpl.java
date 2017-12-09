package com.bridgelabz.todoApp.logging.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.logging.entity.NoteDetails;

@Repository
public class NoteDetailsDaoImpl implements NoteDetailsDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	Logger logger = Logger.getLogger(NoteDetailsDaoImpl.class);

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
	
	@SuppressWarnings("deprecation")
	@Override
	public Long getDetailsCount(boolean containsText, boolean containsLinks, boolean containsImage) {
		
		Long count = 0L;
		
		Session session = sessionFactory.getCurrentSession();
		
		Criteria criteria = session.createCriteria(NoteDetails.class)
				.add(Restrictions.eq("containsText", containsText))
				.add(Restrictions.eq("containsLinks", containsLinks))
				.add(Restrictions.eq("containsImage", containsImage))
				.setProjection(Projections.rowCount());
		
		 count = (Long)criteria.uniqueResult();
		
		System.out.println(count);
												
		return count;
		
	}
	
	
	
}
