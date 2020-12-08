package edu.byu.cs.tweeter.client.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.client.model.service.PostServiceProxy;
import edu.byu.cs.tweeter.client.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.client.model.service.PostService;
import edu.byu.cs.tweeter.client.model.service.request.PostRequest;
import edu.byu.cs.tweeter.client.model.service.response.PostResponse;

/**
 * The presenter for the post functionality of the application.
 */
public class PostPresenter {

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
    public PostPresenter(View view) {
        this.view = view;
    }

    /**
     * Makes a post request.
     *
     * @param postRequest the request.
     */
    public PostResponse post(PostRequest postRequest) throws IOException, TweeterRemoteException {
        PostService postService = getPostService();
        return postService.post(postRequest);
    }

    /**
     * Returns an instance of {@link PostService}. Allows mocking of the PostService class
     * for testing purposes. All usages of PostService should get their PostService
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    PostService getPostService() {
        return new PostServiceProxy();
    }
}
