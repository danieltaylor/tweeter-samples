package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a follow request.
 */
public class FollowRequest {

    private final User requestingUser;
    private final User toBeFollowed;
    private final AuthToken authToken;

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
     * Returns the the user to be followed.
     *
     * @return the user to be followed.
     */
    public User getToBeFollowed() {
        return toBeFollowed;
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
