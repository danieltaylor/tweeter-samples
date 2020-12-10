package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class FollowServiceImpl implements FollowService {

    @Override
    public FollowResponse follow(FollowRequest request) {
        if (!getAuthTokenDAO().isValid(request.getAuthToken(), request.getRequestingUser().getAlias())){
            return new FollowResponse("Invalid auth token.");
        } else {
            FollowResponse response = getFollowsDAO().follow(request);
            if (response.isSuccess()) {
                getUserDAO().incrementFolloweeCount(request.getRequestingUser().getAlias());
                getUserDAO().incrementFollowerCount(request.getToBeFollowed().getAlias());
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
