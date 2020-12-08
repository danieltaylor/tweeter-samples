package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.client.model.service.response.LogoutResponse;

/**
 * Defines the interface for the 'logout' service.
 */
public interface LogoutService {

    LogoutResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException;
}
