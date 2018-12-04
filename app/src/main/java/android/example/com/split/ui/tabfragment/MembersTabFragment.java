package android.example.com.split.ui.tabfragment;

import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.recycleradapter.MembersRecyclerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MembersTabFragment extends BaseTabFragment<MembersRecyclerAdapter, User> implements
    MembersActions {

  private static final String TAG = "MembersTabFragment";
  private Group group;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_tab_members, container, false);
    setupRecyclerView(rootView, R.id.recyclerView_fragment_tab_expenses);
    return rootView;
  }

  @Override
  protected void setupRecyclerView(View rootView, int recyclerViewId) {
    recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_fragment_tab_members);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    // Create bundle to get the group passed from the GroupDetailActivity
    Bundle bundle = getArguments();
    group = (Group) bundle.get("group");
    //gets the expenses from the group
    setData(group.getUserMembers());
    setRecyclerAdapter(new MembersRecyclerAdapter(getData()));
    recyclerView.setAdapter(getRecyclerAdapter());
  }

  @Override
  public void addNewMember(User member) {

  }

  @Override
  public List<User> getContactsNotInAGroup(Group group, List<User> contacts) {
    return null;
  }

  @Override
  public User selectContactFromContacts(List<User> contacts, User contact) {
    return null;
  }

  @Override
  public boolean isContactMemberInGroup(User contact, Group group) {
    return false;
  }

  @Override
  public User initializeNewGroupMember(User newMember, Group group) {
    return null;
  }

  @Override
  public void saveNewMemberInGroup(User newMember, Group group) {

  }

  @Override
  public void saveNewMemberInGroupToRemoteDb(User newMember, Group group) {

  }

  @Override
  public void updateUiAfterAddingNewMemberToGroup() {

  }

  @Override
  public void removeGroupMember(User member, Group group) {

  }

  @Override
  public boolean groupMemberHasZeroBalanceInGroup(User user, Group group) {
    return false;
  }

  @Override
  public void removeGroupMemberInRemoteDb(User member, Group group) {

  }

  @Override
  public void updateUiAfterRemovingGroupMember() {

  }
}
