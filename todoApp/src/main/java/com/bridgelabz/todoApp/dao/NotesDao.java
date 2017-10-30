package com.bridgelabz.todoApp.dao;

import java.util.List;

import com.bridgelabz.todoApp.entity.Note;

public interface NotesDao {

public void createNote(Note note, int uId);
	
	public void updateNote(Note note);
	
	public void deleteNote(int noteId);
	
	public Note getNote(int noteId);
	
	public List<Note> getAllNotes(int uId);
}
