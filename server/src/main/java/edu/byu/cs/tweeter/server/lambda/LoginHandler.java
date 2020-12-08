package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.service.LoginServiceImpl;

/**
 * An AWS lambda function that logs a user in and returns the user object and an auth code for
 * a successful login.
 */
public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {

    /**
     * Attempts to login an existing user using the specified info.  Returns a LoginResponse.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public LoginResponse handleRequest(LoginRequest request, Context context) {
        LoginServiceImpl loginService = new LoginServiceImpl();
        return loginService.login(request);
    }
}
