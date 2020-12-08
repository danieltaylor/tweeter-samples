package edu.byu.cs.tweeter.client.model.service.request;

import edu.byu.cs.tweeter.client.model.domain.User;

/**
 * Contains all the information needed to make a profile info request.
 */
public class ProfileInfoRequest {
	private User requestedUser;
	private User requestingUser;

	/**
	 * Allows construction of the object from Json. Private so it won't be called in normal code.
	 */
	private ProfileInfoRequest() {}

	/**
	 * Creates an instance.
	 *
	 * @param requestedUser the user whose profile is being requested.
	 * @param requestingUser the user who is making the request.
	 */
	public ProfileInfoRequest(User requestedUser, User requestingUser) {
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
	 * Sets the user whose profile is being requested.
	 *
	 * @param requestedUser the requested user.
	 */
	public void setRequestedUser(User requestedUser) {
		this.requestedUser = requestedUser;
	}

	/**
	 * Returns the user who is making this request.
	 *
	 * @return the requesting user.
	 */
	public User getRequestingUser() {
		return requestingUser;
	}

	/**
	 * Sets the user who is making this request.
	 *
	 * @param requestingUser the requesting user.
	 */
	public void setRequestingUser(User requestingUser) {
		this.requestingUser = requestingUser;
	}

}
