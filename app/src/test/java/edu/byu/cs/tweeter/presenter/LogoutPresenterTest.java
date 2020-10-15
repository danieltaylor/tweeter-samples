package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public class LogoutPresenterTest {

    private LogoutRequest request;
    private LogoutResponse response;
    private LogoutService mockLogoutService;
    private LogoutPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        AuthToken currentAuthToken = new AuthToken();

        request = new LogoutRequest(currentAuthToken);
        response = new LogoutResponse();

        // Create a mock LogoutService
        mockLogoutService = Mockito.mock(LogoutService.class);
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        // Wrap a LogoutPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {}));
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
    }

    @Test
    public void testGetLogout_returnsServiceResult() throws IOException {
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.logout(request));
    }

    @Test
    public void testGetLogout_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockLogoutService.logout(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.logout(request);
        });
    }
}
