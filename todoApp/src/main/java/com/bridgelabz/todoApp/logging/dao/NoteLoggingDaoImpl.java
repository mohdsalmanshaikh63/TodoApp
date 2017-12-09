package com.bridgelabz.todoApp.logging.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.logging.entity.NoteLog;
import com.bridgelabz.todoApp.logging.entity.Operation;
import com.bridgelabz.todoApp.utilities.DateUtility;

@SuppressWarnings("deprecation")
@Repository
public class NoteLoggingDaoImpl implements NoteLoggingDao {

	@Autowired
	SessionFactory sessionFactory;

	@Override
	public void saveNoteLog(NoteLog noteLog) {

		Session session = sessionFactory.getCurrentSession();

		session.save(noteLog);
	}

	@Override
	public List<NoteLog> getAllNoteLog() {

		Session session = sessionFactory.getCurrentSession();

		Query<NoteLog> noteLogQuery = session.createQuery("from NoteLog", NoteLog.class);

		List<NoteLog> noteLogList = noteLogQuery.getResultList();

		return noteLogList;
	}

	@Override
	public List<Object[]> getNotesByOperationType(Operation operation, int lastNDays) {

		Session session = sessionFactory.getCurrentSession();

		Date date = DateUtility.subtractDays(new Date(), lastNDays);
		
		// optimized code with DTO use this later and change front end accordingly
/*		Criteria criteria = session.createCriteria(NoteLog.class)
				.add(Restrictions.ge("date", date))
				.setProjection(Projections.projectionList()
						.add(Projections.count("noteLogId"), "count")
						.add(Projections.groupProperty("date"),"date")
						.add(Projections.groupProperty("operation"), "operation")
						
				 )
				.setResultTransformer(Transformers.aliasToBean(NotesWithOperation.class));
			
		@SuppressWarnings("unchecked")
		List<NotesWithOperation> notesWithOperation = criteria.list();
		System.out.println( notesWithOperation );
		
		notesWithOperation.forEach(noteList ->{ 
			System.out.println("******Got list count as"+noteList.toString());
		
		});*/
		
		// raw object code
		Criteria criteria = session.createCriteria(NoteLog.class)
				.add(Restrictions.ge("date", date))
				.setProjection(Projections.projectionList()
						.add(Projections.property("date"))
						.add(Projections.property("operation"))
						.add(Projections.count("noteLogId"), "total")
						.add(Projections.groupProperty("date"))
						.add(Projections.groupProperty("operation"))
						);
		
		@SuppressWarnings("unchecked")
		List<Object[]> listNoteLog = criteria.list();
		
		for (Object[] objects : listNoteLog) {
			System.out.println("******Objects at"+objects[2]);
		}
		
		System.out.println("******Getting NoteLog list as"+listNoteLog);
				
		
		return listNoteLog;
	}

}
