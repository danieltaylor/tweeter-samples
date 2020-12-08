package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;

/**
 * Contains the business logic to support the getProfileInfo operation.
 */
public class ProfileInfoServiceProxy implements ProfileInfoService {

    static final String URL_PATH = "/profileinfo";

    public ProfileInfoResponse getProfileInfo(ProfileInfoRequest request) throws IOException, TweeterRemoteException {
        return getServerFacade().getProfileInfo(request, URL_PATH);
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
