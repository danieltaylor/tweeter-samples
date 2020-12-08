package edu.byu.cs.tweeter.server.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;
import edu.byu.cs.tweeter.client.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.client.model.service.response.UserResponse;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class UserDAO {
    // This is the hard coded followee data returned by the 'getFollowees()' method
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
    private static final String FEMALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/daisy_duck.png";

    private final User user0 = new User("Test", "User", MALE_IMAGE_URL);

    /**
     * Gets the user from the database that is specified in the login request, if the password is correct.
     * Returns null if the user is not found or password is incorrect.
     *
     * @param request contains the username and password of the user to be logged in.
     * @return the user.
     */
    public User login(LoginRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getAlias() != null;
        assert request.getPassword() != null;

        return user0;
    }

    /**
     * Registers a new user in the database from the register request, and returns the new user.
     * Returns null if the user cannot be created.
     *
     * @param request contains the registration information for the new user.
     * @return the newly registered user.
     */
    public User register(RegisterRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getAlias() != null;
        assert request.getPassword() != null;

        return user0;
    }

    /**
     * Gets the user from the database that is specified by the alias in the user request.
     *
     * @param request contains the alias of the user to be retrieved.
     * @return the user.
     */
    public UserResponse user(UserRequest request) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert request.getAlias() != null;

        return new UserResponse(user0);
    }
}
