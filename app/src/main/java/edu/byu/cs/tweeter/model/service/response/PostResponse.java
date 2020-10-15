package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Status;

/**
 * A response for a {@link edu.byu.cs.tweeter.model.service.request.PostRequest}.
 */
public class PostResponse extends Response {

    private Status status;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public PostResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public PostResponse() {
        super(true, null);
    }

    /**
     * Returns the posted status.
     *
     * @return the status.
     */
    public Status getStatus() {
        return status;
    }
}
