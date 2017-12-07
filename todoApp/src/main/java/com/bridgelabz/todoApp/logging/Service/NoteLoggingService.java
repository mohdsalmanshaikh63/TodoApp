package com.bridgelabz.todoApp.logging.Service;

import com.bridgelabz.todoApp.logging.entity.Operation;
import com.bridgelabz.todoApp.notes.entity.Note;

public interface NoteLoggingService {

	public void createLog(Note note, Operation operation);
}
