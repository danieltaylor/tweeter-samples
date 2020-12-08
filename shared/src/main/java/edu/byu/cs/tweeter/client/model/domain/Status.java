package edu.byu.cs.tweeter.client.model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Status {
	private User user;
	private String timestamp;
	private String body;
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a");

	/**
	 * Allows construction of the object from Json. Private so it won't be called by other code.
	 */
	private Status() {}

	public Status(User user, String body, LocalDateTime date) {
		this.user = user;
		this.timestamp = date.format(formatter);
		this.body = body;
	}

	public Status(User user, String body, String timestamp) {
		this.user = user;
		this.timestamp = timestamp;
		this.body = body;
	}

	public Status(User user, String body) {
		this.user = user;
		this.body = body;
		this.timestamp = LocalDateTime.now().format(formatter);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

//	public LocalDateTime getDate() {
//		return LocalDateTime.parse(timestamp, formatter);
//	}

//	public void setDate(LocalDateTime date) {
//		this.timestamp = date.format(formatter);
//	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Status status = (Status) o;
		System.out.println("here");
		System.out.println(getUser().getAlias() + " & " + status.getUser().getAlias());
		System.out.println(getBody() + " & " + status.getBody());
		System.out.println(getTimestamp() + " & " + status.getTimestamp());

		return (getUser().equals(status.getUser())) &&
				(getTimestamp().equals(status.getTimestamp())) &&
				(getBody().equals(status.getBody()));
	}
}
