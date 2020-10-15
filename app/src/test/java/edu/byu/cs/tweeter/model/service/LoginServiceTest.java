package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginServiceTest {

    private LoginRequest validRequest;
    private LoginRequest invalidRequest;

    private LoginResponse successResponse;
    private LoginResponse failureResponse;

    private LoginService loginServiceSpy;

    /**
     * Create a LoginService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User resultUser = new User("FirstName", "LastName", "@TestAlias",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
        AuthToken resultAuthToken = new AuthToken();

        // Setup request objects to use in the tests
        validRequest = new LoginRequest("@TestAlias", "password");
        invalidRequest = new LoginRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new LoginResponse(resultUser, resultAuthToken);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.login(validRequest)).thenReturn(successResponse);

        failureResponse = new LoginResponse("An exception occurred");
        Mockito.when(mockServerFacade.login(invalidRequest)).thenReturn(failureResponse);

        // Create a LoginService instance and wrap it with a spy that will use the mock service
        loginServiceSpy = Mockito.spy(new LoginService());
        Mockito.when(loginServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link LoginService#login(LoginRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLogin_validRequest_correctResponse() throws IOException {
        LoginResponse response = loginServiceSpy.login(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that the {@link LoginService#login(LoginRequest)} method loads the
     * profile image of each login included in the result.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLogin_validRequest_loadsProfileImages() throws IOException {
        LoginResponse response = loginServiceSpy.login(validRequest);

        Assertions.assertNotNull(response.getUser().getImageBytes());
    }

    /**
     * Verify that for failed requests the {@link LoginService#login(LoginRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetLogin_invalidRequest_returnsNoUser() throws IOException {
        LoginResponse response = loginServiceSpy.login(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}