package edu.byu.cs.tweeter.client.model.service.response;

/**
 * A response for an {@link edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest}.
 */
public class UnfollowResponse extends Response {

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public UnfollowResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     */
    public UnfollowResponse() {
        super(true, null);
    }
}
