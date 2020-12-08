package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;

/**
 * Defines the interface for the 'user' service.
 */
public interface UserService {

    UserResponse getUser(UserRequest request) throws IOException, TweeterRemoteException;
}
