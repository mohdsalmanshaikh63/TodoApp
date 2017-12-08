package com.bridgelabz.todoApp.logging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.logging.entity.NoteLog;
import com.bridgelabz.todoApp.logging.service.NoteDetailsService;
import com.bridgelabz.todoApp.logging.service.NoteLoggingService;

@RestController
@RequestMapping(value="/admin")
public class DashboardController {
	
	@Autowired
	NoteLoggingService noteLoggingService;
	
	@Autowired
	NoteDetailsService noteDetailsService;
	
	@RequestMapping(value="/getAllNoteLogs")
	public ResponseEntity<List<NoteLog>> getAllNoteLog() {
		
		try {
			
			List<NoteLog> noteLogList = noteLoggingService.getAllNoteLogs();
			noteLoggingService.getNotesByOperationType();
			
			return new ResponseEntity<List<NoteLog>>(noteLogList, HttpStatus.OK);
			
		} catch (Exception e) {

			return new ResponseEntity<List<NoteLog>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
