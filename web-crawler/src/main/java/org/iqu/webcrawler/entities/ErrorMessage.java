package org.iqu.webcrawler.entities;

/**
 * ErrorMessage - Class that represents the error message.
 * 
 * @author Alex Dragomir
 *
 */
public class ErrorMessage {

	String message;

	public ErrorMessage() {

	}

	public ErrorMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}