package com.bridgelabz.todoApp.logging.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="note_details")
public class NoteDetails {
	
	@Id	
	@Column(name = "note_id")
	private int noteId;

	@Column(name="contains_text")
	private boolean containsText;
	
	@Column(name="contains_image")
	private boolean containsImage;
	
	@Column(name="contains_links")
	private boolean containsLinks;
	
	public NoteDetails() {
		
	}
			
	public NoteDetails(int noteId, boolean containsText, boolean containsImage, boolean containsLinks) {
		this.noteId = noteId;
		this.containsText = containsText;
		this.containsImage = containsImage;
		this.containsLinks = containsLinks;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public boolean isContainsText() {
		return containsText;
	}

	public void setContainsText(boolean containsText) {
		this.containsText = containsText;
	}

	public boolean isContainsImage() {
		return containsImage;
	}

	public void setContainsImage(boolean containsImage) {
		this.containsImage = containsImage;
	}

	public boolean isContainsLinks() {
		return containsLinks;
	}

	public void setContainsLinks(boolean containsLinks) {
		this.containsLinks = containsLinks;
	}

	@Override
	public String toString() {
		return "NoteDetails [noteId=" + noteId + ", containsText=" + containsText + ", containsImage=" + containsImage
				+ ", containsLinks=" + containsLinks + "]";
	}
	
	
}
