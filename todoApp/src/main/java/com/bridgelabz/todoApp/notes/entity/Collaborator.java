package com.bridgelabz.todoApp.notes.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="collaborator")
public class Collaborator {
	
	@EmbeddedId
	CollaboratorId collaboratorId;
	
	boolean shared;

	public CollaboratorId getCollaboratorId() {
		return collaboratorId;
	}

	public void setCollaboratorId(CollaboratorId collaboratorId) {
		this.collaboratorId = collaboratorId;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	@Override
	public String toString() {
		return "Collaborator [collaboratorId=" + collaboratorId + ", shared=" + shared + "]";
	}
	
	

}
