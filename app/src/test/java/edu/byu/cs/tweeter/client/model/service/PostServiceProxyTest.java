package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;

public class PostServiceProxyTest {

    private PostRequest validRequest;
    private PostRequest invalidRequest;

    private PostResponse successResponse;
    private PostResponse failureResponse;

    private PostServiceProxy postServiceProxySpy;

    /**
     * Create a PostService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        AuthToken currentAuthToken = new AuthToken();
        Status status = new Status(currentUser, "Status body.",
                LocalDateTime.of(2020, 3, 14, 3, 14));

        // Setup request objects to use in the tests
        validRequest = new PostRequest(status, currentAuthToken);
        invalidRequest = new PostRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.post(validRequest, PostServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new PostResponse("An exception occurred");
        Mockito.when(mockServerFacade.post(invalidRequest, PostServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a PostService instance and wrap it with a spy that will use the mock service
        postServiceProxySpy = Mockito.spy(new PostServiceProxy());
        Mockito.when(postServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link PostServiceProxy#post(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testPost_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        PostResponse response = postServiceProxySpy.post(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link PostServiceProxy#post(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testPost_invalidRequest_returnsNoStatus() throws IOException, TweeterRemoteException {
        PostResponse response = postServiceProxySpy.post(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for requests the {@link PostServiceProxy#post(PostRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testPost_integration() throws IOException, TweeterRemoteException {
        PostServiceProxy integratedProxy = new PostServiceProxy();
        PostResponse response = integratedProxy.post(validRequest);
        Assertions.assertNotNull(response);
    }
}
