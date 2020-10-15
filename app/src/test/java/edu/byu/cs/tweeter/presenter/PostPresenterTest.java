package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.PostService;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

public class PostPresenterTest {

    private PostRequest request;
    private PostResponse response;
    private PostService mockPostService;
    private PostPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User currentUser = new User("FirstName", "LastName", null);

        AuthToken currentAuthToken = new AuthToken();
        Status status = new Status(currentUser, "Status body.",
                LocalDateTime.of(2020, 3, 14, 3, 14));

        request = new PostRequest(status, currentAuthToken);
        response = new PostResponse();

        // Create a mock PostService
        mockPostService = Mockito.mock(PostService.class);
        Mockito.when(mockPostService.post(request)).thenReturn(response);

        // Wrap a PostPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new PostPresenter(new PostPresenter.View() {}));
        Mockito.when(presenter.getPostService()).thenReturn(mockPostService);
    }

    @Test
    public void testGetPost_returnsServiceResult() throws IOException {
        Mockito.when(mockPostService.post(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.post(request));
    }

    @Test
    public void testGetPost_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockPostService.post(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.post(request);
        });
    }
}
