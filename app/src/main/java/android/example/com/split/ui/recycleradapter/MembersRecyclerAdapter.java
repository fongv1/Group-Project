package android.example.com.split.ui.recycleradapter;

import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.viewholder.MemberViewHolder;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class MembersRecyclerAdapter extends BaseRecyclerAdapter<MemberViewHolder, User> {

  // Create the adapter with a dataset
  public MembersRecyclerAdapter(List<User> groupMembers) {
    super(groupMembers);
  }

  // Create new views (invoked by the layout manager)
  @NonNull
  @Override
  public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_group_member, parent, false);
    return getViewHolder(v);
  }

  @Override
  @NonNull
  protected MemberViewHolder getViewHolder(View v) {
    return new MemberViewHolder(v, this);
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    holder.bind(getDataset().get(position), position);
  }

  @Override
  public void onDelete(String id) {

  }
}


