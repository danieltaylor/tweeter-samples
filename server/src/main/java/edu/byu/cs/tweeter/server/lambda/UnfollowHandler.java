package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.service.UnfollowServiceImpl;

/**
 * An AWS lambda function that unfollows a user.
 */
public class UnfollowHandler implements RequestHandler<UnfollowRequest, UnfollowResponse> {

    /**
     * Requests to unfollow a user.  Returns a UnfollowResponse.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public UnfollowResponse handleRequest(UnfollowRequest request, Context context) {
        UnfollowServiceImpl unfollowService = new UnfollowServiceImpl();
        return unfollowService.unfollow(request);
    }
}
