package edu.byu.cs.tweeter.client.model.service.request;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;

/**
 * Contains all the information needed to make a follow request.
 */
public class FollowRequest {

    private User requestingUser;
    private User toBeFollowed;
    private AuthToken authToken;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowRequest() {}

    /**
     * Creates an instance.
     *
     * @param requestingUser the user requesting to follow someone.
     * @param toBeFollowed the user to be followed.
     * @param authToken the auth token of the requesting user.
     */
    public FollowRequest(User requestingUser, User toBeFollowed, AuthToken authToken) {
        this.requestingUser = requestingUser;
        this.toBeFollowed = toBeFollowed;
        this.authToken = authToken;
    }

    /**
     * Returns the user requesting to follow someone.
     *
     * @return the requesting user.
     */
    public User getRequestingUser() {
        return requestingUser;
    }

    /**
     * Sets the user requesting to follow someone.
     *
     * @param requestingUser the requesting user.
     */
    public void setRequestingUser(User requestingUser) {
        this.requestingUser = requestingUser;
    }

    /**
     * Returns the user to be followed.
     *
     * @return the user to be followed.
     */
    public User getToBeFollowed() {
        return toBeFollowed;
    }

    /**
     * Sets the user to be followed.
     *
     * @param toBeFollowed the user to be followed.
     */
    public void setToBeFollowed(User toBeFollowed) {
        this.toBeFollowed = toBeFollowed;
    }

    /**
     * Returns the auth token of the requesting user.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * Sets the auth token of the requesting user.
     *
     * @param authToken the auth token.
     */
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
