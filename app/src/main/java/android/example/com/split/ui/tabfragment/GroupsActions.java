package android.example.com.split.ui.tabfragment;

import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;

import java.util.List;

public interface GroupsActions {

  // Adding
  Group getNewGroupDetailsFromUi(String groupName);

  boolean groupExists(List<Group> groups, String groupName);

  void addNewGroup(List<Group> groups, Group group);

  // Actions
  Group initializeNewGroup(String groupName);

  boolean saveNewGroup(User currentUser, List<Group> groups, Group group);

  void saveNewGroupToRemoteDb(User currentUser, Group group);

  void updateUiAfterAddingNewGroup();

  // Removing
  void removeGroup(List<Group> groups, Group group);

  void removeGroupFromRemoteDb(User currentUser, Group group);

  void updateUiAfterRemovingGroup();

  void fetchGroupsFromRemoteDb(User currentUser);
}
