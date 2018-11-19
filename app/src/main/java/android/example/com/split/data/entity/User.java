package android.example.com.split.data.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * User
 */
@IgnoreExtraProperties
public class User {
    // firebase authentiction id
    private String authId;

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


    public User(String authId, String firstName, String lastName, String email, String phoneNumber) {
        this.authId = authId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    @Exclude
    public String getId() {
        return id;
    }


    public String getAuthId() {
        return authId;
    }

    public String getFirstName() {
        return firstName;

    }



    public String getLastName() {
        return lastName;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
