package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.client.model.service.LogoutService;
import edu.byu.cs.tweeter.client.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.client.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.AuthTokenDAO;

public class LogoutServiceImpl implements LogoutService {

    @Override
    public LogoutResponse logout(LogoutRequest request) {
        if (getAuthTokenDAO().invalidate(request.getAuthToken())) {
            return new LogoutResponse();
        } else {
            return new LogoutResponse("The auth token could not be invalidated.");
        }
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
