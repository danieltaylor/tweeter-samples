package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;

public class UnfollowServiceProxyTest {

    private UnfollowRequest validRequest;
    private UnfollowRequest invalidRequest;

    private UnfollowResponse successResponse;
    private UnfollowResponse failureResponse;

    private UnfollowServiceProxy unfollowServiceProxySpy;

    /**
     * Create a UnfollowService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();

        User otherUser = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new UnfollowRequest(currentUser, otherUser, currentAuthToken);
        invalidRequest = new UnfollowRequest(null, null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new UnfollowResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.unfollow(validRequest, UnfollowServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new UnfollowResponse("An exception occurred");
        Mockito.when(mockServerFacade.unfollow(invalidRequest, UnfollowServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a UnfollowService instance and wrap it with a spy that will use the mock service
        unfollowServiceProxySpy = Mockito.spy(new UnfollowServiceProxy());
        Mockito.when(unfollowServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link UnfollowServiceProxy#unfollow(UnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUnfollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        UnfollowResponse response = unfollowServiceProxySpy.unfollow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link UnfollowServiceProxy#unfollow(UnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUnfollow_invalidRequest_returnsNoUnfollow() throws IOException, TweeterRemoteException {
        UnfollowResponse response = unfollowServiceProxySpy.unfollow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for requests the {@link UnfollowServiceProxy#unfollow(UnfollowRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testUnfollow_integration() throws IOException, TweeterRemoteException {
        UnfollowServiceProxy integratedProxy = new UnfollowServiceProxy();
        UnfollowResponse response = integratedProxy.unfollow(validRequest);
        Assertions.assertNotNull(response);
    }
}
