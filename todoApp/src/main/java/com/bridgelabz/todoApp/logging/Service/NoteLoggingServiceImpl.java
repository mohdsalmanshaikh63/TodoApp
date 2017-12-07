package com.bridgelabz.todoApp.logging.Service;

import org.springframework.stereotype.Service;

import com.bridgelabz.todoApp.logging.entity.Operation;
import com.bridgelabz.todoApp.notes.entity.Note;

@Service
public class NoteLoggingServiceImpl implements NoteLoggingService {

	@Override
	public void createLog(Note note, Operation operation) {

		switch (operation) {
		case CREATE:
			
			// first createNoteDetails
			
			break;

		case UPDATE:

			break;

		case DELETE:

			break;

		default:
			break;
		}

	}

}
