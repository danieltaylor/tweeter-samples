package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.FollowersDAO;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class PostServiceImplTest {

    private PostRequest validRequest;
    private PostRequest invalidRequest;
    private PostResponse successResponse;
    private PostResponse failureResponse;
    private FeedDAO mockFeedDAO;
    private StoryDAO mockStoryDAO;
    private AuthTokenDAO mockAuthTokenDAO;
    private PostServiceImpl postServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        Status status = new Status(currentUser, "Status body", LocalDateTime.of(2020,10,7,20,23));
        AuthToken validAuthToken = new AuthToken();
        AuthToken invalidAuthToken = new AuthToken();


        // Setup a request object to use in the tests
        validRequest = new PostRequest(status, validAuthToken);
        invalidRequest = new PostRequest(status, invalidAuthToken);

        // Setup a mock ProfileInfoDAO that will return known responses
        successResponse = new PostResponse();
        failureResponse = new PostResponse("Failed");

        mockFeedDAO = Mockito.mock(FeedDAO.class);
        mockStoryDAO = Mockito.mock(StoryDAO.class);
        mockAuthTokenDAO = Mockito.mock(AuthTokenDAO.class);

        Mockito.when(mockFeedDAO.addToFeed(validRequest)).thenReturn(successResponse);
        Mockito.when(mockStoryDAO.addToStory(validRequest)).thenReturn(successResponse);
        Mockito.when(mockAuthTokenDAO.isValid(validRequest.getAuthToken(), currentUser.getAlias())).thenReturn(true);

        Mockito.when(mockFeedDAO.addToFeed(invalidRequest)).thenReturn(failureResponse);
        Mockito.when(mockStoryDAO.addToStory(invalidRequest)).thenReturn(failureResponse);
        Mockito.when(mockAuthTokenDAO.isValid(invalidRequest.getAuthToken(), currentUser.getAlias())).thenReturn(false);

        postServiceImplSpy = Mockito.spy(PostServiceImpl.class);
        Mockito.when(postServiceImplSpy.getFeedDAO()).thenReturn(mockFeedDAO);
        Mockito.when(postServiceImplSpy.getStoryDAO()).thenReturn(mockStoryDAO);
        Mockito.when(postServiceImplSpy.getAuthTokenDAO()).thenReturn(mockAuthTokenDAO);
    }

    /**
     * Verify that the {@link PostServiceImpl#post(PostRequest)}
     * method returns the same result as the {@link FeedDAO}, {@link StoryDAO}, and {@link AuthToken} classes.
     */
    @Test
    public void testPost_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        PostResponse response = postServiceImplSpy.post(validRequest);

        Assertions.assertEquals(successResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(successResponse.getStatus(), response.getStatus());
    }

    /**
     * Verify that the {@link PostServiceImpl#post(PostRequest)}
     * method returns the same result as the {@link FeedDAO}, {@link StoryDAO}, and {@link AuthToken} classes.
     */
    @Test
    public void testFollow_invalidRequest_correctResponse() throws IOException, TweeterRemoteException {
        PostResponse response = postServiceImplSpy.post(invalidRequest);

        Assertions.assertEquals(failureResponse.isSuccess(), response.isSuccess());
        Assertions.assertEquals(failureResponse.getStatus(), response.getStatus());
    }
}
