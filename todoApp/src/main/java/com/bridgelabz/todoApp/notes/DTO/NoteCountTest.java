package com.bridgelabz.todoApp.notes.DTO;

import java.util.Date;

public class NoteCountTest {

	private Long count;

	private Date date;			

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "NoteCountTest [count=" + count + ", date=" + date + "]";
	}

	
}
