package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.response.LoginResponse;

/**
 * Defines the interface for the 'login' service.
 */
public interface LoginService {

    LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;
}
