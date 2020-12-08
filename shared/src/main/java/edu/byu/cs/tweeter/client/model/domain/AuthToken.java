package edu.byu.cs.tweeter.client.model.domain;

import java.io.Serializable;

/**
 * Represents an auth token in the system.
 */
public class AuthToken implements Serializable {
	private String value;
	public AuthToken(String value) {
		this.value = value;
	}
	public AuthToken() {}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
