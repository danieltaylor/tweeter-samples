package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FollowersRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowersResponse;
import edu.byu.cs.tweeter.client.model.service.response.LiteFollowersResponse;
import edu.byu.cs.tweeter.server.dao.FollowsDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class FollowersServiceImplTest {

    private FollowersRequest request;
    private LiteFollowersResponse daoResponse;
    private FollowersResponse expectedResponse;
    private FollowsDAO mockFollowsDAO;
    private UserDAO mockUserDAO;
    private FollowersServiceImpl followersServiceImplSpy;

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
        request = new FollowersRequest(currentUser, 3, null);

        // Setup a mock FollowsDAO that will return known responses
        daoResponse = new LiteFollowersResponse(Arrays.asList(resultUser1.getAlias(), resultUser2.getAlias(), resultUser3.getAlias()), false);
        mockFollowsDAO = Mockito.mock(FollowsDAO.class);
        Mockito.when(mockFollowsDAO.getFollowers(request)).thenReturn(daoResponse);

        mockUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockUserDAO.user(resultUser1.getAlias())).thenReturn(resultUser1);
        Mockito.when(mockUserDAO.user(resultUser2.getAlias())).thenReturn(resultUser2);
        Mockito.when(mockUserDAO.user(resultUser3.getAlias())).thenReturn(resultUser3);

        followersServiceImplSpy = Mockito.spy(FollowersServiceImpl.class);
        Mockito.when(followersServiceImplSpy.getFollowsDAO()).thenReturn(mockFollowsDAO);
        Mockito.when(followersServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);

        expectedResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
    }

    /**
     * Verify that the {@link FollowersServiceImpl#getFollowers(FollowersRequest)}
     * method returns the same result as the {@link FollowsDAO} class.
     */
    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersServiceImplSpy.getFollowers(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
