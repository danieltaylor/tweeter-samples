package edu.byu.cs.tweeter.model.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Status {
	private final User user;
	private final Date date;
	private final String body;

	public Status(User user, String body, Date date) {
		this.user = user;
		this.date = date;
		this.body = body;
	}

	public Status(User user, String body) {
		this.user = user;
		this.body = body;
		this.date = new Date();
	}

	public User getUser() {
		return user;
	}

	public String getTimestamp() {
		SimpleDateFormat df = new SimpleDateFormat ("d MMM yyyy HH:mm a");
		return df.format(date);
	}

	public String getBody() {
		return body;
	}
}
