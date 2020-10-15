package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a login request.
 */
public class RegisterRequest {

    private final String firstName;
    private final String lastName;
    private final String alias;
    private final String password;
    private final String imageUrl;
    private final byte[] imageBytes;

    /**
     * Creates an instance.
     *
     * @param firstName the first name of the user to be registered.
     * @param lastName the last name of the user to be registered.
     * @param alias the username of the user to be registered.
     * @param password the password of the user to be registered.
     * @param imageUrl url of the profile picture of the user to be registered.
     */
    public RegisterRequest(String firstName, String lastName, String alias, String password, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
        this.imageUrl = imageUrl;
        imageBytes = null;
    }

    /**
     * Creates an instance.
     *
     * @param firstName the first name of the user to be registered.
     * @param lastName the last name of the user to be registered.
     * @param alias the username of the user to be registered.
     * @param password the password of the user to be registered.
     * @param imageBytes url of the profile picture of the user to be registered.
     */
    public RegisterRequest(String firstName, String lastName, String alias, String password, byte[] imageBytes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.alias = alias;
        this.password = password;
        this.imageBytes = imageBytes;
        imageUrl = null;
    }

    /**
     * Returns the first name of the user to be registered by this request.
     *
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user to be registered by this request.
     *
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the username of the user to be registered by this request.
     *
     * @return the username.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Returns the password of the user to be registered by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the image url of the user to be registered by this request.
     *
     * @return the image url.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Returns the image bytes of the user to be registered by this request.
     *
     * @return the image bytes.
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }
}
