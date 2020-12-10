package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.response.LiteFollowingResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowingResponse;

public class FollowingServiceImplTest {

    private FollowingRequest request;
    private LiteFollowingResponse daoResponse;
    private FollowingResponse expectedResponse;
    private FollowsDAO mockFollowsDAO;
    private UserDAO mockUserDAO;
    private FollowingServiceImpl followingServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User resultUser2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User resultUser3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        // Setup a request object to use in the tests
        request = new FollowingRequest(currentUser, 3, null);

        // Setup a mock FollowingDAO that will return known responses
        daoResponse = new LiteFollowingResponse(Arrays.asList(resultUser1.getAlias(), resultUser2.getAlias(), resultUser3.getAlias()), false);
        mockFollowsDAO = Mockito.mock(FollowsDAO.class);
        Mockito.when(mockFollowsDAO.getFollowees(request)).thenReturn(daoResponse);

        mockUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockUserDAO.user(resultUser1.getAlias())).thenReturn(resultUser1);
        Mockito.when(mockUserDAO.user(resultUser2.getAlias())).thenReturn(resultUser2);
        Mockito.when(mockUserDAO.user(resultUser3.getAlias())).thenReturn(resultUser3);

        followingServiceImplSpy = Mockito.spy(FollowingServiceImpl.class);
        Mockito.when(followingServiceImplSpy.getFollowsDAO()).thenReturn(mockFollowsDAO);
        Mockito.when(followingServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);

        expectedResponse = new FollowingResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
    }

    /**
     * Verify that the {@link FollowingServiceImpl#getFollowees(FollowingRequest)}
     * method returns the same result as the {@link FollowsDAO} class.
     */
    @Test
    public void testGetFollowees_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowingResponse response = followingServiceImplSpy.getFollowees(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
