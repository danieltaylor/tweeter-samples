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
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.client.model.service.response.RegisterResponse;

public class RegisterServiceProxyTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterServiceProxy registerServiceProxySpy;

    /**
     * Create a RegisterService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User resultUser = new User("FirstName", "LastName", "@TestAlias", ServerFacade.MALE_IMAGE_URL);
        AuthToken resultAuthToken = new AuthToken();

        // Setup request objects to use in the tests
        validRequest = new RegisterRequest("FirstName", "LastName", "@TestAlias", "password", ServerFacade.MALE_IMAGE_URL);
        invalidRequest = new RegisterRequest(null, null, null, null, (String) null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RegisterResponse(resultUser, resultAuthToken);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest, RegisterServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("An exception occurred");
        Mockito.when(mockServerFacade.register(invalidRequest, RegisterServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a RegisterService instance and wrap it with a spy that will use the mock service
        registerServiceProxySpy = Mockito.spy(new RegisterServiceProxy());
        Mockito.when(registerServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link RegisterServiceProxy#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxySpy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link RegisterServiceProxy#register(RegisterRequest)} method loads the
     * profile image of each register included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_validRequest_loadsProfileImages() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxySpy.register(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    /**
     * Verify that for failed requests the {@link RegisterServiceProxy#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_invalidRequest_returnsNoUser() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceProxySpy.register(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for requests the {@link RegisterServiceProxy#register(RegisterRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testRegister_integration() throws IOException, TweeterRemoteException {
        RegisterServiceProxy integratedProxy = new RegisterServiceProxy();
        RegisterResponse response = integratedProxy.register(validRequest);
        Assertions.assertNotNull(response);
    }
}
