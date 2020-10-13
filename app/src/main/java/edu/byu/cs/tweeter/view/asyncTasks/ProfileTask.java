package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.ProfileRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileResponse;
import edu.byu.cs.tweeter.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.util.ByteArrayUtils;

public class ProfileTask extends AsyncTask<ProfileRequest, Void, ProfileResponse> {

    private final ProfilePresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void getProfileSuccessful(ProfileResponse profileResponse);
        void getProfileUnsuccessful(ProfileResponse profileResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to profile.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public ProfileTask(ProfilePresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to retrieve a users profile info. This method is
     * invoked indirectly by calling {@link #execute(ProfileRequest...)}.
     *
     * @param profileRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected ProfileResponse doInBackground(ProfileRequest... profileRequests) {
        ProfileResponse profileResponse = null;

        try {
            profileResponse = presenter.getProfile(profileRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return profileResponse;
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(ProfileRequest...)} method) when the task completes.
     *
     * @param profileResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(ProfileResponse profileResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(profileResponse.isSuccess()) {
            observer.getProfileSuccessful(profileResponse);
        } else {
            observer.getProfileUnsuccessful(profileResponse);
        }
    }
}
