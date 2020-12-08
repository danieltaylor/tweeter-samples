package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.UnfollowService;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class UnfollowServiceImpl implements UnfollowService {

    @Override
    public UnfollowResponse unfollow(UnfollowRequest request) {
        UnfollowResponse response1 = getFollowersDAO().unfollow(request);
        if (!response1.isSuccess()){
            return response1;
        }

        UnfollowResponse response2 =  getFollowingDAO().unfollow(request);
        if (!response2.isSuccess()) {
            return response2;
        }

        if (!getAuthTokenDAO().isValid(request.getAuthToken(), request.getRequestingUser().getAlias())){
            return new UnfollowResponse("Invalid auth token.");
        }

        return new UnfollowResponse();
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

    /**
     * Returns an instance of {@link AuthTokenDAO}. Allows mocking of the AuthTokenDAO class
     * for testing purposes. All usages of AuthTokenDAO should get their AuthTokenDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
