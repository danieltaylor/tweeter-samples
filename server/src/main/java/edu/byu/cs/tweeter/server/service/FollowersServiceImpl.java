package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;

/**
 * Contains the business logic for getting the users a user is followed by.
 */
public class FollowersServiceImpl implements FollowersService {

    /**
     * Returns the users that the user specified in the request is followed by. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link FollowersDAO} to
     * get the followers.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        return getFollowersDAO().getFollowers(request);
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
