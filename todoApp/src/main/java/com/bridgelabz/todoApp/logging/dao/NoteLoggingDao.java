package com.bridgelabz.todoApp.logging.dao;

import java.util.List;

import com.bridgelabz.todoApp.logging.entity.NoteLog;
import com.bridgelabz.todoApp.logging.entity.Operation;

import utilityPojos.NotesWithOperation;

public interface NoteLoggingDao {

	public void saveNoteLog(NoteLog noteLog);
	
	public List<NoteLog> getAllNoteLog();

	List<NotesWithOperation> getNotesByOperationType(Operation operation, int lastNDays);
}
