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
public class User implements Serializable {

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


    public List<String> getContacts() {
        return contacts;
    }

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




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public String getAuthId() {
        return authId;
    }

    public String getFirstName() {
        return firstName;

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void addToContactList(String newUser){
        contacts.add(newUser);
    }

}
