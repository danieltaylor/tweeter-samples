package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.ProfileInfoService;
import edu.byu.cs.tweeter.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileInfoResponse;

/**
 * The presenter for the profile functionality of the application.
 */
public class ProfileInfoPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with its view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public ProfileInfoPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a profile request.
     *
     * @param profileInfoRequest the request.
     */
    public ProfileInfoResponse getProfileInfo(ProfileInfoRequest profileInfoRequest) throws IOException {
        ProfileInfoService profileInfoService = getProfileInfoService();
        return profileInfoService.getProfileInfo(profileInfoRequest);
    }

    /**
     * Returns an instance of {@link ProfileInfoService}. Allows mocking of the ProfileInfoService class
     * for testing purposes. All usages of ProfileInfoService should get their ProfileInfoService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    ProfileInfoService getProfileInfoService() {
        return new ProfileInfoService();
    }
}
