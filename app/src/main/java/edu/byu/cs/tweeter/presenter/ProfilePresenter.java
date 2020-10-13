package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.ProfileService;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

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
     * @param profileRequest the request.
     */
    public ProfileResponse getProfile(ProfileRequest profileRequest) throws IOException {
        ProfileService profileService = new ProfileService();
        return profileService.profile(profileRequest);
    }
}
