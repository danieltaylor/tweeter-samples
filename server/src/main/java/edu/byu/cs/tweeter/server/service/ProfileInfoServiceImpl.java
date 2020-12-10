package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.ProfileInfoService;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

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
        int numFollowers = getUserDAO().getFollowerCount(request.getRequestedUser().getAlias());
        int numFollowees = getUserDAO().getFolloweeCount(request.getRequestedUser().getAlias());
        boolean isFollowed = getFollowsDAO().isFollowing(request.getRequestingUser().getAlias(), request.getRequestedUser().getAlias());

        return new ProfileInfoResponse(numFollowers, numFollowees, isFollowed);
    }

    /**
     * Returns an instance of {@link UserDAO}. Allows mocking of the UserDAO class
     * for testing purposes. All usages of UserDAO should get their UserDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UserDAO getUserDAO() {
        return new UserDAO();
    }

    /**
     * Returns an instance of {@link FollowsDAO}. Allows mocking of the FollowsDAO class
     * for testing purposes. All usages of FollowsDAO should get their FollowsDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FollowsDAO getFollowsDAO() {
        return new FollowsDAO();
    }
}
