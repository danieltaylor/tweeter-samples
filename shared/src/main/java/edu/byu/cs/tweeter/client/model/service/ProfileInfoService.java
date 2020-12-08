package edu.byu.cs.tweeter.client.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;

/**
 * Defines the interface for the 'profile info' service.
 */
public interface ProfileInfoService {

    ProfileInfoResponse getProfileInfo(ProfileInfoRequest request) throws IOException, TweeterRemoteException;
}
