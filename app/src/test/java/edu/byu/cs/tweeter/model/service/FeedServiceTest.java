package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedServiceTest {

    private FeedRequest validRequest;
    private FeedRequest invalidRequest;

    private FeedResponse successResponse;
    private FeedResponse failureResponse;

    private FeedService feedServiceSpy;

    /**
     * Create a FeedService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);
        User resultUser2 = new User("FirstName2", "LastName2", ServerFacade.FEMALE_IMAGE_URL);
        User resultUser3 = new User("FirstName3", "LastName3", ServerFacade.FEMALE_IMAGE_URL);

        Status resultStatus1 = new Status(resultUser1, "Status body 1.",
                LocalDateTime.of(2020, 7, 4, 7, 20));
        Status resultStatus2 = new Status(resultUser2, "Status body 2?",
                LocalDateTime.of(2020, 2, 27, 11, 11));
        Status resultStatus3 = new Status(resultUser3, "Status body 3!",
                LocalDateTime.of(2020, 1, 8, 17, 38));

        // Setup request objects to use in the tests
        validRequest = new FeedRequest(currentUser, 3, null);
        invalidRequest = new FeedRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FeedResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getFeed(validRequest)).thenReturn(successResponse);

        failureResponse = new FeedResponse("An exception occurred");
        Mockito.when(mockServerFacade.getFeed(invalidRequest)).thenReturn(failureResponse);

        // Create a FeedService instance and wrap it with a spy that will use the mock service
        feedServiceSpy = Mockito.spy(new FeedService());
        Mockito.when(feedServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FeedService#getFeed(FeedRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_validRequest_correctResponse() throws IOException {
        FeedResponse response = feedServiceSpy.getFeed(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link FeedService#getFeed(FeedRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_validRequest_loadsProfileImages() throws IOException {
        FeedResponse response = feedServiceSpy.getFeed(validRequest);

        for (Status status : response.getStatuses()) {
            User user = status.getUser();
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link FeedService#getFeed(FeedRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFeed_invalidRequest_returnsNoFeed() throws IOException {
        FeedResponse response = feedServiceSpy.getFeed(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
