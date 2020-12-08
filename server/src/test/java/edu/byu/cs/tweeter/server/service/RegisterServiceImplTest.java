package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class RegisterServiceImplTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;
    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;
    private UserDAO mockUserDAO;
    private AuthTokenDAO mockAuthTokenDAO;
    private RegisterServiceImpl registerServiceImplSpy;

    @BeforeEach
    public void setup() {

        User createdUser = new User("FirstName1", "LastName1", "@alias",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken createdAuthToken = new AuthToken();

        // Setup a request object to use in the tests
        validRequest = new RegisterRequest("FirstName1", "LastName1", "@alias", "somepassword", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        invalidRequest = new RegisterRequest("", "", "", "", "");

        // Setup a mock ProfileInfoDAO that will return known responses
        successResponse = new RegisterResponse(createdUser, createdAuthToken);
        failureResponse = new RegisterResponse("Failed");

        mockUserDAO = Mockito.mock(UserDAO.class);
        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockUserDAO.register(validRequest)).thenReturn(createdUser);
        Mockito.when(mockUserDAO.register(invalidRequest)).thenReturn(null);

        Mockito.when(mockAuthTokenDAO.create(validRequest.getAlias())).thenReturn(createdAuthToken);
        Mockito.when(mockAuthTokenDAO.create(invalidRequest.getAlias())).thenReturn(null);

        registerServiceImplSpy = Mockito.spy(RegisterServiceImpl.class);
        Mockito.when(registerServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
        Mockito.when(registerServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    /**
     * Verify that the {@link RegisterServiceImpl#register(RegisterRequest)}
     * method returns the same result as the {@link UserDAO} and {@link AuthTokenDAO} classes.
     */
    @Test
    public void testRegister_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceImplSpy.register(validRequest);

        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successResponse.getUser(), response.getUser());
        Assertions.assertEquals(successResponse.getAuthToken(), response.getAuthToken());
    }

    /**
     * Verify that the {@link RegisterServiceImpl#register(RegisterRequest)}
     * method returns the same result as the {@link UserDAO} and {@link AuthTokenDAO} classes.
     */
    @Test
    public void testRegister_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        RegisterResponse response = registerServiceImplSpy.register(invalidRequest);

        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(failureResponse.getUser(), response.getUser());
        Assertions.assertEquals(failureResponse.getAuthToken(), response.getAuthToken());
    }
}
