package org.iqu.webcrawler.entities;

import com.fasterxml.jackson.annotation.JsonValue;

public class Client {

	private String host, location;
	private int port;
	private boolean active;

	public Client(String host, int port, String location, boolean active) {
		super();
		this.host = host;
		this.port = port;
		this.location = location;
		this.active = active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	@JsonValue
	public String toString() {
		return "Client [host=" + host + ", location=" + location + ", port=" + port + ", active=" + active + "]";
	}

}
