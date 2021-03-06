package edu.byu.cs.tweeter.client.model.service.response;

import edu.byu.cs.tweeter.client.model.domain.User;

/**
 * A response for a {@link edu.byu.cs.tweeter.client.model.service.request.UserRequest}.
 */
public class UserResponse extends Response {

    private User user;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public UserResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param user the retrieved user.
     */
    public UserResponse(User user) {
        super(true, null);
        this.user = user;
    }

    /**
     * Returns the retrieved user.
     *
     * @return the user.
     */
    public User getUser() {
        return user;
    }
}
