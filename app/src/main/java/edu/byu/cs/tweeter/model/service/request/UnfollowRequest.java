package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make an unfollow request.
 */
public class UnfollowRequest {

    private final User requestingUser;
    private final User toBeUnfollowed;
    private final AuthToken authToken;

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
     * Returns the the user to be unfollowed.
     *
     * @return the user to be unfollowed.
     */
    public User getToBeUnfollowed() {
        return toBeUnfollowed;
    }

    /**
     * Returns the auth token of the requesting user.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }
}
