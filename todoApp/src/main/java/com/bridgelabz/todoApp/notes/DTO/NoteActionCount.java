package com.bridgelabz.todoApp.notes.DTO;

import java.util.List;

public class NoteActionCount {

	private String category;
	
	private List<NoteDateCount> noteDateCount;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<NoteDateCount> getNoteDateCount() {
		return noteDateCount;
	}

	public void setNoteDateCount(List<NoteDateCount> noteDateCount) {
		this.noteDateCount = noteDateCount;
	}

	public NoteActionCount(String category, List<NoteDateCount> noteDateCount) {		
		this.category = category;
		this.noteDateCount = noteDateCount;
	}

	
	
	
}
