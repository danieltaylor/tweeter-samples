package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.PostService;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

/**
 * The presenter for the post functionality of the application.
 */
public class PostPresenter {

    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public PostPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a post request.
     *
     * @param postRequest the request.
     */
    public PostResponse post(PostRequest postRequest) throws IOException {
        PostService postService = new PostService();
        return postService.post(postRequest);
    }
}
