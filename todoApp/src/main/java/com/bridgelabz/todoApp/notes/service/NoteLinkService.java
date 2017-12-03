package com.bridgelabz.todoApp.notes.service;

import java.util.Set;

import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.entity.NoteLink;

public interface NoteLinkService {

	public Set<NoteLink> extractLinks(String string) throws Exception;
	
	public void createNoteLinks(Note note) throws Exception;

}
