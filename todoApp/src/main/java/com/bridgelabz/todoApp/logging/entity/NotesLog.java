package com.bridgelabz.todoApp.logging.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notes_log")
public class NotesLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_log_id")
	private int noteLogId;

	@Column(name = "user_id")
	private int userId;

	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;

	@Enumerated(EnumType.STRING)
	private Operation operation;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "note_id")
	private NoteDetails noteDetails;

	public int getNoteLogId() {
		return noteLogId;
	}

	public void setNoteLogId(int noteLogId) {
		this.noteLogId = noteLogId;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public NoteDetails getNoteDetails() {
		return noteDetails;
	}

	public void setNoteDetails(NoteDetails noteDetails) {
		this.noteDetails = noteDetails;
	}

	@Override
	public String toString() {
		return "NotesLog [noteLogId=" + noteLogId + ", userId=" + userId + ", date=" + date + ", operation=" + operation
				+ ", noteDetails=" + noteDetails + "]";
	}

}
