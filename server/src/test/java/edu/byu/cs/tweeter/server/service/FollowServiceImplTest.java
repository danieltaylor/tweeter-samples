package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class FollowServiceImplTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;
    private FollowResponse successResponse;
    private FollowResponse failureResponse;
    private FollowersDAO mockFollowersDAO;
    private FollowingDAO mockFollowingDAO;
    private AuthTokenDAO mockAuthTokenDAO;
    private FollowServiceImpl followServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User requestedUser = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken authToken = new AuthToken();

        // Setup a request object to use in the tests
        validRequest = new FollowRequest(currentUser, requestedUser, authToken);
        invalidRequest = new FollowRequest(currentUser, requestedUser, authToken);

        // Setup a mock ProfileInfoDAO that will return known responses
        successResponse = new FollowResponse();
        failureResponse = new FollowResponse("Failed");

        mockFollowersDAO = Mockito.mock(FollowersDAO.class);
        mockFollowingDAO = Mockito.mock(FollowingDAO.class);
        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockFollowersDAO.follow(validRequest)).thenReturn(successResponse);
        Mockito.when(mockFollowingDAO.follow(validRequest)).thenReturn(successResponse);
        Mockito.when(mockAuthTokenDAO.isValid(validRequest.getAuthToken(), validRequest.getRequestingUser().getAlias())).thenReturn(true);

        Mockito.when(mockFollowersDAO.follow(invalidRequest)).thenReturn(failureResponse);
        Mockito.when(mockFollowingDAO.follow(invalidRequest)).thenReturn(failureResponse);
        Mockito.when(mockAuthTokenDAO.isValid(invalidRequest.getAuthToken(), invalidRequest.getRequestingUser().getAlias())).thenReturn(true);

        followServiceImplSpy = Mockito.spy(FollowServiceImpl.class);
        Mockito.when(followServiceImplSpy.getFollowersDAO()).thenReturn(mockFollowersDAO);
        Mockito.when(followServiceImplSpy.getFollowingDAO()).thenReturn(mockFollowingDAO);
        Mockito.when(followServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    /**
     * Verify that the {@link FollowServiceImpl#follow(FollowRequest)}
     * method returns the same result as the {@link FollowersDAO}, {@link FollowingDAO}, and {@link AuthToken}classes.
     */
    @Test
    public void testFollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceImplSpy.follow(validRequest);

        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
    }

    /**
     * Verify that the {@link FollowServiceImpl#follow(FollowRequest)}
     * method returns the same result as the {@link FollowersDAO}, {@link FollowingDAO}, and {@link AuthToken}classes.
     */
    @Test
    public void testFollow_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowResponse response = followServiceImplSpy.follow(invalidRequest);

        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
    }
}
