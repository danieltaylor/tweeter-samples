package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.UnfollowService;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class UnfollowServiceImpl implements UnfollowService {

    @Override
    public UnfollowResponse unfollow(UnfollowRequest request) {
        if (!getAuthTokenDAO().isValid(request.getAuthToken(), request.getRequestingUser().getAlias())){
            return new UnfollowResponse("Invalid auth token.");
        } else {
            UnfollowResponse response = getFollowsDAO().unfollow(request);
            if (response.isSuccess()) {
                getUserDAO().decrementFolloweeCount(request.getRequestingUser().getAlias());
                getUserDAO().decrementFollowerCount(request.getToBeUnfollowed().getAlias());
            }
            return response;
        }
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
}
