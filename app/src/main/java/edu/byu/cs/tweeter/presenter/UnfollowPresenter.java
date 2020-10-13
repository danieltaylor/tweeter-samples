package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

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
    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) throws IOException {
        UnfollowService unfollowService = new UnfollowService();
        return unfollowService.unfollow(unfollowRequest);
    }
}
