package android.example.com.split;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder, M> extends
    RecyclerView.Adapter<T> implements OnDeleteItemListener {

  private List<M> mDataset;
  private Context context;

  public BaseRecyclerAdapter(List<M> myDataset, Context context) {
    this.mDataset = myDataset;
    this.context = context;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public abstract void onBindViewHolder(T holder, int position);

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return mDataset.size();
  }

  public List<M> getDataset() {
    return mDataset;
  }

  @Override
  public void onDelete(int position) {
    mDataset.remove(position);
    notifyItemRemoved(position);
    notifyItemRangeChanged(position, getItemCount());
  }
}
