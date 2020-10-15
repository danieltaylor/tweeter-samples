package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.presenter.ProfileInfoPresenter;

public class ProfileTask extends AsyncTask<ProfileInfoRequest, Void, ProfileInfoResponse> {

    private final ProfileInfoPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void getProfileSuccessful(ProfileInfoResponse profileInfoResponse);
        void getProfileUnsuccessful(ProfileInfoResponse profileInfoResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to profile.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public ProfileTask(ProfileInfoPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on a background thread to retrieve a users profile info. This method is
     * invoked indirectly by calling {@link #execute(ProfileInfoRequest...)}.
     *
     * @param profileInfoRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected ProfileInfoResponse doInBackground(ProfileInfoRequest... profileInfoRequests) {
        ProfileInfoResponse profileInfoResponse = null;

        try {
            profileInfoResponse = presenter.getProfileInfo(profileInfoRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return profileInfoResponse;
    }

    /**
     * Notifies the observer (on the thread of the invoker of the
     * {@link #execute(ProfileInfoRequest...)} method) when the task completes.
     *
     * @param profileInfoResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(ProfileInfoResponse profileInfoResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(profileInfoResponse.isSuccess()) {
            observer.getProfileSuccessful(profileInfoResponse);
        } else {
            observer.getProfileUnsuccessful(profileInfoResponse);
        }
    }
}
