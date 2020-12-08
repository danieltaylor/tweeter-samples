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
import edu.byu.cs.tweeter.client.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.FollowingResponse;

public class FollowServiceProxyTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowServiceProxy followServiceProxySpy;

    /**
     * Create a FollowService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();

        User otherUser = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new FollowRequest(currentUser, otherUser, currentAuthToken);
        invalidRequest = new FollowRequest(null, null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.follow(validRequest, FollowServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new FollowResponse("An exception occurred");
        Mockito.when(mockServerFacade.follow(invalidRequest, FollowServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowService instance and wrap it with a spy that will use the mock service
        followServiceProxySpy = Mockito.spy(new FollowServiceProxy());
        Mockito.when(followServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowServiceProxy#follow(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxySpy.follow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link FollowServiceProxy#follow(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testFollow_invalidRequest_returnsNoFollow() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceProxySpy.follow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for requests the {@link FollowServiceProxy#follow(FollowRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testFollow_integration() throws IOException, TweeterRemoteException {
        FollowServiceProxy integratedProxy = new FollowServiceProxy();
        FollowResponse response = integratedProxy.follow(validRequest);
        Assertions.assertNotNull(response);
    }
}
