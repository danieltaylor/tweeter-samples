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
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryServiceTest {

    private StoryRequest validRequest;
    private StoryRequest invalidRequest;

    private StoryResponse successResponse;
    private StoryResponse failureResponse;

    private StoryService storyServiceSpy;

    /**
     * Create a StoryService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", ServerFacade.MALE_IMAGE_URL);

        Status resultStatus1 = new Status(currentUser, "Status body 1.",
                LocalDateTime.of(2020, 7, 4, 7, 20));
        Status resultStatus2 = new Status(currentUser, "Status body 2?",
                LocalDateTime.of(2020, 2, 27, 11, 11));
        Status resultStatus3 = new Status(currentUser, "Status body 3!",
                LocalDateTime.of(2020, 1, 8, 17, 38));

        // Setup request objects to use in the tests
        validRequest = new StoryRequest(currentUser, 3, null);
        invalidRequest = new StoryRequest(null, 0, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new StoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getStory(validRequest)).thenReturn(successResponse);

        failureResponse = new StoryResponse("An exception occurred");
        Mockito.when(mockServerFacade.getStory(invalidRequest)).thenReturn(failureResponse);

        // Create a StoryService instance and wrap it with a spy that will use the mock service
        storyServiceSpy = Mockito.spy(new StoryService());
        Mockito.when(storyServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link StoryService#getStory(StoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link StoryService#getStory(StoryRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_validRequest_loadsProfileImages() throws IOException {
        StoryResponse response = storyServiceSpy.getStory(validRequest);

        for (Status status : response.getStatuses()) {
            User user = status.getUser();
            Assertions.assertNotNull(user.getImageBytes());
        }
    }

    /**
     * Verify that for failed requests the {@link StoryService#getStory(StoryRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetStory_invalidRequest_returnsNoStory() throws IOException {
        StoryResponse response = storyServiceSpy.getStory(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
