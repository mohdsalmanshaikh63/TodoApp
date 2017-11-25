package com.bridgelabz.todoApp.notes.dao;

import java.util.List;

import com.bridgelabz.todoApp.notes.entity.Note;

public interface NotesDao {

public int createNote(Note note, int uId);
	
	public void updateNote(Note note);
	
	public void deleteNote(int noteId);
	
	public Note getNote(int noteId);
	
	public List<Note> getAllNotes(int uId);
}
