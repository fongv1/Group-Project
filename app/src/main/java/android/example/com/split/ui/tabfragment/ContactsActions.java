package android.example.com.split.ui.tabfragment;

import android.example.com.split.data.entity.User;

import java.util.List;

public interface ContactsActions {

  //Checks
  void addContact(User user);

  void getContactDetailFromUI(String firstName, String lastName, String Email);

  boolean contactExist(List<User> users, String firstName, String lastName);

  //Actions
  User initialiseNewContact(String firstName, String lastName, String email);

  void saveNewContact(User user);

  void saveNewContactToRemote(User currentUser, User contact);

  public void updateUIWithNewContact();


  //Remove
  void removeContact(List<User> users, User user);

  void removeContactFromDB(User currentUser, User contact);

}
