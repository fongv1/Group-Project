package android.example.com.split.ui.home.groups;

import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


// Simple implementation for a data set that consists of a List of Groups displayed using
// TextView widgets
public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupViewHolder> {

  private List<Group> mDataset;

  // Create the adapter with a dataset
  public GroupsRecyclerAdapter(List<Group> myDataset) {
    mDataset = myDataset;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_group, parent, false);
    GroupViewHolder vh = new GroupViewHolder(v, this);
    return vh;
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(GroupViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    holder.bind(mDataset.get(position));
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    if (mDataset == null) {
      mDataset = new ArrayList<>();
    }
    return mDataset.size();
  }

}


