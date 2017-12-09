package com.bridgelabz.todoApp.logging.dao;

import org.springframework.stereotype.Repository;

import com.bridgelabz.todoApp.logging.entity.NoteDetails;

@Repository
public interface NoteDetailsDao {

	public NoteDetails saveNoteDetails(NoteDetails noteDetails);

	public NoteDetails getNoteDetails(int noteId);

	Long getDetailsCount(boolean containsText, boolean containsLinks, boolean containsImage);
}
