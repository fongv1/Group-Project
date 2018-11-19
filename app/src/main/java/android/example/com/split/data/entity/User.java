package android.example.com.split.data.entity;

import android.example.com.split.data.model.DisplayName;
import android.example.com.split.data.model.Email;
import android.example.com.split.data.model.Id;
import android.example.com.split.data.model.PhoneNumber;
import android.support.annotation.Nullable;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * User
 */
@IgnoreExtraProperties
public class User implements Id, DisplayName, Email, PhoneNumber {

    /**
     * User UD
     */
    private String id;
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
    @Override
    public String getId() {
        return id;
    }

    /**
     * Set user ID
     *
     * @param id User ID
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get user's first name
     *
     * @return User's first name
     */
    @Override
    @Nullable
    public String getFirstName() throws Exception {
        if (firstName == null) {
            throw new Exception("First name is null");
        }
        return firstName;
    }

    /**
     * Set user's first name
     *
     * @param firstName User's last name
     */
    @Override
    public void setFirstName(String firstName) throws IllegalArgumentException {
        if (firstName.length() > 32) {
            throw new IllegalArgumentException("First name must not exceed 32 characters.");
        }
        this.firstName = firstName;
    }

    /**
     * Get user's last name
     *
     * @return {@link User} User's last name
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * Set user's last name
     *
     * @param lastName User's last name
     */
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get user's email
     *
     * @return user's email
     */
    @Override
    public String getEmail() {
        return email;
    }

    /**
     * Set user's email
     *
     * @param email email
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user's phone number
     *
     * @return User's phone number
     */
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Set user's phone number
     *
     * @param phoneNumber User's phone number
     */
    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
