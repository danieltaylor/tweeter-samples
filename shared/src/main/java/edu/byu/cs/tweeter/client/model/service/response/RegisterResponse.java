package edu.byu.cs.tweeter.client.model.service.response;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;

/**
 * A response for a {@link edu.byu.cs.tweeter.client.model.service.request.LoginRequest}.
 */
public class RegisterResponse extends Response {

    private User user;
    private AuthToken authToken;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public RegisterResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the now registered user.
     * @param authToken the auth token representing this user's session with the server.
     */
    public RegisterResponse(User user, AuthToken authToken) {
        super(true, null);
        this.user = user;
        this.authToken = authToken;
    }

    /**
     * Returns the registered user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the auth token.
     *
     * @return the auth token.
     */
    public AuthToken getAuthToken() {
        return authToken;
    }
}
