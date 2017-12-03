package com.bridgelabz.todoApp.notes.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.todoApp.customResponse.Message;
import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.service.NoteService;

@RestController
@RequestMapping(value = "/notes")
@CrossOrigin(origins = "http://localhost:8000")
public class NotesController {

	Logger logger = Logger.getLogger(NotesController.class);

	@Autowired
	NoteService noteService;

	// add backend spring validation later
	@PutMapping(value = "/create")
	public ResponseEntity<Note> createNote(@RequestBody Note note, HttpServletRequest request) {
				

		try {
						
			
			int userId = (int) request.getAttribute("userId");			

			int noteId = noteService.createNote(note, userId);
			
			note.setNoteId(noteId);

			return new ResponseEntity<Note>(note, HttpStatus.OK);

		} catch (Exception e) {

			logger.info("Note could not be created");			
			return new ResponseEntity<Note>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// add backend spring validation later
	@PostMapping(value = "/update")
	public ResponseEntity<Message> updateNote(@RequestBody Note note,HttpServletRequest request) {

		try {
			
			//logger.info("********Got the note from front end as "+note);
			
			int userId = (int) request.getAttribute("userId");			

			noteService.updateNote(note, userId);

			return new ResponseEntity<Message>(new Message("Note updated successfully"), HttpStatus.OK);

		} catch (Exception e) {

			logger.info("Note could not be updated");
			return new ResponseEntity<Message>(new Message("Error while updating note"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(value = "/getNote/{noteId}")
	public ResponseEntity<Note> getNote(@PathVariable("noteId") int noteId) {

		try {

			Note note = noteService.getNote(noteId);
			return new ResponseEntity<Note>(note, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<Note>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/getAllNotes")
	public ResponseEntity<List<Note>> getAllNotes(HttpServletRequest request) {
		

			try {
				
				int userId = (int) request.getAttribute("userId");

				logger.info("*****Got the userId " + userId);
				
				List<Note> notesList = noteService.getAllNotes(userId);

				System.out.println();
				return new ResponseEntity<List<Note>>(notesList, HttpStatus.OK);

			} catch (Exception e) {

				e.printStackTrace();
				return new ResponseEntity<List<Note>>(HttpStatus.INTERNAL_SERVER_ERROR);

			}

		}
		

	@DeleteMapping(value = "/delete/{noteId}")
	public ResponseEntity<Message> deleteNote(@PathVariable("noteId") int noteId) {

		logger.info("****Note Id is " + noteId);

		try {

			noteService.deleteNote(noteId);
			return new ResponseEntity<Message>(new Message("Note deleted successfully"), HttpStatus.OK);

		} catch (Exception e) {

			logger.info("Error while deleting notes");
			return new ResponseEntity<Message>(new Message("Error while deleting note"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
