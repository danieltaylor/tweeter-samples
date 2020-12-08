package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

public class ProfileInfoServiceImplTest {

    private ProfileInfoRequest request;
    private ProfileInfoResponse expectedResponse;
    private FollowersDAO mockFollowersDAO;
    private FollowingDAO mockFollowingDAO;
    private ProfileInfoServiceImpl profileInfoServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User requestedUser = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
    
        // Setup a request object to use in the tests
        request = new ProfileInfoRequest(currentUser, requestedUser);

        // Setup a mock ProfileInfoDAO that will return known responses
        expectedResponse = new ProfileInfoResponse(11, 18, false);
        mockFollowersDAO = Mockito.mock(FollowersDAO.class);
        mockFollowingDAO = Mockito.mock(FollowingDAO.class);
        Mockito.when(mockFollowersDAO.getFollowerCount(request.getRequestedUser())).thenReturn(expectedResponse.getNumFollowers());
        Mockito.when(mockFollowingDAO.getFolloweeCount(request.getRequestedUser())).thenReturn(expectedResponse.getNumFollowees());
        Mockito.when(mockFollowingDAO.isFollowing(request.getRequestingUser(), request.getRequestedUser())).thenReturn(expectedResponse.isFollowed());

        profileInfoServiceImplSpy = Mockito.spy(ProfileInfoServiceImpl.class);
        Mockito.when(profileInfoServiceImplSpy.getFollowersDAO()).thenReturn(mockFollowersDAO);
        Mockito.when(profileInfoServiceImplSpy.getFollowingDAO()).thenReturn(mockFollowingDAO);
    }

    /**
     * Verify that the {@link ProfileInfoServiceImpl#getProfileInfo(ProfileInfoRequest)}
     * method returns the same result as the {@link FollowersDAO} and {@link FollowingDAO} classes.
     */
    @Test
    public void testGetProfileInfo_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        ProfileInfoResponse response = profileInfoServiceImplSpy.getProfileInfo(request);

        Assertions.assertEquals(expectedResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(expectedResponse.getNumFollowers(), response.getNumFollowers());
        Assertions.assertEquals(expectedResponse.getNumFollowees(), response.getNumFollowees());
        Assertions.assertEquals(expectedResponse.isFollowed(), response.isFollowed());
    }
}
