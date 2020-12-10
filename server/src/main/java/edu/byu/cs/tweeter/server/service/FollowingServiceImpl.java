package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.FollowingService;
import edu.byu.cs.tweeter.client.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.client.model.service.response.LiteFollowersResponse;
import edu.byu.cs.tweeter.client.model.service.response.LiteFollowingResponse;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl implements FollowingService {

    /**
     * Returns the users that the user specified in the request is following. Uses information in
     * the request object to limit the number of followees returned and to return the next set of
     * followees after any that were returned in a previous request. Uses the {@link FollowingDAO} to
     * get the followees.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followees.
     */
    @Override
    public FollowingResponse getFollowees(FollowingRequest request) {
        List<User> followeeUsers = new ArrayList<>();
        LiteFollowingResponse liteFollowersResponse = getFollowsDAO().getFollowees(request);
        List<String> followeeAliases = liteFollowersResponse.getFollowees();
        UserDAO userDAO = getUserDAO();
        for (String alias : followeeAliases) {
            User user = userDAO.user(alias);
            followeeUsers.add(user);
        }
        return new FollowingResponse(followeeUsers, liteFollowersResponse.getHasMorePages());
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
