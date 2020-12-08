package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class LoginServiceImplTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;
    private LoginResponse successResponse;
    private LoginResponse failureResponse;
    private UserDAO mockUserDAO;
    private AuthTokenDAO mockAuthTokenDAO;
    private LoginServiceImpl loginServiceImplSpy;

    @BeforeEach
    public void setup() {

        User loggedInUser = new User("FirstName1", "LastName1", "@alias",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken createdAuthToken = new AuthToken();

        // Setup a request object to use in the tests
        validRequest = new LoginRequest("@alias", "somepassword");
        invalidRequest = new LoginRequest("", "");

        // Setup a mock ProfileInfoDAO that will return known responses
        successResponse = new LoginResponse(loggedInUser, createdAuthToken);
        failureResponse = new LoginResponse("Failed");

        mockUserDAO = Mockito.mock(UserDAO.class);
        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockUserDAO.login(validRequest)).thenReturn(loggedInUser);
        Mockito.when(mockUserDAO.login(invalidRequest)).thenReturn(null);

        Mockito.when(mockAuthTokenDAO.create(validRequest.getAlias())).thenReturn(createdAuthToken);
        Mockito.when(mockAuthTokenDAO.create(invalidRequest.getAlias())).thenReturn(null);

        loginServiceImplSpy = Mockito.spy(LoginServiceImpl.class);
        Mockito.when(loginServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
        Mockito.when(loginServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    /**
     * Verify that the {@link LoginServiceImpl#login(LoginRequest)}
     * method returns the same result as the {@link UserDAO} and {@link AuthTokenDAO} classes.
     */
    @Test
    public void testLogin_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceImplSpy.login(validRequest);

        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successResponse.getUser(), response.getUser());
        Assertions.assertEquals(successResponse.getAuthToken(), response.getAuthToken());
    }

    /**
     * Verify that the {@link RegisterServiceImpl#register(RegisterRequest)}
     * method returns the same result as the {@link UserDAO} and {@link AuthTokenDAO} classes.
     */
    @Test
    public void testLogin_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        LoginResponse response = loginServiceImplSpy.login(invalidRequest);

        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(failureResponse.getUser(), response.getUser());
        Assertions.assertEquals(failureResponse.getAuthToken(), response.getAuthToken());
    }
}
