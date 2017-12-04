package com.bridgelabz.todoApp.notes.service;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.todoApp.notes.dao.NoteLinkDao;
import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.entity.NoteLink;
import com.bridgelabz.todoApp.utilities.LinkScraper;
import com.bridgelabz.todoApp.utilities.UrlExtractor;
import com.bridgelabz.todoApp.utilities.pojo.UrlMetaData;

@Service
public class NoteLinkServiceImpl implements NoteLinkService {

	@Autowired
	NoteLinkDao noteLinkDao;

	@Autowired
	LinkScraper linkScraper;

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

		// improvise this code if possible
		if (linkSet != null) {
			noteLinks = new HashSet<>();

			for (String link : linkSet) {

				NoteLink noteLink = new NoteLink();
				noteLink.setUrl(link);

				URI uri = new URI(link);

				String domain = uri.getHost();

				// call another method to generate and save link metadata from each link
				UrlMetaData urlMetaData = linkScraper.getUrlMetaData(link);

				String title = urlMetaData.getTitle();
				String imageUrl = urlMetaData.getImageUrl();

				noteLink.setTitle(title);
				noteLink.setImageUrl(imageUrl);
				noteLink.setDomain(domain);

				noteLinks.add(noteLink);
			}

			return noteLinks;

		}

		return null;
	}

	@Override
	@Transactional
	public Set<NoteLink> createNoteLinks(Note note) throws Exception {

		// extract linkmetadata from noteDescription
		Set<NoteLink> noteLinks = extractLinks(note.getDescription());

		// pass the note and linkset to dao for saving
		if (noteLinks != null) {
			noteLinkDao.saveNoteLinks(noteLinks, note);
			return noteLinks;
		}

		return null;

	}

}
