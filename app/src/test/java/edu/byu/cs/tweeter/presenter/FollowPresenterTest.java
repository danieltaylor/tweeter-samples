package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

public class FollowPresenterTest {

    private FollowRequest request;
    private FollowResponse response;
    private FollowService mockFollowService;
    private FollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();

        User otherUser = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);

        request = new FollowRequest(currentUser, otherUser, currentAuthToken);
        response = new FollowResponse();

        // Create a mock FollowService
        mockFollowService = Mockito.mock(FollowService.class);
        Mockito.when(mockFollowService.follow(request)).thenReturn(response);

        // Wrap a FollowPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FollowPresenter(new FollowPresenter.View() {}));
        Mockito.when(presenter.getFollowService()).thenReturn(mockFollowService);
    }

    @Test
    public void testGetFollow_returnsServiceResult() throws IOException {
        Mockito.when(mockFollowService.follow(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.follow(request));
    }

    @Test
    public void testGetFollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockFollowService.follow(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.follow(request);
        });
    }
}
