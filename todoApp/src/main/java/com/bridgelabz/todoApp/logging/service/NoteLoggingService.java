package com.bridgelabz.todoApp.logging.service;

import java.util.List;

import com.bridgelabz.todoApp.logging.DTO.NotesWithOperation;
import com.bridgelabz.todoApp.logging.entity.NoteLog;
import com.bridgelabz.todoApp.logging.entity.Operation;
import com.bridgelabz.todoApp.notes.entity.Note;

public interface NoteLoggingService {

	public void createLog(Note note, Operation operation);
	
	public List<NoteLog> getAllNoteLogs();

	public List<Object[]> getNotesByOperationType();
}
