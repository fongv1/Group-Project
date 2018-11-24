package android.example.com.split.ui.home.groups.group.members;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder, M> extends RecyclerView
    .Adapter<T> {

  protected List<M> mDataset;

  BaseRecyclerAdapter(List<M> myDataset) {
    mDataset = myDataset;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public abstract void onBindViewHolder(T holder, int position);

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public abstract int getItemCount();
}
