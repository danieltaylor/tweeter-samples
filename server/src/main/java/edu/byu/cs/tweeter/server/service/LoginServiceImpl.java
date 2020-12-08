package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.domain.AuthToken;
import edu.byu.cs.tweeter.client.model.domain.User;
import edu.byu.cs.tweeter.client.model.service.LoginService;
import edu.byu.cs.tweeter.client.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.client.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class LoginServiceImpl implements LoginService {

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = getUserDAO().login(request);
        if (user != null) {
            AuthToken authToken = getAuthTokenDAO().create(user.getAlias());
            return new LoginResponse(user, authToken);
        } else {
            return new LoginResponse("The user either does not exist or the password was incorrect.");
        }
    }

    /**
     * Returns an instance of {@link UserDAO}. Allows mocking of the UserDAO class
     * for testing purposes. All usages of UserDAO should get their UserDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    UserDAO getUserDAO() {
        return new UserDAO();
    }

    /**
     * Returns an instance of {@link AuthTokenDAO}. Allows mocking of the AuthTokenDAO class
     * for testing purposes. All usages of AuthTokenDAO should get their AuthTokenDAO
     * instance from this method to allow for mocking of the instance.
     *
     * @return the instance.
     */
    AuthTokenDAO getAuthTokenDAO() {
        return new AuthTokenDAO();
    }
}
