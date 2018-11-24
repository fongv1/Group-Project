package android.example.com.split.ui.tabfragment;

import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.ui.recycleradapter.ExpensesRecyclerAdapter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExpensesTabFragment extends BaseTabFragment<ExpensesRecyclerAdapter, Expense> {

  private static final String TAG = "ExpensesTabFragment";
  private Group group;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_tab_expenses, container, false);
    // Create bundle to get the group passed from the GroupDetailActivity
    Bundle bundle = getArguments();
    group = (Group) bundle.get("group");
    //gets the expenses from the group
    setupRecyclerView(rootView, R.id.recyclerView_fragment_tab_expenses);
    return rootView;
  }

  @Override
  protected void setupRecyclerView(View rootView, int recyclerViewId) {
    mRecyclerView = (RecyclerView) rootView.findViewById(recyclerViewId);
    mRecyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);
    setData(group.getExpenses());
    setRecyclerAdapter(new ExpensesRecyclerAdapter(this.getContext(), getData(), group));
    mRecyclerView.setAdapter(getRecyclerAdapter());
  }
}
