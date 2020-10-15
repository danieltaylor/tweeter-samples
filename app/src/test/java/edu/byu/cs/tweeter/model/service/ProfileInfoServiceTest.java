package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileInfoResponse;

public class ProfileInfoServiceTest {

    private ProfileInfoRequest validRequest;
    private ProfileInfoRequest invalidRequest;

    private ProfileInfoResponse successResponse;
    private ProfileInfoResponse failureResponse;

    private ProfileInfoService profileInfoServiceSpy;

    /**
     * Create a ProfileInfoService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);
        User requestedUser = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        int numFollowersResult = 32;
        int numFolloweesResult = 6;
        boolean isFollowedResult = true;

        // Setup request objects to use in the tests
        validRequest = new ProfileInfoRequest(currentUser, requestedUser);
        invalidRequest = new ProfileInfoRequest(null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new ProfileInfoResponse(numFollowersResult, numFolloweesResult, isFollowedResult);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.getProfileInfo(validRequest)).thenReturn(successResponse);

        failureResponse = new ProfileInfoResponse("An exception occurred");
        Mockito.when(mockServerFacade.getProfileInfo(invalidRequest)).thenReturn(failureResponse);

        // Create a ProfileInfoService instance and wrap it with a spy that will use the mock service
        profileInfoServiceSpy = Mockito.spy(new ProfileInfoService());
        Mockito.when(profileInfoServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link ProfileInfoService#getProfileInfo(ProfileInfoRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetProfileInfo_validRequest_correctResponse() throws IOException {
        ProfileInfoResponse response = profileInfoServiceSpy.getProfileInfo(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link ProfileInfoService#getProfileInfo(ProfileInfoRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testGetProfileInfo_invalidRequest_returnsNoProfileInfo() throws IOException {
        ProfileInfoResponse response = profileInfoServiceSpy.getProfileInfo(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
