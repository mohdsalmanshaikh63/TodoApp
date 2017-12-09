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

		/*@SuppressWarnings({ "unchecked" })
		Query<Object[]> noteLogQuery = session.createQuery("select count(n) from NoteLog n ");
		// having date >" + date + " and operation=" + operation);

		List<Object[]> noteLogList = noteLogQuery.getResultList();*/
		
		/*Criteria criteria = session.createCriteria(NoteLog.class)
				//.add(Restrictions.ge("date", date))
				.setProjection(Projections.projectionList()
						.add(Projections.property("date"))
						.add(Projections.property("operation"))
						.add(Projections.count("noteLogId"), "total")
						.add(Projections.groupProperty("date"))
						.add(Projections.groupProperty("operation"))
						)
				.setResultTransformer(Transformers.aliasToBean(NotesWithOperation.class));*/
		
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
				
		/*@SuppressWarnings("unchecked")
		List<NotesWithOperation> notesWithOperation = criteria.list();*/
		return listNoteLog;
	}

}
