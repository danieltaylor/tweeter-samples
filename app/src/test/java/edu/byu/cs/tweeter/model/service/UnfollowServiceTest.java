package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class UnfollowServiceTest {

    private UnfollowRequest validRequest;
    private UnfollowRequest invalidRequest;

    private UnfollowResponse successResponse;
    private UnfollowResponse failureResponse;

    private UnfollowService unfollowServiceSpy;

    /**
     * Create a UnfollowService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();

        User otherUser = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new UnfollowRequest(currentUser, otherUser, currentAuthToken);
        invalidRequest = new UnfollowRequest(null, null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new UnfollowResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.unfollow(validRequest)).thenReturn(successResponse);

        failureResponse = new UnfollowResponse("An exception occurred");
        Mockito.when(mockServerFacade.unfollow(invalidRequest)).thenReturn(failureResponse);

        // Create a UnfollowService instance and wrap it with a spy that will use the mock service
        unfollowServiceSpy = Mockito.spy(new UnfollowService());
        Mockito.when(unfollowServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link UnfollowService#unfollow(UnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUnfollow_validRequest_correctResponse() throws IOException {
        UnfollowResponse response = unfollowServiceSpy.unfollow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link UnfollowService#unfollow(UnfollowRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUnfollow_invalidRequest_returnsNoUnfollow() throws IOException {
        UnfollowResponse response = unfollowServiceSpy.unfollow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
