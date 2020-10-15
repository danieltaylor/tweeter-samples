package edu.byu.cs.tweeter.model.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Status {
	private final User user;
	private final LocalDateTime date;
	private final String body;

	public Status(User user, String body, LocalDateTime date) {
		this.user = user;
		this.date = date;
		this.body = body;
	}

	public Status(User user, String body) {
		this.user = user;
		this.body = body;
		this.date = LocalDateTime.now();
	}

	public User getUser() {
		return user;
	}

	public String getTimestamp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a");
		return formatter.format(date);
	}

	public String getBody() {
		return body;
	}
}
