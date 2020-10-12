package edu.byu.cs.tweeter.view.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

/**
 * Contains the minimum UI required to allow the user to login with a hard-coded user. Most or all
 * of this should be replaced when the back-end is implemented.
 */
public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, LoginTask.Observer, RegisterTask.Observer {

    private static final String LOG_TAG = "LoginActivity";

    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        EditText loginUsername = this.findViewById(R.id.loginUsername);
        loginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!loginUsername.getText().toString().startsWith("@")){
                    loginUsername.setText("@" + loginUsername.getText());
                    Selection.setSelection(loginUsername.getText(), loginUsername.getText().length());
                }
            }
        });
        EditText loginPassword = this.findViewById(R.id.loginPassword);
        Button loginButton = findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * in the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Logging In", Toast.LENGTH_LONG).show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                LoginRequest loginRequest = new LoginRequest(loginUsername.getText().toString(), loginPassword.getText().toString());
                LoginTask loginTask = new LoginTask(presenter, LoginActivity.this);
                loginTask.execute(loginRequest);
            }
        });

        EditText firstName = this.findViewById(R.id.firstName);
        EditText lastName = this.findViewById(R.id.lastName);
        EditText registerUsername = this.findViewById(R.id.registerUsername);
        registerUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!registerUsername.getText().toString().startsWith("@")){
                    registerUsername.setText("@" + registerUsername.getText());
                    Selection.setSelection(registerUsername.getText(), registerUsername.getText().length());
                }
            }
        });
        EditText registerPassword = this.findViewById(R.id.registerPassword);
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a register request. The user is hard-coded, so it doesn't matter what data we put
             * in the RegisterRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Registering", Toast.LENGTH_LONG).show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                RegisterRequest registerRequest = new RegisterRequest(firstName.getText().toString(),
                        lastName.getText().toString(), registerUsername.getText().toString(),
                        registerPassword.getText().toString(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
                RegisterTask registerTask = new RegisterTask(presenter, LoginActivity.this);
                registerTask.execute(registerRequest);
            }
        });
    }

    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(this, "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * The callback method that gets invoked for a successful register. Displays the MainActivity.
     *
     * @param registerResponse the response from the login request.
     */
    @Override
    public void registerSuccessful(RegisterResponse registerResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, registerResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, registerResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful register. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param registerResponse the response from the login request.
     */
    @Override
    public void registerUnsuccessful(RegisterResponse registerResponse) {
        Toast.makeText(this, "Failed to register: " + registerResponse.getMessage(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
