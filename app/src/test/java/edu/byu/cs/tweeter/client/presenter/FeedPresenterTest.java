package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;

import edu.byu.cs.tweeter.client.model.domain.Status;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.FeedServiceProxy;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.client.model.service.response.FeedResponse;

public class FeedPresenterTest {

    private FeedRequest request;
    private FeedResponse response;
    private FeedServiceProxy mockFeedService;
    private FeedPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);

        User resultUser1 = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);
        User resultUser2 = new User("FirstName2", "LastName2", ServerFacade.FEMALE_IMAGE_URL);
        User resultUser3 = new User("FirstName3", "LastName3", ServerFacade.FEMALE_IMAGE_URL);

        Status resultStatus1 = new Status(resultUser1, "Status body 1.",
                LocalDateTime.of(2020, 7, 4, 7, 20));
        Status resultStatus2 = new Status(resultUser2, "Status body 2?",
                LocalDateTime.of(2020, 2, 27, 11, 11));
        Status resultStatus3 = new Status(resultUser3, "Status body 3!",
                LocalDateTime.of(2020, 1, 8, 17, 38));

        request = new FeedRequest(currentUser, 3, null);
        response = new FeedResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);

        // Create a mock FeedService
        mockFeedService = Mockito.mock(FeedServiceProxy.class);
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        // Wrap a FeedPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new FeedPresenter(new FeedPresenter.View() {}));
        Mockito.when(presenter.getFeedService()).thenReturn(mockFeedService);
    }

    @Test
    public void testGetFeed_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedService.getFeed(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getFeed(request));
    }

    @Test
    public void testGetFeed_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockFeedService.getFeed(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getFeed(request);
        });
    }
}
