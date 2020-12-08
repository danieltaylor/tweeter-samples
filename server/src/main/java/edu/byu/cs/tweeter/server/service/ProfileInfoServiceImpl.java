package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.ProfileInfoService;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

/**
 * Contains the business logic for getting the a user's profile info.
 */
public class ProfileInfoServiceImpl implements ProfileInfoService {

    /**
     * Returns the profile info of the user specified in the request.
     *
     * @param request contains the data required to fulfill the request.
     * @return the profile info.
     */
    @Override
    public ProfileInfoResponse getProfileInfo(ProfileInfoRequest request) {
        int numFollowers = getFollowersDAO().getFollowerCount(request.getRequestedUser());
        int numFollowees = getFollowingDAO().getFolloweeCount(request.getRequestedUser());
        boolean isFollowed = getFollowingDAO().isFollowing(request.getRequestingUser(), request.getRequestedUser());

        return new ProfileInfoResponse(numFollowers, numFollowees, isFollowed);
    }

    /**
     * Returns an instance of {@link FollowingDAO}. Allows mocking of the FollowingDAO class
     * for testing purposes. All usages of FollowingDAO should get their FollowingDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowingDAO getFollowingDAO() {
        return new FollowingDAO();
    }

    /**
     * Returns an instance of {@link FollowersDAO}. Allows mocking of the FollowersDAO class
     * for testing purposes. All usages of FollowersDAO should get their FollowersDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowersDAO getFollowersDAO() {
        return new FollowersDAO();
    }
}
