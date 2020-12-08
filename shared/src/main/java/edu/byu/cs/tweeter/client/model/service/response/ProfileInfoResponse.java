package edu.byu.cs.tweeter.client.model.service.response;

import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;

/**
 * A response for a {@link ProfileInfoRequest}.
 */
public class ProfileInfoResponse extends Response {

    private int numFollowers;
    private int numFollowees;
    private boolean isFollowed;

    /**
     * Creates a response indicating that the corresponding request was unsuccessful.
     *
     * @param message a message describing why the request was unsuccessful.
     */
    public ProfileInfoResponse(String message) {
        super(false, message);
    }

    /**
     * Creates a response indicating that the corresponding request was successful.
     *
     * @param numFollowers the number of users following the requested user.
     * @param numFollowees the number of users followed by the requested user.
     * @param isFollowed true if the requested user is followed by the requesting user, else false.
     */
    public ProfileInfoResponse(int numFollowers, int numFollowees, boolean isFollowed) {
        super(true, null);
        this.numFollowers = numFollowers;
        this.numFollowees = numFollowees;
        this.isFollowed = isFollowed;
    }

    /**
     * Returns the number of users following the requested user.
     *
     * @return the number of followers.
     */
    public int getNumFollowers() {
        return numFollowers;
    }

    /**
     * Returns the number of users followed by the requested user.
     *
     * @return the number of folowees.
     */
    public int getNumFollowees() {
        return numFollowees;
    }

    /**
     * Returns true if the requested user is followed by the requesting user, else false.
     *
     * @return whether the requested user is following the requesting user.
     */
    public boolean isFollowed() {
        return isFollowed;
    }
}
