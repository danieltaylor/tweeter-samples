package edu.byu.cs.tweeter.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.server.service.ProfileInfoServiceImpl;

/**
 * An AWS lambda function that returns the a user's profile info.
 */
public class ProfileInfoHandler implements RequestHandler<ProfileInfoRequest, ProfileInfoResponse> {

    /**
     * Returns the specified user's profile info.
     *
     * @param request contains the data required to fulfill the request.
     * @param context the lambda context.
     * @return the response.
     */
    @Override
    public ProfileInfoResponse handleRequest(ProfileInfoRequest request, Context context) {
        ProfileInfoServiceImpl service = new ProfileInfoServiceImpl();
        return service.getProfileInfo(request);
    }
}
