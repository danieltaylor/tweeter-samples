package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;

/**
 * Contains the business logic to support the getUser operation.
 */
public class UserServiceProxy implements UserService {

    static final String URL_PATH = "/user";

    public UserResponse getUser(UserRequest request) throws IOException, TweeterRemoteException {
        UserResponse response = getServerFacade().getUser(request, URL_PATH);

        if (response.isSuccess()) {
            loadImages(response);
        }

        return response;
    }


    /**
     * Loads the profile image data for the user included in the response.
     *
     * @param response the response from the user request.
     */
    private void loadImages(UserResponse response) throws IOException {
        User user = response.getUser();
        if (user.getImageBytes() == null) {
            byte[] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        }
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
