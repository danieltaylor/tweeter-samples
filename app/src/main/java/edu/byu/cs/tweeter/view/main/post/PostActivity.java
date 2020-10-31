package edu.byu.cs.tweeter.view.main.post;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.PostTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The profile activity for the application. Contains tabs for story, following, and followers.
 */
public class PostActivity extends AppCompatActivity implements PostPresenter.View, PostTask.Observer {

    private static final String LOG_TAG = "PostActivity";

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private PostPresenter presenter;
    private Toast postToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        presenter = new PostPresenter(this);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        TextView timestamp = findViewById(R.id.timestamp);
        timestamp.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM yyyy hh:mm a")));

        EditText statusBody = findViewById(R.id.statusBody);
        statusBody.requestFocus();

        Button postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Makes a post request.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                postToast = Toast.makeText(PostActivity.this, "Posting...", Toast.LENGTH_SHORT);
                postToast.show();

                Status status = new Status(user, statusBody.getText().toString());
                PostRequest postRequest = new PostRequest(status, authToken);
                PostTask postTask = new PostTask(presenter, PostActivity.this);
                postTask.execute(postRequest);
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Cancels the post activity.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void postSuccessful(PostResponse postResponse) {
        postToast.cancel();
        onBackPressed();
    }

    @Override
    public void postUnsuccessful(PostResponse postResponse) {
        Toast.makeText(this, "Unable to post. Try again later.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to post because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}