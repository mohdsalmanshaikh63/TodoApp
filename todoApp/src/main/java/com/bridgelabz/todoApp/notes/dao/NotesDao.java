package com.bridgelabz.todoApp.notes.dao;

import java.util.List;

import com.bridgelabz.todoApp.notes.DTO.NoteActionCount;
import com.bridgelabz.todoApp.notes.entity.Note;

public interface NotesDao {

public Note createNote(Note note, int uId);
	
	public void updateNote(Note note);
	
	public void deleteNote(int noteId);
	
	public Note getNote(int noteId);
	
	public List<Note> getAllNotes(int uId);

	public int deleteTrash();
	
	public NoteActionCount getNotesCountByDate(String category);
}
