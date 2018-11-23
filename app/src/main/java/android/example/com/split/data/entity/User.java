package android.example.com.split.data.entity;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User
 */
@IgnoreExtraProperties
public class User implements Serializable, com.alaskalany.lib.model.User {

    // firebase authentiction id
    @Exclude
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

    private List<String> contacts;


    @Override
    public List<String> getContacts() {
        return contacts;
    }

    @Override
    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }


    /**
     * User
     */

    public User() {

    }

    //
    public User(String firstName, String lastName, String email, String phoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // constructor with auth id
    public User(String id, String firstName, String lastName, String email, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        contacts = new ArrayList<String>();
    }




    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @Exclude
    public String getAuthId() {
        return authId;
    }

    @Override
    public String getFirstName() {
        return firstName;

    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void addToContactList(String newUser){
        contacts.add(newUser);
    }

    public String toString() {
        return firstName + " " + lastName;
    }
}
