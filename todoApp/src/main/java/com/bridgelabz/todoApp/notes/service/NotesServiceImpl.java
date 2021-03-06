package com.bridgelabz.todoApp.notes.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.logging.entity.Operation;
import com.bridgelabz.todoApp.logging.service.NoteLoggingService;
import com.bridgelabz.todoApp.notes.DTO.NoteActionCount;
import com.bridgelabz.todoApp.notes.dao.NotesDao;
import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.entity.NoteLink;
import com.bridgelabz.todoApp.user.entity.User;
import com.bridgelabz.todoApp.user.service.UserService;

@Service
public class NotesServiceImpl implements NoteService {

	@Autowired
	NotesDao notesDao;

	@Autowired
	UserService userService;

	@Autowired
	NoteLinkService noteLinkService;

	@Autowired
	NoteLoggingService noteLoggingService;

	@Override
	@Transactional
	public int createNote(Note note, int uId) throws Exception {

		Date currentDateTime = new Date();
		note.setCreateTime(currentDateTime);
		note.setModifyTime(currentDateTime);

		Note createdNote = notesDao.createNote(note, uId);

		// pass this note to NoteLinkService to create and save link metadata

		/*
		 * Set<NoteLink> noteLinks =
		 * noteLinkService.extractLinks(note.getDescription());
		 * 
		 * if (noteLinks != null) { note.setNoteLinks(noteLinks); }
		 */

		Set<NoteLink> noteLinks = noteLinkService.createNoteLinks(createdNote);

		createdNote.setNoteLinks(noteLinks);

		noteLoggingService.createLog(createdNote, Operation.CREATE);

		return createdNote.getNoteId();

	}

	@Override
	@Transactional
	public void updateNote(Note note, int userId) throws Exception {

		Date currentDateTime = new Date();
		note.setModifyTime(currentDateTime);

		/*
		 * User user = userService.getUser(userId); note.setUser(user);
		 */

		Note oldNote = notesDao.getNote(note.getNoteId());

		if (oldNote.getUser().getUserId() == userId) {

			User user = userService.getUser(userId);
			note.setUser(user);

			// check if the updated note has newer links and add session detach in getNote
			// dao if errror occurs
			Set<NoteLink> oldNoteLinks = oldNote.getNoteLinks();

			Set<NoteLink> newNoteLinks = noteLinkService.extractLinks(note.getDescription());

			// if no links are present with the note simple add the links and update
			if (oldNoteLinks.isEmpty()) {

				note.setNoteLinks(newNoteLinks);

			} else {

				newNoteLinks.forEach(newNoteLink -> {

					// if new link is found add it to the set
					if (oldNoteLinks.contains(newNoteLink) == false) {
						oldNoteLinks.add(newNoteLink);
					}
				});
				note.setNoteLinks(oldNoteLinks);
			}

			notesDao.updateNote(note);

			noteLoggingService.createLog(note, Operation.UPDATE);

		}

	}

	@Override
	@Transactional
	public boolean deleteNote(int noteId, int userId) {

		Note note = getNote(noteId);

		// Authorization only the owner can delete the note
		if (userId == note.getUser().getUserId()) {
			notesDao.deleteNote(noteId);
			noteLoggingService.createLog(note, Operation.DELETE);
			return true;
		}

		return false;

	}

	@Override
	@Transactional
	public Note getNote(int noteId) {

		return notesDao.getNote(noteId);
	}

	@Override
	@Transactional
	public List<Note> getAllNotes(int userId) {
		return notesDao.getAllNotes(userId);
	}

	@Override
	@Transactional
	public int deleteTrash() {
		return notesDao.deleteTrash();
	}

	@Override
	@Transactional
	public List<NoteActionCount> getNotesCountByActionAndDate() {

		List<NoteActionCount> noteActionCountList = new ArrayList<>();

		// get count of each category
		NoteActionCount archiveCount = notesDao.getNotesCountByDate("archive");
		noteActionCountList.add(archiveCount);
		
		NoteActionCount pinCount = notesDao.getNotesCountByDate("pinned");
		noteActionCountList.add(pinCount);
		
		NoteActionCount trashCount = notesDao.getNotesCountByDate("trash");
		noteActionCountList.add(trashCount);

		return noteActionCountList;
	}

}
