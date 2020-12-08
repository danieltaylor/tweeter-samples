package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.PostServiceProxy;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;

public class PostPresenterTest {

    private PostRequest request;
    private PostResponse response;
    private PostServiceProxy mockPostService;
    private PostPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        AuthToken currentAuthToken = new AuthToken();
        Status status = new Status(currentUser, "Status body.",
                LocalDateTime.of(2020, 3, 14, 3, 14));

        request = new PostRequest(status, currentAuthToken);
        response = new PostResponse();

        // Create a mock PostService
        mockPostService = Mockito.mock(PostServiceProxy.class);
        Mockito.when(mockPostService.post(request)).thenReturn(response);

        // Wrap a PostPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new PostPresenter(new PostPresenter.View() {}));
        Mockito.when(presenter.getPostService()).thenReturn(mockPostService);
    }

    @Test
    public void testGetPost_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockPostService.post(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.post(request));
    }

    @Test
    public void testGetPost_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockPostService.post(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.post(request);
        });
    }
}
