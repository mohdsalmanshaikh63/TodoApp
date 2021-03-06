package com.bridgelabz.todoApp.notes.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bridgelabz.todoApp.user.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "note")
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private int noteId;

	@Column(name = "title", nullable = false)
	private String title;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@Column(name = "modify_time")
	private Date modifyTime;

	@Column(name = "reminder")
	private Date reminder;

	@Column(name = "color")
	private String color;

	@Column(name = "pinned")
	private boolean pinned;

	@Column(name = "archive")
	private boolean archive;

	@Column(name = "trash")
	private boolean trash;

	@Column(name = "image", columnDefinition = "LONGBLOB")
	private String image;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "note", cascade = CascadeType.ALL)
	private Set<NoteLink> noteLinks;

	// on deletion of notes a user should not be deleted
	// also fetch type lazy since we don't want to get user object with notes
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private User user;

	public Note() {
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public Date getReminder() {
		return reminder;
	}

	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Set<NoteLink> getNoteLinks() {
		return noteLinks;
	}

	public void setNoteLinks(Set<NoteLink> noteLinks) {
		this.noteLinks = noteLinks;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", title=" + title + ", description=" + description + ", createTime="
				+ createTime + ", modifyTime=" + modifyTime + ", reminder=" + reminder + ", color=" + color
				+ ", pinned=" + pinned + ", archive=" + archive + ", trash=" + trash + ", noteLinks=" + noteLinks + "]";
	}

}
