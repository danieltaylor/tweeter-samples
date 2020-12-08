package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.client.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;

public class LogoutServiceImplTest {

    private LogoutRequest validRequest;
    private LogoutRequest invalidRequest;
    private LogoutResponse successResponse;
    private LogoutResponse failureResponse;
    private AuthTokenDAO mockAuthTokenDAO;
    private LogoutServiceImpl logoutServiceImplSpy;

    @BeforeEach
    public void setup() {

        AuthToken validAuthToken = new AuthToken();
        AuthToken invalidAuthToken = new AuthToken();

        // Setup a request object to use in the tests
        validRequest = new LogoutRequest(validAuthToken);
        invalidRequest = new LogoutRequest(invalidAuthToken);

        // Setup a mock ProfileInfoDAO that will return known responses
        successResponse = new LogoutResponse();
        failureResponse = new LogoutResponse("Failed");

        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockAuthTokenDAO.invalidate(validAuthToken)).thenReturn(true);
        Mockito.when(mockAuthTokenDAO.invalidate(invalidAuthToken)).thenReturn(false);

        logoutServiceImplSpy = Mockito.spy(LogoutServiceImpl.class);
        Mockito.when(logoutServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    /**
     * Verify that the {@link LogoutServiceImpl#logout(LogoutRequest)}
     * method returns the same result as the {@link AuthTokenDAO} class.
     */
    @Test
    public void testLogout_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceImplSpy.logout(validRequest);

        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
    }

    /**
     * Verify that the {@link LogoutServiceImpl#logout(LogoutRequest)}
     * method returns the same result as the {@link AuthTokenDAO} class.
     */
    @Test
    public void testLogout_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        LogoutResponse response = logoutServiceImplSpy.logout(invalidRequest);

        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
    }
}
