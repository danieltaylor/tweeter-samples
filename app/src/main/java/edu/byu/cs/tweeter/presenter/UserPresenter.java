package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.UserService;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

/**
 * The presenter for the getUser functionality of the application.
 */
public class UserPresenter {

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
    public UserPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a getUser request.
     *
     * @param userRequest the request.
     */
    public UserResponse getUser(UserRequest userRequest) throws IOException {
        UserService userService = getUserService();
        return userService.getUser(userRequest);
    }

    /**
     * Returns an instance of {@link UserService}. Allows mocking of the UserService class
     * for testing purposes. All usages of UserService should get their UserService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UserService getUserService() {
        return new UserService();
    }
}
