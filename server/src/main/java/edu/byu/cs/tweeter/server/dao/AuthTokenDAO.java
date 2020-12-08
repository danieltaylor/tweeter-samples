package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.client.model.service.request.UserRequest;

/**
 * A DAO for accessing 'following' data from the database.
 */
public class AuthTokenDAO {

    /**
     * Adds a new auth token to the database for the specified user.
     * Only to be called after validating a register or login request via UserDAO.
     * Returns null if the alias is invalid.
     *
     * @param alias the alias of the user to create an auth token for
     * @return the new auth token.
     */
    public AuthToken create(String alias) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert alias != null;

        return new AuthToken();
    }

    /**
     * Checks the database to see if an auth token exists, is valid, and is associated with a given alias.
     *
     * @param authToken the auth token
     * @param alias the alias
     * @return true if valid, false if not.
     */
    public boolean isValid(AuthToken authToken, String alias) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert authToken != null;
        assert alias != null;

        return true;
    }

    /**
     * Invalidates a given auth token.
     *
     * @param authToken the auth token
     * @return true if the auth token was found and invalidated, false if not.
     */
    public boolean invalidate(AuthToken authToken) {
        // TODO: Generates dummy data. Replace with a real implementation.
        assert authToken != null;

        return true;
    }
}
