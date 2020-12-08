package edu.byu.cs.tweeter.client.model.service.request;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;

/**
 * Contains all the information needed to make an unfollow request.
 */
public class UnfollowRequest {

    private User requestingUser;
    private User toBeUnfollowed;
    private AuthToken authToken;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private UnfollowRequest() {}

    /**
     * Creates an instance.
     *
     * @param requestingUser the user requesting to unfollow someone.
     * @param toBeUnfollowed the user to be unfollowed.
     * @param authToken the auth token of the requesting user.
     */
    public UnfollowRequest(User requestingUser, User toBeUnfollowed, AuthToken authToken) {
        this.requestingUser = requestingUser;
        this.toBeUnfollowed = toBeUnfollowed;
        this.authToken = authToken;
    }

    /**
     * Returns the user requesting to unfollow someone.
     *
     * @return the requesting user.
     */
    public User getRequestingUser() {
        return requestingUser;
    }

    /**
     * Sets the user requesting to unfollow someone.
     *
     * @param requestingUser the requesting user.
     */
    public void setRequestingUser(User requestingUser) {
        this.requestingUser = requestingUser;
    }

    /**
     * Returns the the user to be unfollowed.
     *
     * @return the user to be unfollowed.
     */
    public User getToBeUnfollowed() {
        return toBeUnfollowed;
    }

    /**
     * Sets the user to be unfollowed.
     *
     * @param toBeUnfollowed the user to be unfollowed.
     */
    public void setToBeUnfollowed(User toBeUnfollowed) {
        this.toBeUnfollowed = toBeUnfollowed;
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
