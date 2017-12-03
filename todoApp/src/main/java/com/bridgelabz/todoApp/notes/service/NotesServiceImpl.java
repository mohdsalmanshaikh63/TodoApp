package com.bridgelabz.todoApp.notes.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.notes.dao.NotesDao;
import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.user.entity.User;
import com.bridgelabz.todoApp.user.service.UserService;

@Service
public class NotesServiceImpl implements NoteService {

	@Autowired
	NotesDao notesDao;

	@Autowired
	UserService userService;

	@Autowired
	NoteLinkService noteLinkService;

	@Override
	@Transactional
	public int createNote(Note note, int uId) throws Exception {

		Date currentDateTime = new Date();
		note.setCreateTime(currentDateTime);
		note.setModifyTime(currentDateTime);

		Note createdNote = notesDao.createNote(note, uId);

		// pass this note to NoteLinkService to create and save link metadata

		/*
		 * Set<NoteLink> noteLinks =
		 * noteLinkService.extractLinks(note.getDescription());
		 * 
		 * if (noteLinks != null) { note.setNoteLinks(noteLinks); }
		 */
		
		noteLinkService.createNoteLinks(createdNote);
		
		return createdNote.getNoteId();

	}

	@Override
	@Transactional
	public void updateNote(Note note, int userId) {

		Date currentDateTime = new Date();
		note.setModifyTime(currentDateTime);

		User user = userService.getUser(userId);
		note.setUser(user);

		notesDao.updateNote(note);

	}

	@Override
	@Transactional
	public void deleteNote(int noteId) {

		notesDao.deleteNote(noteId);

	}

	@Override
	@Transactional
	public Note getNote(int noteId) {

		return notesDao.getNote(noteId);
	}

	@Override
	@Transactional
	public List<Note> getAllNotes(int userId) {
		return notesDao.getAllNotes(userId);
	}

	@Override
	@Transactional
	public int deleteTrash() {

		return notesDao.deleteTrash();
	}

}
