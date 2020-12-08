package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;
import edu.byu.cs.tweeter.server.service.PostServiceImpl;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful post.
 */
public class PostHandler implements RequestHandler<PostRequest, PostResponse> {

    /**
     * Attempts to post a status.  Returns a PostResponse.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public PostResponse handleRequest(PostRequest request, Context context) {
        PostServiceImpl postService = new PostServiceImpl();
        return postService.post(request);
    }
}
