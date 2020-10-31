package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

/**
 * Contains the business logic to support the register operation.
 */
public class RegisterService {

    public RegisterResponse register(RegisterRequest request) throws IOException {
        RegisterResponse registerResponse = getServerFacade().register(request);

        if(registerResponse.isSuccess()) {
            loadImage(registerResponse);
        }

        return registerResponse;
    }

    /**
     * Loads the profile image data for the user.
     *
     * @param response the register response for the user whose profile image data is to be loaded.
     */
    private void loadImage(RegisterResponse response) throws IOException {
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
