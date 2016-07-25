package org.iqu.webcrawler.entities;

/**
 * ErrorMessage - Class that represents the error message.
 * 
 * @author Alex Dragomir
 *
 */
public class ErrorMessage {

	private String error;

	public ErrorMessage() {

	}

	public ErrorMessage(String message) {
		super();
		this.error = message;
	}

	public String getMessage() {
		return error;
	}

	public void setMessage(String message) {
		this.error = message;
	}

}