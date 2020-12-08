package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.UnfollowServiceProxy;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;

public class UnfollowPresenterTest {

    private UnfollowRequest request;
    private UnfollowResponse response;
    private UnfollowServiceProxy mockUnfollowService;
    private UnfollowPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        AuthToken currentAuthToken = new AuthToken();

        User otherUser = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);

        request = new UnfollowRequest(currentUser, otherUser, currentAuthToken);
        response = new UnfollowResponse();

        // Create a mock UnfollowService
        mockUnfollowService = Mockito.mock(UnfollowServiceProxy.class);
        Mockito.when(mockUnfollowService.unfollow(request)).thenReturn(response);

        // Wrap a UnfollowPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new UnfollowPresenter(new UnfollowPresenter.View() {}));
        Mockito.when(presenter.getUnfollowService()).thenReturn(mockUnfollowService);
    }

    @Test
    public void testGetUnfollow_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockUnfollowService.unfollow(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.unfollow(request));
    }

    @Test
    public void testGetUnfollow_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockUnfollowService.unfollow(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.unfollow(request);
        });
    }
}
