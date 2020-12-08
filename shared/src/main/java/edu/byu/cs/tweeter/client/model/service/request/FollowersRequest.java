package edu.byu.cs.tweeter.client.model.service.request;

import edu.byu.cs.tweeter.client.model.domain.User;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * followers for a specified followee.
 */
public class FollowersRequest {

    private User followee;
    private int limit;
    private User lastFollower;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowersRequest() {}

    /**
     * Creates an instance.
     *
     * @param followee the {@link User} whose followers are to be returned.
     * @param limit the maximum number of followers to return.
     * @param lastFollower the last follower that was returned in the previous request (null if
     *                     there was no previous request or if no followers were returned in the
     *                     previous request).
     */
    public FollowersRequest(User followee, int limit, User lastFollower) {
        this.followee = followee;
        this.limit = limit;
        this.lastFollower = lastFollower;
    }

    /**
     * Returns the followee whose followers are to be returned by this request.
     *
     * @return the followee.
     */
    public User getFollowee() {
        return followee;
    }

    /**
     * Sets the followee.
     *
     * @param followee the followee.
     */
    public void setFollowee(User followee) {
        this.followee = followee;
    }

    /**
     * Returns the number representing the maximum number of followers to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the limit.
     *
     * @param limit the limit.
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Returns the last follower that was returned in the previous request or null if there was no
     * previous request or if no followers were returned in the previous request.
     *
     * @return the last follower.
     */
    public User getLastFollower() {
        return lastFollower;
    }

    /**
     * Sets the last follower.
     *
     * @param lastFollower the last follower.
     */
    public void setLastFollower(User lastFollower) {
        this.lastFollower = lastFollower;
    }
}
