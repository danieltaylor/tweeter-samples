package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;

/**
 * Contains the business logic to support the profile operation.
 */
public class ProfileService {

    public ProfileResponse profile(ProfileRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        ProfileResponse profileResponse = serverFacade.getProfile(request);

        return profileResponse;
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
