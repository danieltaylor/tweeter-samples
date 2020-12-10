package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.server.service.RegisterServiceImpl;

/**
 * An AWS lambda function that registers a user and returns the user object and an auth code for
 * a successful register.
 */
public class RegisterHandler implements RequestHandler<RegisterRequest, RegisterResponse> {

    /**
     * Attempts to register a new user using the specified info.  Returns a RegisterResponse.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public RegisterResponse handleRequest(RegisterRequest request, Context context) {
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        System.out.println(request.getImageBytes() == null ? "imageBytes is null" : "imageBytes has a value!");
        return registerService.register(request);
    }
}
