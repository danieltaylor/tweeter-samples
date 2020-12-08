package edu.byu.cs.tweeter.client.model.service.request;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;

/**
 * Contains all the information needed to make a logout request.
 */
public class LogoutRequest {

    private AuthToken authToken;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private LogoutRequest() {}

    /**
     * Creates an instance.
     *
     * @param authToken the auth token to be invalidated.
     */
    public LogoutRequest(AuthToken authToken) {
        this.authToken = authToken;
    }

    /**
     * Returns the auth token to be invalidated by this request.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }

    /**
     * Sets the auth token to be invalidated by this request.
     *
     * @param authToken the auth token.
     */
    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
