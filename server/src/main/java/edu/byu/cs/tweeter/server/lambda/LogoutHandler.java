package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.client.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.service.LogoutServiceImpl;

/**
 * An AWS lambda function that logs out a user.
 */
public class LogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse> {

    /**
     * Attempts to logout a user.  Returns a LogoutResponse.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public LogoutResponse handleRequest(LogoutRequest request, Context context) {
        LogoutServiceImpl logoutService = new LogoutServiceImpl();
        return logoutService.logout(request);
    }
}
