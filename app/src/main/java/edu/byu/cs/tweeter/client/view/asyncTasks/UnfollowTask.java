package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.client.presenter.UnfollowPresenter;

public class UnfollowTask extends AsyncTask<UnfollowRequest, Void, UnfollowResponse> {

    private final UnfollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void unfollowSuccessful(UnfollowResponse unfollowResponse);
        void unfollowUnsuccessful(UnfollowResponse unfollowResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to unfollow.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public UnfollowTask(UnfollowPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(UnfollowRequest...)}.
     *
     * @param unfollowRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected UnfollowResponse doInBackground(UnfollowRequest... unfollowRequests) {
        UnfollowResponse unfollowResponse = null;

        try {
            unfollowResponse = presenter.unfollow(unfollowRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return unfollowResponse;
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(UnfollowRequest...)} method) when the task completes.
     *
     * @param unfollowResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(UnfollowResponse unfollowResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(unfollowResponse.isSuccess()) {
            observer.unfollowSuccessful(unfollowResponse);
        } else {
            observer.unfollowUnsuccessful(unfollowResponse);
        }
    }
}
