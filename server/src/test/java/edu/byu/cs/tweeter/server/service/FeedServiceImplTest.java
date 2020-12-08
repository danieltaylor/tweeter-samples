package edu.byu.cs.tweeter.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.client.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

public class FeedServiceImplTest {

    private FeedRequest request;
    private FeedResponse expectedResponse;
    private FeedDAO mockFeedDAO;
    private FeedServiceImpl feedServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User user1 = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        User user2 = new User("FirstName2", "LastName2",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");
        User user3 = new User("FirstName3", "LastName3",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png");

        Status resultStatus1 = new Status(user1,  "Anyone know if @HelenHopwell is on tweeter yet?",
                LocalDateTime.of(2020, 10, 1 ,19, 13));
        Status resultStatus2 = new Status(user2, "Covfefe",
                LocalDateTime.of(2020, 9, 28, 18,26));
        Status resultStatus3 = new Status(user3, "Check this out: www.crouton.net",
                LocalDateTime.of(2020, 9, 20, 3, 41));
        
        // Setup a request object to use in the tests
        request = new FeedRequest(currentUser, 3, null);

        // Setup a mock FeedDAO that will return known responses
        expectedResponse = new FeedResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        mockFeedDAO = Mockito.mock(FeedDAO.class);
        Mockito.when(mockFeedDAO.getFeed(request)).thenReturn(expectedResponse);

        feedServiceImplSpy = Mockito.spy(FeedServiceImpl.class);
        Mockito.when(feedServiceImplSpy.getFeedDAO()).thenReturn(mockFeedDAO);
    }

    /**
     * Verify that the {@link FeedServiceImpl#getFeed(FeedRequest)}
     * method returns the same result as the {@link FeedDAO} class.
     */
    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        FeedResponse response = feedServiceImplSpy.getFeed(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
