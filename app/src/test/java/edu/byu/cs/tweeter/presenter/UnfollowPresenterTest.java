package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class UnfollowPresenterTest {

    private UnfollowRequest request;
    private UnfollowResponse response;
    private UnfollowService mockUnfollowService;
    private UnfollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();

        User otherUser = new User("FirstName1", "LastName1",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        request = new UnfollowRequest(currentUser, otherUser, currentAuthToken);
        response = new UnfollowResponse();

        // Create a mock UnfollowService
        mockUnfollowService = Mockito.mock(UnfollowService.class);
        Mockito.when(mockUnfollowService.unfollow(request)).thenReturn(response);

        // Wrap a UnfollowPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new UnfollowPresenter(new UnfollowPresenter.View() {}));
        Mockito.when(presenter.getUnfollowService()).thenReturn(mockUnfollowService);
    }

    @Test
    public void testGetUnfollow_returnsServiceResult() throws IOException {
        Mockito.when(mockUnfollowService.unfollow(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.unfollow(request));
    }

    @Test
    public void testGetUnfollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockUnfollowService.unfollow(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.unfollow(request);
        });
    }
}
