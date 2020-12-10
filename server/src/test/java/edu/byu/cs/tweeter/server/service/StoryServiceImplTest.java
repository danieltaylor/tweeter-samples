package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.server.dao.UserDAO;
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
import edu.byu.cs.tweeter.client.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.client.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

public class StoryServiceImplTest {

    private StoryRequest request;
    private StoryResponse expectedResponse;
    private StoryDAO mockStoryDAO;
    private UserDAO mockUserDAO;
    private StoryServiceImpl storyServiceImplSpy;

    @BeforeEach
    public void setup() {
        User currentUser = new User("FirstName", "LastName", null);

        Status resultStatus1 = new Status(currentUser,  "Anyone know if @HelenHopwell is on tweeter yet?",
                LocalDateTime.of(2020, 10, 1 ,19, 13));
        Status resultStatus2 = new Status(currentUser, "Covfefe",
                LocalDateTime.of(2020, 9, 28, 18,26));
        Status resultStatus3 = new Status(currentUser, "Check this out: www.crouton.net",
                LocalDateTime.of(2020, 9, 20, 3, 41));
        
        // Setup a request object to use in the tests
        request = new StoryRequest(currentUser, 3, null);

        // Setup a mock StoryDAO that will return known responses
        expectedResponse = new StoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);
        mockStoryDAO = Mockito.mock(StoryDAO.class);
        Mockito.when(mockStoryDAO.getStory(request)).thenReturn(expectedResponse);

        mockUserDAO = Mockito.mock(UserDAO.class);
        Mockito.when(mockUserDAO.user(currentUser.getAlias())).thenReturn(currentUser);

        storyServiceImplSpy = Mockito.spy(StoryServiceImpl.class);
        Mockito.when(storyServiceImplSpy.getStoryDAO()).thenReturn(mockStoryDAO);
        Mockito.when(storyServiceImplSpy.getUserDAO()).thenReturn(mockUserDAO);
    }

    /**
     * Verify that the {@link StoryServiceImpl#getStory(StoryRequest)}
     * method returns the same result as the {@link StoryDAO} class.
     */
    @Test
    public void testGetStory_validRequest_correctResponse() throws IOException, TweeterRemoteException {
        StoryResponse response = storyServiceImplSpy.getStory(request);
        Assertions.assertEquals(expectedResponse, response);
    }
}
