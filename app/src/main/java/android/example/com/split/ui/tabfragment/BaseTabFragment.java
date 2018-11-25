package android.example.com.split.ui.tabfragment;

import android.example.com.split.ui.recycleradapter.BaseRecyclerAdapter;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;
import java.util.List;

public abstract class BaseTabFragment<T extends BaseRecyclerAdapter, M extends Serializable>
    extends Fragment {

  protected RecyclerView recyclerView;
  private T recyclerAdapter;
  private List<M> data;

  public T getRecyclerAdapter() {
    return recyclerAdapter;
  }

  public void setRecyclerAdapter(T recyclerAdapter) {
    this.recyclerAdapter = recyclerAdapter;
  }

  public List<M> getData() {
    return data;
  }

  public void setData(List<M> data) {
    this.data = data;
  }

  protected abstract void setupRecyclerView(View rootView, int recyclerViewId);
}
