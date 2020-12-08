package edu.byu.cs.tweeter.client.model.service.request;

/**
 * Contains all the information needed to make a user request.
 */
public class UserRequest {

    private String alias;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private UserRequest() {}

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

    /**
     * Sets the alias of the user to be retrieved by this request.
     *
     * @param alias the alias.
     */
    public void getAlias(String alias) {
        this.alias = alias;
    }
}
