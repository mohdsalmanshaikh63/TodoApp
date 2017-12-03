package com.bridgelabz.todoApp.notes.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Table(name = "note_link")
@Entity
public class NoteLink {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private int linkId;

	@Column(name = "title")
	private String title;

	@Column(name = "url")
	private String url;
	
	
	@JsonBackReference
	@JoinColumn(name="note_id")
	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
	private Note note;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
		
	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "NoteLink [linkId=" + linkId + ", title=" + title + ", url=" + url + "]";
	}

}
