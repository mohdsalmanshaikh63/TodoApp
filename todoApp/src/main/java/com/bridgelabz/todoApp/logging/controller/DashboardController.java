package com.bridgelabz.todoApp.logging.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.logging.DTO.NoteCount;
import com.bridgelabz.todoApp.logging.entity.NoteLog;
import com.bridgelabz.todoApp.logging.service.NoteDetailsService;
import com.bridgelabz.todoApp.logging.service.NoteLoggingService;
import com.bridgelabz.todoApp.notes.DTO.NoteCountTest;
import com.bridgelabz.todoApp.notes.service.NoteService;

@RestController
@RequestMapping(value="/admin")
public class DashboardController {
	
	@Autowired
	NoteLoggingService noteLoggingService;
	
	@Autowired
	NoteDetailsService noteDetailsService;
	
	@Autowired
	NoteService noteService;
	
	Logger logger = Logger.getLogger(DashboardController.class);
	
	@GetMapping(value="/getAllNoteLogs")
	public ResponseEntity<List<NoteLog>> getAllNoteLog() {
		
		try {
			
			List<NoteLog> noteLogList = noteLoggingService.getAllNoteLogs();			
			
			return new ResponseEntity<List<NoteLog>>(noteLogList, HttpStatus.OK);
			
		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity<List<NoteLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value="/getNotesWithOperations")
	public ResponseEntity<List<Object[]>> getNotesWithOperations() {
		
		try {
			
			List<Object[]> notesWithOperation = noteLoggingService.getNotesByOperationType();
			
			logger.info("Success in getting notes with operations");
			
			noteDetailsService.getCount();
			
			return new ResponseEntity<List<Object[]>>(notesWithOperation, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Object[]>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/getNoteDetailsCount")
	public ResponseEntity<List<NoteCount>> getNoteDetailsCount() {
		
		try {
			
			List<NoteCount> noteCountList = noteDetailsService.getCount();
			
			return new ResponseEntity<List<NoteCount>>(noteCountList, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("******Error while getting noteDetailsCount");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value="/getNotesCountByDate")
	public ResponseEntity<List<NoteCountTest>> getNotesCountByDate() {
		
		try {
			
			List<NoteCountTest> noteCountList = noteService.getNotesCountByDate();
			
			return new ResponseEntity<List<NoteCountTest>>(noteCountList, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("******Error while getting noteDetailsCount");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
