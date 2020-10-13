package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;

/**
 * The presenter for the follow functionality of the application.
 */
public class FollowPresenter {

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
    public FollowPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a follow request.
     *
     * @param followRequest the request.
     */
    public FollowResponse follow(FollowRequest followRequest) throws IOException {
        FollowService followService = new FollowService();
        return followService.follow(followRequest);
    }
}
