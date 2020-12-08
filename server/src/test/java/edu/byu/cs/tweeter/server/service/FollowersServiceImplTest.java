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
import edu.byu.cs.tweeter.server.dao.FollowersDAO;

public class FollowersServiceImplTest {

    private FollowersRequest request;
    private FollowersResponse expectedResponse;
    private FollowersDAO mockFollowersDAO;
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

        // Setup a mock FollowersDAO that will return known responses
        expectedResponse = new FollowersResponse(Arrays.asList(resultUser1, resultUser2, resultUser3), false);
        mockFollowersDAO = Mockito.mock(FollowersDAO.class);
        Mockito.when(mockFollowersDAO.getFollowers(request)).thenReturn(expectedResponse);

        followersServiceImplSpy = Mockito.spy(FollowersServiceImpl.class);
        Mockito.when(followersServiceImplSpy.getFollowersDAO()).thenReturn(mockFollowersDAO);
    }

    /**
     * Verify that the {@link FollowersServiceImpl#getFollowers(FollowersRequest)}
     * method returns the same result as the {@link FollowersDAO} class.
     */
    @Test
    public void testGetFollowers_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FollowersResponse response = followersServiceImplSpy.getFollowers(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
