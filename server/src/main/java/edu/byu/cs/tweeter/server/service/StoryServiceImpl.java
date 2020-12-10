package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.StoryService;
import edu.byu.cs.tweeter.client.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.client.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

import java.util.List;

/**
 * Contains the business logic for getting a user's story.
 */
public class StoryServiceImpl implements StoryService {

    /**
     * Returns the statuses of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. Uses the {@link StoryDAO} to
     * get the followers.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    @Override
    public StoryResponse getStory(StoryRequest request) {
        StoryResponse response = getStoryDAO().getStory(request);
        if (response.isSuccess()) {
            //add full user info to each status
            List<Status> statuses = response.getStatuses();
            for (int i = 0; i < statuses.size(); i++) {
                Status status = statuses.get(i);
                User user = status.getUser();
                user = getUserDAO().user(user.getAlias());
                status.setUser(user);
                statuses.set(i, status);
            }
            return new StoryResponse(statuses, response.getHasMorePages());
        }
        else {
            return response;
        }
    }

    /**
     * Returns an instance of {@link StoryDAO}. Allows mocking of the StoryDAO class
     * for testing purposes. All usages of StoryDAO should get their StoryDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    StoryDAO getStoryDAO() {
        return new StoryDAO();
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
