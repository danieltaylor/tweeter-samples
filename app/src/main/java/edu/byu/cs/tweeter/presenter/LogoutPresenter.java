package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

/**
 * The presenter for the logout functionality of the application.
 */
public class LogoutPresenter {

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
    public LogoutPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a logout request.
     *
     * @param logoutRequest the request.
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException {
        LogoutService logoutService = getLogoutService();
        return logoutService.logout(logoutRequest);
    }

    /**
     * Returns an instance of {@link LogoutService}. Allows mocking of the LogoutService class
     * for testing purposes. All usages of LogoutService should get their LogoutService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    LogoutService getLogoutService() {
        return new LogoutService();
    }
}
