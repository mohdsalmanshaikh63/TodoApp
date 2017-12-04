package com.bridgelabz.todoApp.notes.service;

import java.util.List;

import com.bridgelabz.todoApp.notes.entity.Note;

public interface NoteService {
	
	public int createNote(Note note, int uId) throws Exception;
	
	public void updateNote(Note note, int userId) throws Exception;
	
	public void deleteNote(int noteId);
	
	public Note getNote(int noteId);
	
	public List<Note> getAllNotes(int uId);

	public int deleteTrash();

}
