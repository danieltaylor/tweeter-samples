package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

public class UserServiceTest {

    private UserRequest validRequest;
    private UserRequest invalidRequest;

    private UserResponse successResponse;
    private UserResponse failureResponse;

    private UserService userServiceSpy;

    /**
     * Create a UserService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        String requestAlias = "@TestAlias";

        User resultUser = new User("FirstName1", "LastName1", "@TestAlias", ServerFacade.MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new UserRequest(requestAlias);
        invalidRequest = new UserRequest(null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new UserResponse(resultUser);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getUser(validRequest)).thenReturn(successResponse);

        failureResponse = new UserResponse("An exception occurred");
        Mockito.when(mockServerFacade.getUser(invalidRequest)).thenReturn(failureResponse);

        // Create a UserService instance and wrap it with a spy that will use the mock service
        userServiceSpy = Mockito.spy(new UserService());
        Mockito.when(userServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link UserService#getUser(UserRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_validRequest_correctResponse() throws IOException {
        UserResponse response = userServiceSpy.getUser(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link UserService#getUser(UserRequest)} method loads the
     * profile image of each user included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_validRequest_loadsProfileImages() throws IOException {
        UserResponse response = userServiceSpy.getUser(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    /**
     * Verify that for failed requests the {@link UserService#getUser(UserRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetUser_invalidRequest_returnsNoUser() throws IOException {
        UserResponse response = userServiceSpy.getUser(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
