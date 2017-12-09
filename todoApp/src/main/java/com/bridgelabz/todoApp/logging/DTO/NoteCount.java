package com.bridgelabz.todoApp.logging.DTO;

public class NoteCount {

	private Long count;
	
	private String noteType;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	@Override
	public String toString() {
		return "NoteCount [count=" + count + ", noteType=" + noteType + "]";
	}
	
}
