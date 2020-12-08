package edu.byu.cs.tweeter.client.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.client.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.client.model.service.response.LogoutResponse;

public class LogoutServiceProxyTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;

    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;

    private LogoutServiceProxy logoutServiceProxySpy;

    /**
     * Create a LogoutService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        AuthToken currentAuthToken = new AuthToken();

        // Setup request objects to use in the tests
        validRequest = new LogoutRequest(currentAuthToken);
        invalidRequest = new LogoutRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LogoutResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.logout(validRequest, LogoutServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new LogoutResponse("An exception occurred");
        Mockito.when(mockServerFacade.logout(invalidRequest, LogoutServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a LogoutService instance and wrap it with a spy that will use the mock service
        logoutServiceProxySpy = Mockito.spy(new LogoutServiceProxy());
        Mockito.when(logoutServiceProxySpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link LogoutServiceProxy#logout(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceProxySpy.logout(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link LogoutServiceProxy#logout(LogoutRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_invalidRequest_returnsNoLogout() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceProxySpy.logout(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }

    /**
     * Verify that for requests the {@link LogoutServiceProxy#logout(LogoutRequest)}
     * method returns a valid response when integrated with a real {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testLogout_integration() throws IOException, TweeterRemoteException {
        LogoutServiceProxy integratedProxy = new LogoutServiceProxy();
        LogoutResponse response = integratedProxy.logout(validRequest);
        Assertions.assertNotNull(response);
    }
}
