package android.example.com.split.ui.tabfragment;


import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.data.repository.GroupDataRepository;
import android.example.com.split.ui.recycleradapter.GroupsRecyclerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GroupsTabFragment extends BaseTabFragment<GroupsRecyclerAdapter, Group> implements
    GroupsActions {

  private List<Group> groups = new ArrayList<>();

  public GroupsTabFragment() {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Initialize dataset, this data would usually come from a local content provider or remote
    // server.
    initDataset();
  }

  // Create dummy data
  private void initDataset() {
    setData(new ArrayList<Group>());
    Random rand = new Random();

    for (int i = 0; i < 5; i++) {
      Group group = new Group();
      String groupName = "Group " + i;
      group.setName(groupName);

      for (int j = 0; j < 5; j++) {
        User user = new User();
        user.setFirstName("Member " + j);
        user.setLastName(groupName);
        group.addUserMember(user);
      }

      for (int j = 0; j < 8; j++) {
        Expense expense = new Expense();
        User user = group.getUserMembers().get(rand.nextInt(5));
        expense.setUser(user);
        expense.setPayerName(user.getFirstName());
        expense.setPaymentAmount(rand.nextInt(10));
        expense.setTittle("Expense " + j);
        group.addExpense(expense);
      }
      getData().add(group);
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_tab_groups, container, false);
    setupRecyclerView(rootView, R.id.recyclerView_fragment_tab_expenses);
    return rootView;
  }

  @Override
  public List<Group> getData() {
    //setData(get);
    return super.getData();
  }

  @Override
  protected void setupRecyclerView(View rootView, int recyclerViewId) {
    recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_fragment_tab_groups);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    setRecyclerAdapter(new GroupsRecyclerAdapter(getData()));
    recyclerView.setAdapter(getRecyclerAdapter());
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    User user = new User();
    if (firebaseUser != null) {
      user.setId(firebaseUser.getUid());
      fetchGroupsFromRemoteDb(user);
    }
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

  }

  @Override
  public void onStart() {
    super.onStart();

  }

  @Override
  public Group getNewGroupDetailsFromUi(String groupName) {
    Group group = initializeNewGroup(groupName);
    if (groupExists(groups, groupName)) {
      Toast.makeText(getActivity(), "Group exists", Toast.LENGTH_SHORT).show();
      return null;
    }
    return group;
  }

  @Override
  public boolean groupExists(List<Group> groups, String groupName) {
    for (Group group : groups) {
      if (group.getName().equals(groupName)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void addNewGroup(List<Group> groups, Group group) {

  }

  @Override
  public Group initializeNewGroup(String groupName) {
    Group group = new Group();
    group.setName(groupName);
    return group;
  }

  @Override
  public boolean saveNewGroup(User currentUser, List<Group> groups, Group group) {
    if ((group != null) && (groups != null)) {
      if (groupExists(groups, group.getName())) {
        return false;
      }
      if (!group.getMembers().contains(currentUser.getId())) {
        group.addMember(currentUser.getId());
      }
      groups.add(group);
      return true;
    }
    return false;
  }

  @Override
  public void saveNewGroupToRemoteDb(User currentUser, Group group) {
    GroupDataRepository repository = new GroupDataRepository();
    repository.addGroup(group, new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (msg.getData().getBoolean(GroupDataRepository.SUCCESS)) {
          Toast.makeText(getContext(), "Saved group in remote", Toast.LENGTH_SHORT).show();
        }
        else {
          Toast.makeText(getContext(), "Failed to save group in remote", Toast.LENGTH_SHORT).show();
        }
        return false;
      }

    });
  }

  @Override
  public void updateUiAfterAddingNewGroup() {
    getRecyclerAdapter().notifyDataSetChanged();
  }

  @Override
  public void removeGroup(List<Group> groups, Group group) {
    if (group != null && groups != null) {
      if (groupExists(groups, group.getName())) {
        groups.remove(group);
      }
    }
  }

  @Override
  public void removeGroupFromRemoteDb(User currentUser, Group group) {
    GroupDataRepository repository = new GroupDataRepository();
    repository.removeGroup( group, new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (msg.getData().getBoolean(GroupDataRepository.SUCCESS)) {
          Toast.makeText(getContext(), "Group removed from remote", Toast.LENGTH_SHORT).show();
        }
        else {
          Toast.makeText(getContext(), "Failed to remove group from remote", Toast.LENGTH_SHORT).show();
        }
        return false;
      }

    });
  }

  @Override
  public void updateUiAfterRemovingGroup() {
    getRecyclerAdapter().notifyDataSetChanged();
  }

  @Override
  public void fetchGroupsFromRemoteDb(User currentUser) {
    GroupDataRepository repository = new GroupDataRepository();
    repository.getGroupList(currentUser.getId(), new Handler.Callback() {
      @Override
      public boolean handleMessage(Message msg) {
        if (msg.getData().getBoolean(GroupDataRepository.SUCCESS, false)) {
          List<Group> groups = (List<Group>) msg.getData().getSerializable(GroupDataRepository
                                                                               .GROUP_LIST);
          // are we adding here to local memory
          getRecyclerAdapter().getDataset().addAll(groups);
        }
        getRecyclerAdapter().notifyDataSetChanged();
        return false;
      }
    });
  }
}
