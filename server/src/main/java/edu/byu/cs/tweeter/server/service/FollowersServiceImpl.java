package edu.byu.cs.tweeter.server.service;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.FollowersService;
import edu.byu.cs.tweeter.client.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.client.model.service.response.LiteFollowersResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

/**
 * Contains the business logic for getting the users a user is followed by.
 */
public class FollowersServiceImpl implements FollowersService {

    /**
     * Returns the users that the user specified in the request is followed by. Uses information in
     * the request object to limit the number of followers returned and to return the next set of
     * followers after any that were returned in a previous request. Uses the {@link FollowsDAO} to
     * get the followers.
     *
     * @param request contains the data required to fulfill the request.
     * @return the followers.
     */
    @Override
    public FollowersResponse getFollowers(FollowersRequest request) {
        List<User> followerUsers = new ArrayList<>();
        LiteFollowersResponse liteFollowersResponse = getFollowsDAO().getFollowers(request);
        List<String> followerAliases = liteFollowersResponse.getFollowers();
        UserDAO userDAO = getUserDAO();
        for (String alias : followerAliases) {
            User user = userDAO.user(alias);
            followerUsers.add(user);
        }
        return new FollowersResponse(followerUsers, liteFollowersResponse.getHasMorePages());
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
