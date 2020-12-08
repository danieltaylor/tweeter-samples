package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;

/**
 * Defines the interface for the 'unfollow' service.
 */
public interface UnfollowService {

    UnfollowResponse unfollow(UnfollowRequest request) throws IOException, TweeterRemoteException;
}
