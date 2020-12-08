package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.client.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.client.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.client.model.service.response.FollowersResponse;

public class FollowersServiceProxyTest {

    private FollowersRequest validRequest;
    private FollowersRequest invalidRequest;

    private FollowersResponse successResponse;
    private FollowersResponse failureResponse;

    private FollowersServiceProxy followersServiceProxySpy;

    /**
     * Create a FollowersService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);
        User resultUser2 = new User("FirstName2", "LastName2", ServerFacade.FEMALE_IMAGE_URL);
        User resultUser3 = new User("FirstName3", "LastName3", ServerFacade.FEMALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new FollowersRequest(currentUser, 3, null);
        invalidRequest = new FollowersRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFollowers(validRequest, FollowersServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new FollowersResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFollowers(invalidRequest, FollowersServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowersService instance and wrap it with a spy that will use the mock service
        followersServiceProxySpy = Mockito.spy(new FollowersServiceProxy());
        Mockito.when(followersServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowersServiceProxy#getFollowers(FollowersRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersServiceProxySpy.getFollowers(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link FollowersServiceProxy#getFollowers(FollowersRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersServiceProxySpy.getFollowers(validRequest);

        for (User user : response.getFollowers()) {
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FollowersServiceProxy#getFollowers(FollowersRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_invalidRequest_returnsNoFollowers() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersServiceProxySpy.getFollowers(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for requests the {@link FollowersServiceProxy#getFollowers(FollowersRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollowers_integration() throws IOException, TweeterRemoteException {
        FollowersServiceProxy integratedProxy = new FollowersServiceProxy();
        FollowersResponse response = integratedProxy.getFollowers(validRequest);
        Assertions.assertNotNull(response);
    }
}
