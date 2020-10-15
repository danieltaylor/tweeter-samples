package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.ProfileInfoRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.ProfileInfoResponse;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.ProfileInfoPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.ProfileTask;
import edu.byu.cs.tweeter.view.login.LoginActivity;
import edu.byu.cs.tweeter.view.main.post.PostActivity;
import edu.byu.cs.tweeter.view.profile.ProfileActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements ProfileInfoPresenter.View, ProfileTask.Observer, LogoutPresenter.View, LogoutTask.Observer {

    private static final String LOG_TAG = "MainActivity";

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private ProfileInfoPresenter profileInfoPresenter;
    private LogoutPresenter logoutPresenter;

    private User user;
    private AuthToken authToken;
    private ViewPager viewPager;
    private TextView followeeCount;
    private TextView followerCount;
    private int numFollowers = 0;
    private int numFollowees = 0;
    private int currentTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileInfoPresenter = new ProfileInfoPresenter(this);
        logoutPresenter = new LogoutPresenter(this);

        user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }
        authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        ProfileInfoRequest profileInfoRequest = new ProfileInfoRequest(user, user);
        ProfileTask profileTask = new ProfileTask(profileInfoPresenter, MainActivity.this);
        profileTask.execute(profileInfoRequest);

        followeeCount = findViewById(R.id.followeeCount);
        followerCount = findViewById(R.id.followerCount);
        this.reloadProfileInfo();
        this.reloadTabs();

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        FloatingActionButton fab = findViewById(R.id.fab);

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra(ProfileActivity.CURRENT_USER_KEY, user);
                intent.putExtra(ProfileActivity.AUTH_TOKEN_KEY, authToken);
                startActivity(intent);
            }
        });
    }

    private void reloadTabs() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(currentTab);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    private void reloadProfileInfo() {
        ProfileInfoRequest profileInfoRequest = new ProfileInfoRequest(user, user);
        ProfileTask profileTask = new ProfileTask(profileInfoPresenter, MainActivity.this);
        profileTask.execute(profileInfoRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutMenu) {
            LogoutRequest logoutRequest = new LogoutRequest(authToken);
            LogoutTask logoutTask = new LogoutTask(logoutPresenter, MainActivity.this);
            logoutTask.execute(logoutRequest);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentTab = viewPager.getCurrentItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadTabs();
        reloadProfileInfo();
    }

    @Override
    public void onBackPressed() {
        viewPager.setCurrentItem(0);
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
    public void logoutSuccessful(LogoutResponse logoutResponse) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful logout. Displays a toast with a
     * message indicating why the logout request failed.
     *
     * @param logoutResponse the response from the logout request.
     */
    @Override
    public void logoutUnsuccessful(LogoutResponse logoutResponse) {
        Toast.makeText(this, "Failed to logout: " + logoutResponse.getMessage(), Toast.LENGTH_LONG).show();
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