package com.bridgelabz.todoApp.notes.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bridgelabz.todoApp.user.entity.User;

@Embeddable
public class CollaboratorId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3332441971128472700L;
	
		
	@ManyToOne
	@JoinColumn(name="note_user")
	private Set<User> noteUsers;

	@ManyToOne
	@JoinColumn(name="note_id")
	private Note note;

	@ManyToOne
	@JoinColumn(name="note_creator")
	private User creator;

	public CollaboratorId() {

	}
		
		
	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	@Override
	public int hashCode() {

		return Objects.hash(getNoteUser().getUserId(), getCreator().getUserId(), getNote().getNoteId());
	}

	@Override
	public boolean equals(Object collaboratorId) {
		if (this == collaboratorId)
			return true;
		if (collaboratorId == null)
			return false;
		if (!(collaboratorId instanceof CollaboratorId))
			return false;

		CollaboratorId collaborator = (CollaboratorId) collaboratorId;
		return Objects.equals(getNoteUser().getUserId(), collaborator.getNoteUser().getUserId())
				&& Objects.equals(getNoteUser().getUserId(), collaborator.getNoteUser().getUserId())
				&& Objects.equals(getNote().getNoteId(), collaborator.getNote().getNoteId());
	}

	@Override
	public String toString() {
		return "Collabarator [noteUser=" + noteUser.getEmail() + ", noteId=" + note.getNoteId() + ", creator="
				+ creator.getEmail() + "]";
	}

}
