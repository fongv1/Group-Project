package android.example.com.split.ui.tabfragment;

import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.recycleradapter.MembersRecyclerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MembersTabFragment extends Fragment {

  private static final String TAG = "MembersTabFragment";
  private List<User> dataset;
  private Group group;
  private MembersRecyclerAdapter membersRecyclerAdapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_tab_members, container, false);

    RecyclerView mRecyclerView = (RecyclerView) rootView
        .findViewById(R.id.recyclerView_fragment_tab_members);
    mRecyclerView.setHasFixedSize(true);

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);

    // Create bundle to get the group passed from the GroupDetailActivity
    Bundle bundle = getArguments();
    group = (Group) bundle.get("group");

    //gets the expenses from the group
    dataset = group.getUserMembers();

    membersRecyclerAdapter = new MembersRecyclerAdapter(this.getContext(), dataset, group);
    mRecyclerView.setAdapter(membersRecyclerAdapter);


    return rootView;
  }

  public MembersRecyclerAdapter getAdapter() {
    return membersRecyclerAdapter;
  }

}
