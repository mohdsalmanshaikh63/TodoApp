package com.bridgelabz.restApiDemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.restApiDemo.dao.NotesDao;
import com.bridgelabz.restApiDemo.entity.Note;

@Service
public class NotesServiceImpl implements NoteService {
	
	@Autowired
	NotesDao notesDao;

	@Override
	@Transactional
	public void createNote(Note note, int uId) {
		
		notesDao.createNote(note, uId);

	}

	@Override
	@Transactional
	public void updateNote(Note note) {
		
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

}
