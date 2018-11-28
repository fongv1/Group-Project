package android.example.com.split.ui.tabfragment;

import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;

import java.util.List;

public interface GroupsActions {

  // Adding
  void getNewGroupDetailsFromUi(String groupName);

  boolean groupExists(List<Group> groups, String groupName);

  void addNewGroup(List<Group> groups, Group group);

  // Actions
  Group initializeNewGroup(String groupName);

  void saveNewGroup(List<Group> groups, Group group);

  void saveNewGroupToRemoteDb(User currentUser, Group group);

  void updateUiAfterAddingNewGroup();

  // Removing
  void removeGroup(List<Group> groups, Group group);

  void rempoveGroupFromRemoteDb(User currentUser, Group group);

  void updateUiAfterRemovingGroup();
}
