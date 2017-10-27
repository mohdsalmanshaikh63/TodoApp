package com.bridgelabz.restApiDemo.service;

import java.util.List;

import com.bridgelabz.restApiDemo.entity.Note;

public interface NoteService {
	
	public void createNote(Note note, int uId);
	
	public void updateNote(Note note);
	
	public void deleteNote(int noteId);
	
	public Note getNote(int noteId);
	
	public List<Note> getAllNotes(int uId);

}
