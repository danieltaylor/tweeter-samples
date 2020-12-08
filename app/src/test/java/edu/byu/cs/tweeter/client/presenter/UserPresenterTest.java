package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.UserServiceProxy;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;

public class UserPresenterTest {

    private UserRequest request;
    private UserResponse response;
    private UserServiceProxy mockUserService;
    private UserPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        String requestAlias = "@TestAlias";

        User resultUser = new User("FirstName1", "LastName1", "@TestAlias", ServerFacade.MALE_IMAGE_URL);

        request = new UserRequest(requestAlias);
        response = new UserResponse(resultUser);

        // Create a mock UserService
        mockUserService = Mockito.mock(UserServiceProxy.class);
        Mockito.when(mockUserService.getUser(request)).thenReturn(response);

        // Wrap a UserPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new UserPresenter(new UserPresenter.View() {}));
        Mockito.when(presenter.getUserService()).thenReturn(mockUserService);
    }

    @Test
    public void testGetUser_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockUserService.getUser(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getUser(request));
    }

    @Test
    public void testGetUser_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockUserService.getUser(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getUser(request);
        });
    }
}
