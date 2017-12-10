package com.bridgelabz.todoApp.notes.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.notes.DTO.NoteCountTest;
import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.user.entity.User;
import com.bridgelabz.todoApp.user.service.UserService;
import com.bridgelabz.todoApp.utilities.DateUtility;

@Repository
public class NotesDaoImpl implements NotesDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	UserService userService;

	private static Logger logger = Logger.getLogger(NotesDaoImpl.class);

	@Override
	public Note createNote(Note note, int uId) {

		Session session = sessionFactory.getCurrentSession();

		// optimize this if possible

		User user = session.get(User.class, uId);

		note.setUser(user);

		session.save(note);

		return note;

	}

	@Override
	public void updateNote(Note note) {

		Session session = sessionFactory.getCurrentSession();

		session.update(note);

	}

	@Override
	public void deleteNote(int noteId) {

		Session session = sessionFactory.getCurrentSession();

		@SuppressWarnings("rawtypes")
		Query deleteNote = session.createQuery("delete from Note where noteId= :noteId");

		deleteNote.setParameter("noteId", noteId);

		deleteNote.executeUpdate();

	}

	@Override
	public Note getNote(int noteId) {

		Session session = sessionFactory.getCurrentSession();

		Note note = session.get(Note.class, noteId);

		session.detach(note);

		//note.setUser(null);

		return note;
	}

	public List<Note> getAllNotes(int userId) {

		User user = userService.getUser(userId);

		logger.debug("Got the user: " + user);

		Session session = sessionFactory.getCurrentSession();

		Query<Note> getAllNotes = session.createQuery("from Note where user_id=:userId", Note.class);

		getAllNotes.setParameter("userId", userId);

		List<Note> notesList = getAllNotes.getResultList();

		// used transformer for setting user object to null
		/*
		 * @SuppressWarnings("deprecation") Criteria criteria =
		 * session.createCriteria(Note.class)
		 * .setProjection(Projections.projectionList()
		 * .add(Projections.property("noteId"), "noteId")
		 * .add(Projections.property("title"), "title")
		 * .add(Projections.property("description"), "description")
		 * .add(Projections.property("createTime"), "createTime"))
		 * .add(Restrictions.eq("user", user))
		 * .setResultTransformer(Transformers.aliasToBean(Note.class));
		 * 
		 * @SuppressWarnings("unchecked") List<Note> notesList = criteria.list();
		 */

		//logger.info("*****Getting Notes List:" + notesList);

		return notesList;
	}

	@Override
	public int deleteTrash() {

		Session session = sessionFactory.getCurrentSession();

		Date deleteTime = DateUtility.subtractDays(new Date(), 7);

		boolean trash = true;

		@SuppressWarnings("rawtypes")
		Query deleteNote = session
				.createQuery("delete from Note where modifyTime < :deleteTime and trash=:trash");
		deleteNote.setParameter("deleteTime", deleteTime);
		deleteNote.setParameter("trash", trash);

		int count = deleteNote.executeUpdate();

		return count;
	}
	
	@Override
	public List<NoteCountTest> getNotesCountByDate() {

		Session session = sessionFactory.getCurrentSession();
							
		@SuppressWarnings("deprecation")
		Criteria criteria = session.createCriteria(Note.class)
				.setProjection(Projections.projectionList()
						//.add(Projections.groupProperty("createTime"),"date")
						.add(Projections.sqlGroupProjection("date(create_time) as date", "date", new String[] { "date" }, new Type[] { StandardBasicTypes.DATE }))
						.add(Projections.rowCount(), "count"))
				.setResultTransformer(Transformers.aliasToBean(NoteCountTest.class));				

		@SuppressWarnings("unchecked")
		List<NoteCountTest> noteCountList = criteria.list();
		
		logger.info("Got the notecount list as "+noteCountList);
		
		return noteCountList;
	}
	
	

}
