package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a post request.
 */
public class PostRequest {

    //note that we do not include a user object in the request because it is contained within the status object
    private final AuthToken authToken;
    private final Status status;

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
     * Returns the auth token of the user whose status is to be posted by this request.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }
}
