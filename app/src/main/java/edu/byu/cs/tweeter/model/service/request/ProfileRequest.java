package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class ProfileRequest {
	private final User requestedUser;
	private final User requestingUser;

	/**
	 * Creates an instance.
	 *
	 * @param requestedUser the user whose profile is being requested.
	 * @param requestingUser the user who is making the request.
	 */
	public ProfileRequest(User requestedUser, User requestingUser) {
		this.requestedUser = requestedUser;
		this.requestingUser = requestingUser;
	}

	/**
	 * Returns the user whose profile is being requested.
	 *
	 * @return the requested user.
	 */
	public User getRequestedUser() {
		return requestedUser;
	}

	/**
	 * Returns the user who is making this request.
	 *
	 * @return the requesting user.
	 */
	public User getRequestingUser() {
		return requestingUser;
	}

}
