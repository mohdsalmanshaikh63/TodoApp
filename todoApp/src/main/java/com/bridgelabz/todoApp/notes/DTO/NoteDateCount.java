package com.bridgelabz.todoApp.notes.DTO;

import java.util.Date;

public class NoteDateCount {

	private long count;
	
	private Date date;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
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
		return "NoteDateCount [count=" + count + ", date=" + date + "]";
	}
	
	
}
