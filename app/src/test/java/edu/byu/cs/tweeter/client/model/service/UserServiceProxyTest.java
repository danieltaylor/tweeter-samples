package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;

public class UserServiceProxyTest {

    private UserRequest validRequest;
    private UserRequest invalidRequest;

    private UserResponse successResponse;
    private UserResponse failureResponse;

    private UserServiceProxy userServiceProxySpy;

    /**
     * Create a UserService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        String requestAlias = "@TestAlias";

        User resultUser = new User("FirstName1", "LastName1", "@TestAlias", ServerFacade.MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new UserRequest(requestAlias);
        invalidRequest = new UserRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new UserResponse(resultUser);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getUser(validRequest, UserServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new UserResponse("An exception occurred");
        Mockito.when(mockServerFacade.getUser(invalidRequest, UserServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a UserService instance and wrap it with a spy that will use the mock service
        userServiceProxySpy = Mockito.spy(new UserServiceProxy());
        Mockito.when(userServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link UserServiceProxy#getUser(UserRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        UserResponse response = userServiceProxySpy.getUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link UserServiceProxy#getUser(UserRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        UserResponse response = userServiceProxySpy.getUser(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    /**
     * Verify that for failed requests the {@link UserServiceProxy#getUser(UserRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_invalidRequest_returnsNoUser() throws IOException, TweeterRemoteException {
        UserResponse response = userServiceProxySpy.getUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for requests the {@link UserServiceProxy#getUser(UserRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_integration() throws IOException, TweeterRemoteException {
        UserServiceProxy integratedProxy = new UserServiceProxy();
        UserResponse response = integratedProxy.getUser(validRequest);
        Assertions.assertNotNull(response);
    }
}
