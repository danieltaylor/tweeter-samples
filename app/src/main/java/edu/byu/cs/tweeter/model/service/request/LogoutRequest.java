package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.AuthToken;

/**
 * Contains all the information needed to make a logout request.
 */
public class LogoutRequest {

    private final AuthToken authToken;

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
}
