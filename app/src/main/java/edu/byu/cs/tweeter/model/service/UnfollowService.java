package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

/**
 * Contains the business logic to support the unfollow operation.
 */
public class UnfollowService {

    public UnfollowResponse unfollow(UnfollowRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        UnfollowResponse unfollowResponse = serverFacade.unfollow(request);

        return unfollowResponse;
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
