package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.client.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.service.StoryServiceImpl;

/**
 * An AWS lambda function that returns a user's story.
 */
public class StoryHandler implements RequestHandler<StoryRequest, StoryResponse> {

    /**
     * Returns the statuses of the specified user's story. Uses information in
     * the request object to limit the number of statuses returned and to return the next set of
     * statuses after any that were returned in a previous request.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public StoryResponse handleRequest(StoryRequest request, Context context) {
        StoryServiceImpl service = new StoryServiceImpl();
        return service.getStory(request);
    }
}
