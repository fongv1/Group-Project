package android.example.com.split.ui.home.groups.group.members;

import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class MembersRecyclerAdapter extends RecyclerView.Adapter<MemberViewHolder> {

  private List<User> mDataset;

  // Create the adapter with a dataset
  public MembersRecyclerAdapter(List<User> myDataset) {
    mDataset = myDataset;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_group_member, parent, false);
    MemberViewHolder vh = new MemberViewHolder(v, this);
    return vh;
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(MemberViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    User user = mDataset.get(position);
    holder.bind(user);
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return mDataset.size();
  }

}


