package android.example.com.split.ui.tabfragment;

import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;

import java.util.List;

public interface MembersActions {

  // Adding
  void addNewMember(User member);

  List<User> getContactsNotInAGroup(Group group, List<User> contacts);

  User selectContactFromContacts(List<User> contacts, User contact);

  boolean isContactMemberInGroup(User contact, Group group);

  // Actions
  User initializeNewGroupMember(User newMember, Group group);

  void saveNewMemberInGroup(User newMember, Group group);

  void saveNewMemberInGroupToRemoteDb(User newMember, Group group);

  void updateUiAfterAddingNewMemberToGroup();

  // Removing
  void removeGroupMember(User member, Group group);

  boolean groupMemberHasZeroBalanceInGroup(User user, Group group);

  void removeGroupMemberInRemoteDb(User member, Group group);

  void updateUiAfterRemovingGroupMember();


}
