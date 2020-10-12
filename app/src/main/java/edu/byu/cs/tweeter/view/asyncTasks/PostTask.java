package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class PostTask extends AsyncTask<PostRequest, Void, PostResponse> {

    private final PostPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void postSuccessful(PostResponse postResponse);
        void postUnsuccessful(PostResponse postResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to post.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public PostTask(PostPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(PostRequest...)}.
     *
     * @param postRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected PostResponse doInBackground(PostRequest... postRequests) {
        PostResponse postResponse = null;

        try {
            postResponse = presenter.post(postRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return postResponse;
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(PostRequest...)} method) when the task completes.
     *
     * @param postResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(PostResponse postResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(postResponse.isSuccess()) {
            observer.postSuccessful(postResponse);
        } else {
            observer.postUnsuccessful(postResponse);
        }
    }
}
