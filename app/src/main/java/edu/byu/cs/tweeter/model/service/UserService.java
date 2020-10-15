package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic to support the getUser operation.
 */
public class UserService {

    public UserResponse getUser(UserRequest request) throws IOException {
        UserResponse response = getServerFacade().getUser(request);

        if(response.isSuccess()) {
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
        byte[] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
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
