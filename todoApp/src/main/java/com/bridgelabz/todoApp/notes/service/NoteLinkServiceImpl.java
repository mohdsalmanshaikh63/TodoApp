package com.bridgelabz.todoApp.notes.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.notes.dao.NoteLinkDao;
import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.entity.NoteLink;
import com.bridgelabz.todoApp.utilities.UrlExtractor;

@Service
public class NoteLinkServiceImpl implements NoteLinkService {
	
	@Autowired
	NoteLinkDao noteLinkDao;

	@Override
	public Set<NoteLink> extractLinks(String string) throws Exception {

		Set<NoteLink> noteLinks = null;

		// sanity check
		if (string == null) {
			return null;
		}

		String trimmedString = string.trim();

		if (trimmedString == "") {
			return null;
		}

		Set<String> linkSet = UrlExtractor.getUrls(trimmedString);

		// call another method to generate and save link metadata from each link

		// improvise this code if possible
		if (linkSet != null) {
			noteLinks = new HashSet<>();

			for (String link : linkSet) {

				NoteLink noteLink = new NoteLink();
				noteLink.setUrl(link);
				noteLinks.add(noteLink);
			}

			return noteLinks;

		}

		return null;
	}

	@Override
	@Transactional
	public void createNoteLinks(Note note) throws Exception {

		// extract linkmetadata from noteDescription
		Set<NoteLink> noteLinks = extractLinks(note.getDescription());

		// pass the note and linkset to dao for saving
		if (noteLinks != null) {
			noteLinkDao.saveNoteLinks(noteLinks, note);
		}

	}

}
