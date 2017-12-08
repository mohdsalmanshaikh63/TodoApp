package com.bridgelabz.todoApp.logging.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.logging.dao.NoteDetailsDao;
import com.bridgelabz.todoApp.logging.entity.NoteDetails;
import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.entity.NoteLink;

@Service
public class NoteDetailsServiceImpl implements NoteDetailsService {
	
	@Autowired
	NoteDetailsDao noteDetailsDao;

	@Override
	@Transactional
	public NoteDetails createNoteDetails(Note note) {

		boolean containsText = false;
		boolean containsImage = false;
		boolean containsLink = false;

		// check whether note contains image, text or links
		String title = note.getTitle();
		String description = note.getDescription();
		String image = note.getImage();
		int id = note.getNoteId();
		Set<NoteLink> noteLinks = note.getNoteLinks();
		System.out.println("********Note Links are "+noteLinks);
		
		if ((title != null && title != "") || (description != null && description != "")) {
			containsText = true;
		}
		if (image != null && image != "") {
			containsImage = true;
		}
		if (noteLinks != null && !noteLinks.isEmpty()) {
			containsLink = true;
		}
		
		NoteDetails noteDetails = new NoteDetails(id, containsText, containsImage, containsLink);
		
		noteDetailsDao.saveNoteDetails(noteDetails);
		
		return noteDetails;
	}

	@Override
	@Transactional
	public NoteDetails getNoteDetails(int noteId) {

		return noteDetailsDao.getNoteDetails(noteId);
	}

}
