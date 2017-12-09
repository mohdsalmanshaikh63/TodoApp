package com.bridgelabz.todoApp.logging.DTO;

import java.util.Date;

import com.bridgelabz.todoApp.logging.entity.Operation;

public class NotesWithOperation {

	private Operation operation;

	private long count;

	private Date date;

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

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
		return "NotesWithOperation [operation=" + operation + ", count=" + count + ", date=" + date + "]";
	}

}
