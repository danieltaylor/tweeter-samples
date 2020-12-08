package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.response.RegisterResponse;

/**
 * Defines the interface for the 'register' service.
 */
public interface RegisterService {

    RegisterResponse register(RegisterRequest request) throws IOException, TweeterRemoteException;
}
