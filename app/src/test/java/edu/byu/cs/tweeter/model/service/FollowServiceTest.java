package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowServiceTest {

    private FollowRequest validRequest;
    private FollowRequest invalidRequest;

    private FollowResponse successResponse;
    private FollowResponse failureResponse;

    private FollowService followServiceSpy;

    /**
     * Create a FollowService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();

        User otherUser = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        // Setup request objects to use in the tests
        validRequest = new FollowRequest(currentUser, otherUser, currentAuthToken);
        invalidRequest = new FollowRequest(null, null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new FollowResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.follow(validRequest)).thenReturn(successResponse);

        failureResponse = new FollowResponse("An exception occurred");
        Mockito.when(mockServerFacade.follow(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowService instance and wrap it with a spy that will use the mock service
        followServiceSpy = Mockito.spy(new FollowService());
        Mockito.when(followServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link FollowService#follow(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollow_validRequest_correctResponse() throws IOException {
        FollowResponse response = followServiceSpy.follow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link FollowService#follow(FollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetFollow_invalidRequest_returnsNoFollow() throws IOException {
        FollowResponse response = followServiceSpy.follow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
