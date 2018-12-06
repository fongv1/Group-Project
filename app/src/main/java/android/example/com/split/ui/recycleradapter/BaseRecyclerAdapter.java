package android.example.com.split.ui.recycleradapter;

import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.OnEditItemListener;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder, M> extends
    RecyclerView.Adapter<T> implements OnDeleteItemListener, OnEditItemListener {

  private List<M> data;

  BaseRecyclerAdapter(final List<M> myDataset) {
    if (myDataset == null) {
      throw new NullPointerException();
    }
    this.data = myDataset;
  }
  public  BaseRecyclerAdapter () {}
  // Create new views (invoked by the layout manager)
  @NonNull
  @Override
  public abstract T onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

  @NonNull
  protected abstract T getViewHolder(View v);

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public abstract void onBindViewHolder(@NonNull T holder, int position);

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return data.size();
  }

  public List<M> getDataset() {
    return data;
  }

  @Override
  public void onDelete(int position) {
    data.remove(position);
    notifyItemRemoved(position);
    notifyDataSetChanged();
  }

  @Override
  public void onEdit(int position) {
    notifyItemChanged(position);
  }
}
