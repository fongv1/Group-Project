package android.example.com.split.ui.recycleradapter;

import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.ui.viewholder.GroupViewHolder;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Groups displayed using
// TextView widgets
public class GroupsRecyclerAdapter extends BaseRecyclerAdapter<GroupViewHolder, Group> {

  //private List<Group> mDataset;

  // Create the adapter with a dataset
  public GroupsRecyclerAdapter(List<Group> groups) {
    super(groups);
  }

/*  public List<Group> getDataset() {
    return mDataset;
  }*/

  // Create new views (invoked by the layout manager)
  @NonNull
  @Override
  public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_group, parent, false);
    return getViewHolder(v);
  }

  @NonNull
  @Override
  protected GroupViewHolder getViewHolder(View v) {
    return new GroupViewHolder(v);
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    //Group group = mDataset.get(position);
    holder.bind(getDataset().get(position));
  }
}