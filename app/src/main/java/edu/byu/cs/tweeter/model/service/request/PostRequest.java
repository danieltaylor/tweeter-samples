package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a login request.
 */
public class PostRequest {

    private final AuthToken authToken;
    private final Status status;

    /**
     * Creates an instance.
     *
     * @param status the status to be posted.
     * @param authToken the authToken of the user who is posting.
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
     * Returns the authToken of the user whose status is to be posted by this request.
     *
     * @return the authToken.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }
}
