package com.bridgelabz.todoApp.notes.dao;

import java.util.Set;

import com.bridgelabz.todoApp.notes.entity.Note;
import com.bridgelabz.todoApp.notes.entity.NoteLink;

public interface NoteLinkDao {

	public void saveNoteLinks(Set<NoteLink> noteLinks, Note note);
}
