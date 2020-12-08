package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.server.service.FollowServiceImpl;

/**
 * An AWS lambda function that follows a user.
 */
public class FollowHandler implements RequestHandler<FollowRequest, FollowResponse> {

    /**
     * Requests to follow a user.  Returns a FollowResponse.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public FollowResponse handleRequest(FollowRequest request, Context context) {
        FollowServiceImpl followService = new FollowServiceImpl();
        return followService.follow(request);
    }
}
