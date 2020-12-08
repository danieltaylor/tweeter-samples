package edu.byu.cs.tweeter.client.model.service.request;

/**
 * Contains all the information needed to make a register request.
 */
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String alias;
    private String password;
    private String imageUrl;
    private byte[] imageBytes;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private RegisterRequest() {}

    /**
     * Creates an instance.
     *
     * @param firstName the first name of the user to be registered.
     * @param lastName the last name of the user to be registered.
     * @param alias the username of the user to be registered.
     * @param password the password of the user to be registered.
     * @param imageUrl the url of the profile picture of the user to be registered.
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
     * @param alias the alias of the user to be registered.
     * @param password the password of the user to be registered.
     * @param imageBytes the image bytes of the profile picture of the user to be registered.
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
     * Sets the first name of the user to be registered by this request.
     *
     * @param firstName the first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
     * Sets the last name of the user to be registered by this request.
     *
     * @param lastName the last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
     * Sets the username of the user to be registered by this request.
     *
     * @param alias the username.
     */
    public void setAlias(String alias) {
        this.alias = alias;
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
     * Sets the password of the user to be registered by this request.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
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
     * Sets the image url of the user to be registered by this request.
     *
     * @param imageUrl the image url.
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * Returns the image bytes of the user to be registered by this request.
     *
     * @return the image bytes.
     */
    public byte[] getImageBytes() {
        return imageBytes;
    }

    /**
     * Sets the image bytes of the user to be registered by this request.
     *
     * @param imageBytes the image bytes.
     */
    public void getImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
