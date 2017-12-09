package com.bridgelabz.todoApp.logging.service;

import org.springframework.stereotype.Service;

import com.bridgelabz.todoApp.logging.entity.NoteDetails;
import com.bridgelabz.todoApp.notes.entity.Note;

@Service
public interface NoteDetailsService {
	
	public NoteDetails createNoteDetails(Note note);
	
	public NoteDetails getNoteDetails(int noteId);

	public Long getCount();
	

}
