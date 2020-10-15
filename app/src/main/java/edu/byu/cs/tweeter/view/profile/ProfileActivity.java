package edu.byu.cs.tweeter.view.profile;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.ExecutionException;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.ProfileInfoPresenter;
import edu.byu.cs.tweeter.presenter.UnfollowPresenter;
import edu.byu.cs.tweeter.presenter.UserPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.ProfileTask;
import edu.byu.cs.tweeter.view.asyncTasks.UnfollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.UserTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The profile activity for the application. Contains tabs for story, following, and followers.
 */
public class ProfileActivity extends AppCompatActivity implements ProfileInfoPresenter.View, ProfileTask.Observer, UserPresenter.View, UserTask.Observer, FollowPresenter.View, FollowTask.Observer, UnfollowPresenter.View, UnfollowTask.Observer {

    private static final String LOG_TAG = "ProfileActivity";

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    public static final String DISPLAYED_USER = "DisplayedUser";

    private User user;
    private User displayedUser;
    private AuthToken authToken;

    private Button followButton;
    private TextView followeeCount;
    private TextView followerCount;
    private int numFollowers = 0;
    private int numFollowees = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ProfileInfoPresenter profileInfoPresenter = new ProfileInfoPresenter(this);
        UserPresenter userPresenter = new UserPresenter(this);
        FollowPresenter followPresenter = new FollowPresenter(this);
        UnfollowPresenter unfollowPresenter = new UnfollowPresenter(this);

        Uri uri = getIntent().getData();
        
        if (uri != null) {
            Toast loadingToast = Toast.makeText(this,"Loading...", Toast.LENGTH_LONG);
            loadingToast.show();
            UserRequest userRequest0 = new UserRequest(uri.getQueryParameter("requestinguser"));
            UserTask userTask0 = new UserTask(userPresenter, ProfileActivity.this);
            userTask0.execute(userRequest0);

            UserRequest userRequest1 = new UserRequest(uri.getQueryParameter("requesteduser"));
            UserTask userTask1 = new UserTask(userPresenter, ProfileActivity.this);
            userTask1.execute(userRequest1);

            authToken = new AuthToken(uri.getQueryParameter("auth"));
            try {
                user = userTask0.get().getUser();
                displayedUser = userTask1.get().getUser();
            } catch (ExecutionException | InterruptedException e) {
                handleException(e);
            }
            loadingToast.cancel();
        } else {
            user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
            displayedUser = (User) getIntent().getSerializableExtra(DISPLAYED_USER);
            authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);
        }
        if (user == null) {
            throw new RuntimeException("User not passed to activity");
        }
        if (displayedUser == null) {
            throw new RuntimeException("Displayed user not passed to activity");
        }
        if (authToken == null) {
            throw new RuntimeException("Auth token not passed to activity");
        }



        ProfileInfoRequest profileInfoRequest = new ProfileInfoRequest(displayedUser, user);
        ProfileTask profileTask = new ProfileTask(profileInfoPresenter, ProfileActivity.this);
        profileTask.execute(profileInfoRequest);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), displayedUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TextView userName = findViewById(R.id.userName);
        userName.setText(displayedUser.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(displayedUser.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(displayedUser.getImageBytes()));

        followeeCount = findViewById(R.id.followeeCount);
        updateFolloweeCount();

        followerCount = findViewById(R.id.followerCount);
        updateFollowerCount();

        followButton = findViewById(R.id.followButton);
        if (user.equals(displayedUser)) {
            followButton.setVisibility(View.GONE);
        }
        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (followButton.getText().toString().equals(getResources().getString(R.string.following))){
                    UnfollowRequest unfollowRequest = new UnfollowRequest(user, displayedUser, authToken);
                    UnfollowTask unfollowTask = new UnfollowTask(unfollowPresenter, ProfileActivity.this);
                    unfollowTask.execute(unfollowRequest);
                } else if (followButton.getText().toString().equals(getResources().getString(R.string.follow))){
                    FollowRequest followRequest = new FollowRequest(user, displayedUser, authToken);
                    FollowTask followTask = new FollowTask(followPresenter, ProfileActivity.this);
                    followTask.execute(followRequest);
                }
            }
        });
    }

    private void follow() {
        numFollowers++;
        updateFollowerCount();
        
        
        setButtonFollowing();
        
    }
    
    private void setButtonFollowing() {
        followButton.setText(R.string.following);
        followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }
    private void setButtonNotFollowing() {
        followButton.setText(R.string.follow);
        followButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void updateFollowerCount() {
        followerCount.setText(getString(R.string.followerCount, numFollowers));
    }
    private void updateFolloweeCount() {
        followeeCount.setText(getString(R.string.followeeCount, numFollowees));
    }

    /**
     * The callback method that gets invoked for a successful profile request. Updates the profile's number of followers and followees, and follow button.
     *
     * @param profileInfoResponse the response from the profile request.
     */
    @Override
    public void getProfileSuccessful(ProfileInfoResponse profileInfoResponse) {
        numFollowees = profileInfoResponse.getNumFollowees();
        numFollowers = profileInfoResponse.getNumFollowers();
        updateFollowerCount();
        updateFolloweeCount();

        if (profileInfoResponse.isFollowed()) {
            setButtonFollowing();
        } else {
            setButtonNotFollowing();
        }
    }

    /**
     * The callback method that gets invoked for an unsuccessful register. Displays a toast with a
     * message indicating why the profile request failed.
     *
     * @param profileInfoResponse the response from the profile request.
     */
    @Override
    public void getProfileUnsuccessful(ProfileInfoResponse profileInfoResponse) {
        Toast.makeText(this, "Failed to retrieve profile info: " + profileInfoResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void getUserSuccessful(UserResponse userResponse) { }

    @Override
    public void getUserUnsuccessful(UserResponse userResponse) {
        Toast.makeText(this, "Failed to get user: " + userResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void followSuccessful(FollowResponse followResponse) {
        numFollowers++;
        this.updateFollowerCount();
        this.setButtonFollowing();
    }

    @Override
    public void followUnsuccessful(FollowResponse followResponse) {
        Toast.makeText(this, "Failed to follow: " + followResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void unfollowSuccessful(UnfollowResponse unfollowResponse) {
        numFollowers--;
        updateFollowerCount();
        setButtonNotFollowing();
    }

    @Override
    public void unfollowUnsuccessful(UnfollowResponse unfollowResponse) {
        Toast.makeText(this, "Failed to unfollow: " + unfollowResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}