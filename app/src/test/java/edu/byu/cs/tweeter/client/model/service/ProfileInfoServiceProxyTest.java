package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;

public class ProfileInfoServiceProxyTest {

    private ProfileInfoRequest validRequest;
    private ProfileInfoRequest invalidRequest;

    private ProfileInfoResponse successResponse;
    private ProfileInfoResponse failureResponse;

    private ProfileInfoServiceProxy profileInfoServiceProxySpy;

    /**
     * Create a ProfileInfoService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User requestedUser = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);

        int numFollowersResult = 32;
        int numFolloweesResult = 6;
        boolean isFollowedResult = true;

        // Setup request objects to use in the tests
        validRequest = new ProfileInfoRequest(currentUser, requestedUser);
        invalidRequest = new ProfileInfoRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new ProfileInfoResponse(numFollowersResult, numFolloweesResult, isFollowedResult);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getProfileInfo(validRequest, ProfileInfoServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new ProfileInfoResponse("An exception occurred");
        Mockito.when(mockServerFacade.getProfileInfo(invalidRequest, ProfileInfoServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a ProfileInfoService instance and wrap it with a spy that will use the mock service
        profileInfoServiceProxySpy = Mockito.spy(new ProfileInfoServiceProxy());
        Mockito.when(profileInfoServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link ProfileInfoServiceProxy#getProfileInfo(ProfileInfoRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetProfileInfo_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        ProfileInfoResponse response = profileInfoServiceProxySpy.getProfileInfo(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link ProfileInfoServiceProxy#getProfileInfo(ProfileInfoRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetProfileInfo_invalidRequest_returnsNoProfileInfo() throws IOException, TweeterRemoteException {
        ProfileInfoResponse response = profileInfoServiceProxySpy.getProfileInfo(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }


    /**
     * Verify that for requests the {@link ProfileInfoServiceProxy#getProfileInfo(ProfileInfoRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetProfileInfo_integration() throws IOException, TweeterRemoteException {
        ProfileInfoServiceProxy integratedProxy = new ProfileInfoServiceProxy();
        ProfileInfoResponse response = integratedProxy.getProfileInfo(validRequest);
        Assertions.assertNotNull(response);
    }
}
