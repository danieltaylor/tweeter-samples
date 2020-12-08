package edu.byu.cs.tweeter.client.model.service.request;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.Status;

/**
 * Contains all the information needed to make a post request.
 */
public class PostRequest {

    //note that we do not include a user object in the request because it is contained within the status object
    private Status status;
    private AuthToken authToken;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private PostRequest() {}

    /**
     * Creates an instance.
     *
     * @param status the status to be posted.
     * @param authToken the auth token of the user who is posting.
     */
    public PostRequest(Status status, AuthToken authToken) {
        this.status = status;
        this.authToken = authToken;
    }

    /**
     * Returns the status to be posted by this request.
     *
     * @return the status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status to be posted by this request.
     *
     * @param status the status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns the auth token of the user whose status is to be posted by this request.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * Sets the auth token of the user whose status is to be posted by this request.
     *
     * @param authToken the auth token.
     */
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
