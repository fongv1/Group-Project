package android.example.com.split.data;

/**
 * User
 */
public class User {

    /**
     * User UD
     */
    private String userId;
    /**
     * User's first name
     */
    private String firstName;
    /**
     * User's last name
     */
    private String lastName;
    /**
     * User's email
     */
    private String email;
    /**
     * User's phone number
     */
    private String phoneNumber;

    /**
     * User
     */
    public User() {
    }

    /**
     * Get user ID
     *
     * @return User ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set user ID
     *
     * @param userId User ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get user's first name
     *
     * @return User's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set user's first name
     *
     * @param firstName User's last name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get user's last name
     *
     * @return {@link User} User's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set user's last name
     *
     * @param lastName User's last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get user's email
     *
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user's email
     *
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user's phone number
     *
     * @return User's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set user's phone number
     *
     * @param phoneNumber User's phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
