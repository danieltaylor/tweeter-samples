package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class UserRequest {

    private final String alias;

    /**
     * Creates an instance.
     *
     * @param alias the alias of the user to be retrieved.
     */
    public UserRequest(String alias) {
        this.alias = alias;
    }

    /**
     * Returns the alias of the user to be retrieved by this request.
     *
     * @return the alias.
     */
    public String getAlias() {
        return alias;
    }
}
