package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

public class PostServiceTest {

    private PostRequest validRequest;
    private PostRequest invalidRequest;

    private PostResponse successResponse;
    private PostResponse failureResponse;

    private PostService postServiceSpy;

    /**
     * Create a PostService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();
        Status status = new Status(currentUser, "Status body.", LocalDateTime.of(2020, 3, 14, 3, 14));

        // Setup request objects to use in the tests
        validRequest = new PostRequest(status, currentAuthToken);
        invalidRequest = new PostRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.post(validRequest)).thenReturn(successResponse);

        failureResponse = new PostResponse("An exception occurred");
        Mockito.when(mockServerFacade.post(invalidRequest)).thenReturn(failureResponse);

        // Create a PostService instance and wrap it with a spy that will use the mock service
        postServiceSpy = Mockito.spy(new PostService());
        Mockito.when(postServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link PostService#post(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetPost_validRequest_correctResponse() throws IOException {
        PostResponse response = postServiceSpy.post(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link PostService#post(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetPost_invalidRequest_returnsNoStatus() throws IOException {
        PostResponse response = postServiceSpy.post(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
