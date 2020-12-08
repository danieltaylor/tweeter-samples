package edu.byu.cs.tweeter.client.view.asyncTasks;

import android.os.AsyncTask;

import edu.byu.cs.tweeter.client.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.client.presenter.FollowPresenter;

public class FollowTask extends AsyncTask<FollowRequest, Void, FollowResponse> {

    private final FollowPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followSuccessful(FollowResponse followResponse);
        void followUnsuccessful(FollowResponse followResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to follow.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public FollowTask(FollowPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to log the user in. This method is
     * invoked indirectly by calling {@link #execute(FollowRequest...)}.
     *
     * @param followRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FollowResponse doInBackground(FollowRequest... followRequests) {
        FollowResponse followResponse = null;

        try {
            followResponse = presenter.follow(followRequests[0]);
        } catch (Exception ex) {
            exception = ex;
        }

        return followResponse;
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(FollowRequest...)} method) when the task completes.
     *
     * @param followResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FollowResponse followResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(followResponse.isSuccess()) {
            observer.followSuccessful(followResponse);
        } else {
            observer.followUnsuccessful(followResponse);
        }
    }
}
