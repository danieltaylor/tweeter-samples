package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class UserServiceImplTest {

    private UserRequest validRequest;
    private UserRequest invalidRequest;
    private UserResponse successResponse;
    private UserResponse failureResponse;
    private UserDAO mockUserDAO;
    private UserServiceImpl userServiceImplSpy;

    @BeforeEach
    public void setup() {
        String validAlias = "@alias";
        String invalidAlias = "not valid";

        User requestedUser = new User("FirstName1", "LastName1", "@alias",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        // Setup a request object to use in the tests
        validRequest = new UserRequest(validAlias);
        invalidRequest = new UserRequest(invalidAlias);

        // Setup a mock ProfileInfoDAO that will return known responses
        successResponse = new UserResponse(requestedUser);
        failureResponse = new UserResponse("Failed");

        mockUserDAO = Mockito.mock(UserDAO.class);

        Mockito.when(mockUserDAO.user(validRequest)).thenReturn(successResponse);
        Mockito.when(mockUserDAO.user(invalidRequest)).thenReturn(failureResponse);

        userServiceImplSpy = Mockito.spy(UserServiceImpl.class);
        Mockito.when(userServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
    }

    /**
     * Verify that the {@link UserServiceImpl#getUser(UserRequest)}
     * method returns the same result as the {@link UserDAO} class.
     */
    @Test
    public void testUser_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        UserResponse response = userServiceImplSpy.getUser(validRequest);

        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successResponse.getUser(), response.getUser());
    }

    /**
     * Verify that the {@link UserServiceImpl#getUser(UserRequest)}
     * method returns the same result as the {@link UserDAO} class.
     */
    @Test
    public void testUser_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        UserResponse response = userServiceImplSpy.getUser(invalidRequest);

        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(failureResponse.getUser(), response.getUser());
    }
}
