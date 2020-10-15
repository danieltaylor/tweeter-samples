package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterServiceTest {

    private RegisterRequest validRequest;
    private RegisterRequest invalidRequest;

    private RegisterResponse successResponse;
    private RegisterResponse failureResponse;

    private RegisterService registerServiceSpy;

    /**
     * Create a RegisterService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User resultUser = new User("FirstName", "LastName", "@TestAlias",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken resultAuthToken = new AuthToken();

        // Setup request objects to use in the tests
        validRequest = new RegisterRequest("FirstName", "LastName", "@TestAlias", "password", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        invalidRequest = new RegisterRequest(null, null, null, null, (String) null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new RegisterResponse(resultUser, resultAuthToken);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.register(validRequest)).thenReturn(successResponse);

        failureResponse = new RegisterResponse("An exception occurred");
        Mockito.when(mockServerFacade.register(invalidRequest)).thenReturn(failureResponse);

        // Create a RegisterService instance and wrap it with a spy that will use the mock service
        registerServiceSpy = Mockito.spy(new RegisterService());
        Mockito.when(registerServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link RegisterService#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetRegister_validRequest_correctResponse() throws IOException {
        RegisterResponse response = registerServiceSpy.register(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link RegisterService#register(RegisterRequest)} method loads the
     * profile image of each register included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetRegister_validRequest_loadsProfileImages() throws IOException {
        RegisterResponse response = registerServiceSpy.register(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    /**
     * Verify that for failed requests the {@link RegisterService#register(RegisterRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetRegister_invalidRequest_returnsNoUser() throws IOException {
        RegisterResponse response = registerServiceSpy.register(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
