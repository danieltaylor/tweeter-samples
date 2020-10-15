package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.ProfileInfoService;
import edu.byu.cs.tweeter.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileInfoResponse;

/**
 * The presenter for the profile functionality of the application.
 */
public class ProfilePresenter {

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
    public ProfilePresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a profile request.
     *
     * @param profileInfoRequest the request.
     */
    public ProfileInfoResponse getProfile(ProfileInfoRequest profileInfoRequest) throws IOException {
        ProfileInfoService profileInfoService = new ProfileInfoService();
        return profileInfoService.getProfileInfo(profileInfoRequest);
    }
}
