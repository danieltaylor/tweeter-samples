package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.service.UserServiceImpl;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful user.
 */
public class UserHandler implements RequestHandler<UserRequest, UserResponse> {

    /**
     * Retrieves a user with specified alias.  Returns a UserResponse.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public UserResponse handleRequest(UserRequest request, Context context) {
        UserServiceImpl userService = new UserServiceImpl();
        return userService.getUser(request);
    }
}
