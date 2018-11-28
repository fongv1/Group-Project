package android.example.com.split.ui.tabfragment;

import android.example.com.split.data.entity.User;

import java.util.List;

public interface ContactsActions {

  //Checks
  public void addContact(User user);

  public void getContactDetailFromUI(String firstName, String lastName, String Email);

  public boolean contactExist(List<User> users, String firstName, String lastName);

  //Actions
  public User initialiseNewContact(String firstName, String lastName, String email);

  public void saveNewContact(User user);

  public void saveNewContactToRemote(User user);

  public void updateUIWithNewContact(User user);

  //Remove
  public void removeContact(List<User> users, User user);

  public void removeContactFromDB(User user);

}
