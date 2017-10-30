package com.bridgelabz.todoApp.service;

import java.util.List;

import com.bridgelabz.todoApp.entity.Note;

public interface NoteService {
	
	public void createNote(Note note, int uId);
	
	public void updateNote(Note note);
	
	public void deleteNote(int noteId);
	
	public Note getNote(int noteId);
	
	public List<Note> getAllNotes(int uId);

}