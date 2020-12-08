package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class FollowServiceImpl implements FollowService {

    @Override
    public FollowResponse follow(FollowRequest request) {
        FollowResponse response1 = getFollowersDAO().follow(request);
        if (!response1.isSuccess()){
            return response1;
        }

        FollowResponse response2 =  getFollowingDAO().follow(request);
        if (!response2.isSuccess()) {
            return response2;
        }

        if (!getAuthTokenDAO().isValid(request.getAuthToken(), request.getRequestingUser().getAlias())){
            return new FollowResponse("Invalid auth token.");
        }

        return new FollowResponse();
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
