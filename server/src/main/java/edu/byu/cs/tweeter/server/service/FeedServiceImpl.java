package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.FeedService;
import edu.byu.cs.tweeter.client.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.client.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

/**
 * Contains the business logic for getting the a user's feed.
 */
public class FeedServiceImpl implements FeedService {

    /**
     * Returns the statuses of the followees of the user specified in the request. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request. Uses the {@link FeedDAO} to
     * get the followers.
     *
     * @param request contains the data required to fulfill the request.
     * @return the statuses.
     */
    @Override
    public FeedResponse getFeed(FeedRequest request) {
        return getFeedDAO().getFeed(request);
    }

    /**
     * Returns an instance of {@link FeedDAO}. Allows mocking of the FeedDAO class
     * for testing purposes. All usages of FeedDAO should get their FeedDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    FeedDAO getFeedDAO() {
        return new FeedDAO();
    }
}
