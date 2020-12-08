package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.client.util.ByteArrayUtils;

/**
 * Contains the business logic to support the register operation.
 */
public class RegisterServiceProxy implements RegisterService {

    static final String URL_PATH = "/register";

    public RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException {
        RegisterResponse registerResponse = getServerFacade().register(request, URL_PATH);

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
