package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.UnfollowServiceProxy;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.UnfollowService;
import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;

/**
 * The presenter for the unfollow functionality of the application.
 */
public class UnfollowPresenter {

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
    public UnfollowPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a unfollow request.
     *
     * @param unfollowRequest the request.
     */
    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) throws IOException, TweeterRemoteException {
        UnfollowService unfollowService = getUnfollowService();
        return unfollowService.unfollow(unfollowRequest);
    }

    /**
     * Returns an instance of {@link UnfollowService}. Allows mocking of the UnfollowService class
     * for testing purposes. All usages of UnfollowService should get their UnfollowService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UnfollowService getUnfollowService() {
        return new UnfollowServiceProxy();
    }
}
