package android.example.com.split;

import android.content.Context;
import android.content.Intent;
import android.example.com.split.data.entity.Group;
import android.support.annotation.CallSuper;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;

public abstract class BaseViewHolder<T extends Serializable> extends RecyclerView.ViewHolder
    implements View.OnClickListener {

  private final Context context;
  private final View itemView;
  private final Class<? extends FragmentActivity> detailActivityClass;
  private final String title;
  private T itemData;
  private OnDeleteItemListener onDeleteItemListener;

  protected BaseViewHolder(View itemView, Class<? extends FragmentActivity> detailActivityClass,
                           String title) {
    super(itemView);
    context = itemView.getContext();
    this.detailActivityClass = detailActivityClass;
    this.itemView = itemView;
    this.title = title;
    itemView.setOnClickListener(this);
    findAllViews(itemView);
  }

  protected abstract void findAllViews(View itemView);

  @CallSuper
  public void bind(T t) {
    setItemData(t);
  }

  protected abstract void bind(Group group, T expense, int position);

  protected void onItemClicked() {
    startDetailActivity();
  }

  protected View getItemView() {
    return itemView;
  }

  protected void setOnDeleteItemListener(OnDeleteItemListener onDeleteItemListener) {
    this.onDeleteItemListener = onDeleteItemListener;
  }

  protected void deleteItem(int position) {
    onDeleteItemListener.onDelete(position);
  }

  @Override
  public void onClick(View itemView) {
    onItemClicked();
  }

  public T getItemData() {
    return itemData;
  }

  protected void setItemData(T itemData) {
    this.itemData = itemData;
  }

  protected Class<?> getDetailActivityClass() {
    return detailActivityClass;
  }

  protected void startDetailActivity() {
    Intent intent = new Intent(context, getDetailActivityClass());
    intent.putExtra(title, getItemData());
    context.startActivity(intent);
  }
}
