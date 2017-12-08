package com.bridgelabz.todoApp.logging.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.logging.dao.NoteLoggingDao;
import com.bridgelabz.todoApp.logging.entity.NoteDetails;
import com.bridgelabz.todoApp.logging.entity.NoteLog;
import com.bridgelabz.todoApp.logging.entity.Operation;
import com.bridgelabz.todoApp.notes.entity.Note;

@Service
public class NoteLoggingServiceImpl implements NoteLoggingService {
	
	@Autowired
	NoteDetailsService noteDetailsService;
	
	@Autowired
	NoteLoggingDao noteLoggingDao;

	@Override
	@Transactional
	public void createLog(Note note, Operation operation) {
		
		NoteDetails noteDetails = null;
		
		NoteLog noteLog = null;

		switch (operation) {
		
		case CREATE:
			
			// first createNoteDetails
			noteDetails = noteDetailsService.createNoteDetails(note);
			
			noteLog = new NoteLog(note.getUser().getUserId(), note.getCreateTime(), operation, noteDetails);
			
			noteLoggingDao.saveNoteLog(noteLog);
			
			break;

		case UPDATE:

			break;

		case DELETE:
			
			int noteLogId = note.getNoteId();						
			
			noteDetails = noteDetailsService.getNoteDetails(noteLogId);
			
			noteLog = new NoteLog(note.getUser().getUserId(), new Date(), operation, noteDetails);
			
			noteLoggingDao.saveNoteLog(noteLog);
			
			System.out.println("Delete Log created");

			break;

		default:
			break;
		}

	}

	@Override
	@Transactional
	public List<NoteLog> getAllNoteLogs() {
		
		return noteLoggingDao.getAllNoteLog();
	}

}
