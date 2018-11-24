package android.example.com.split.ui.home.groups.group.members;

import android.content.Context;
import android.example.com.split.BaseRecyclerAdapter;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class MembersRecyclerAdapter extends BaseRecyclerAdapter<MemberViewHolder, User> {

  // Create the adapter with a dataset
  public MembersRecyclerAdapter(List<User> myDataset, Context context) {
    super(myDataset, context);
  }

  // Create new views (invoked by the layout manager)
  @Override
  public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_group_member, parent, false);
    MemberViewHolder vh = new MemberViewHolder(v);
    return vh;
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(MemberViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    User user = getDataset().get(position);
    holder.bind(user);
  }
}


