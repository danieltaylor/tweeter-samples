package edu.byu.cs.tweeter.client.model.service.response;

/**
 * A response for a {@link edu.byu.cs.tweeter.client.model.service.request.FollowRequest}.
 */
public class FollowResponse extends Response {

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public FollowResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public FollowResponse() {
        super(true, null);
    }
}
