package android.example.com.split;

import android.example.com.split.data.entity.Group;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

  private final View itemView;

  public BaseViewHolder(View itemView) {
    super(itemView);
    this.itemView = itemView;
    findAllViews(itemView);
  }

  protected abstract void findAllViews(View itemView);

  public abstract void bind(T t);

  public abstract void bind(Group group, T expense, int position);

  public View getItemView() {
    return itemView;
  }
}
