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
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class UnfollowServiceImplTest {

    private UnfollowRequest validRequest;
    private UnfollowRequest invalidRequest;
    private UnfollowResponse successResponse;
    private UnfollowResponse failureResponse;
    private FollowersDAO mockFollowersDAO;
    private FollowingDAO mockFollowingDAO;
    private AuthTokenDAO mockAuthTokenDAO;
    private UnfollowServiceImpl unfollowServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User requestedUser = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken authToken = new AuthToken();

        // Setup a request object to use in the tests
        validRequest = new UnfollowRequest(currentUser, requestedUser, authToken);
        invalidRequest = new UnfollowRequest(currentUser, requestedUser, authToken);

        // Setup a mock ProfileInfoDAO that will return known responses
        successResponse = new UnfollowResponse();
        failureResponse = new UnfollowResponse("Failed");

        mockFollowersDAO = Mockito.mock(FollowersDAO.class);
        mockFollowingDAO = Mockito.mock(FollowingDAO.class);
        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockFollowersDAO.unfollow(validRequest)).thenReturn(successResponse);
        Mockito.when(mockFollowingDAO.unfollow(validRequest)).thenReturn(successResponse);
        Mockito.when(mockAuthTokenDAO.isValid(validRequest.getAuthToken(), validRequest.getRequestingUser().getAlias())).thenReturn(true);

        Mockito.when(mockFollowersDAO.unfollow(invalidRequest)).thenReturn(failureResponse);
        Mockito.when(mockFollowingDAO.unfollow(invalidRequest)).thenReturn(failureResponse);
        Mockito.when(mockAuthTokenDAO.isValid(invalidRequest.getAuthToken(), invalidRequest.getRequestingUser().getAlias())).thenReturn(true);

        unfollowServiceImplSpy = Mockito.spy(UnfollowServiceImpl.class);
        Mockito.when(unfollowServiceImplSpy.getFollowersDAO()).thenReturn(mockFollowersDAO);
        Mockito.when(unfollowServiceImplSpy.getFollowingDAO()).thenReturn(mockFollowingDAO);
        Mockito.when(unfollowServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    /**
     * Verify that the {@link UnfollowServiceImpl#unfollow(UnfollowRequest)}
     * method returns the same result as the {@link FollowersDAO}, {@link FollowingDAO}, and {@link AuthToken}classes.
     */
    @Test
    public void testUnfollow_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        UnfollowResponse response = unfollowServiceImplSpy.unfollow(validRequest);

        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
    }

    /**
     * Verify that the {@link UnfollowServiceImpl#unfollow(UnfollowRequest)}
     * method returns the same result as the {@link FollowersDAO}, {@link FollowingDAO}, and {@link AuthToken}classes.
     */
    @Test
    public void testUnfollow_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        UnfollowResponse response = unfollowServiceImplSpy.unfollow(invalidRequest);

        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
    }
}
