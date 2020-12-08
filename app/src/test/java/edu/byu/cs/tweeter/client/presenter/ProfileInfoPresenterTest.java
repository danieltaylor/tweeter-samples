package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;import edu.byu.cs.tweeter.client.model.service.ProfileInfoServiceProxy;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.client.model.service.response.ProfileInfoResponse;

public class ProfileInfoPresenterTest {

    private ProfileInfoRequest request;
    private ProfileInfoResponse response;
    private ProfileInfoServiceProxy mockProfileInfoService;
    private ProfileInfoPresenter presenter;

    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", null);
        User requestedUser = new User("FirstName1", "LastName1", ServerFacade.MALE_IMAGE_URL);

        int numFollowersResult = 32;
        int numFolloweesResult = 6;
        boolean isFollowedResult = true;

        request = new ProfileInfoRequest(currentUser, requestedUser);
        response = new ProfileInfoResponse(numFollowersResult, numFolloweesResult, isFollowedResult);

        // Create a mock ProfileInfoService
        mockProfileInfoService = Mockito.mock(ProfileInfoServiceProxy.class);
        Mockito.when(mockProfileInfoService.getProfileInfo(request)).thenReturn(response);

        // Wrap a ProfileInfoPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new ProfileInfoPresenter(new ProfileInfoPresenter.View() {}));
        Mockito.when(presenter.getProfileInfoService()).thenReturn(mockProfileInfoService);
    }

    @Test
    public void testGetProfileInfo_returnsServiceResult() throws IOException, TweeterRemoteException {
        Mockito.when(mockProfileInfoService.getProfileInfo(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getProfileInfo(request));
    }

    @Test
    public void testGetProfileInfo_serviceThrowsIOException_presenterThrowsIOException() throws IOException, TweeterRemoteException {
        Mockito.when(mockProfileInfoService.getProfileInfo(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getProfileInfo(request);
        });
    }
}
