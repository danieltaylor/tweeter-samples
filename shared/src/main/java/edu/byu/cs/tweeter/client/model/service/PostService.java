package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;

/**
 * Defines the interface for the 'post' service.
 */
public interface PostService {

    PostResponse post(PostRequest request) throws IOException, TweeterRemoteException;
}
