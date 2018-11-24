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

import java.util.List;

public class ExpensesTabFragment extends BaseTabFragment {

  private static final String TAG = "ExpensesTabFragment";
  private List<Expense> dataset;
  private ExpensesRecyclerAdapter expensesRecyclerAdapter;
  private Group group;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_tab_expenses, container, false);

    RecyclerView mRecyclerView = (RecyclerView) rootView
        .findViewById(R.id.recyclerView_fragment_tab_expenses);
    mRecyclerView.setHasFixedSize(true);

    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    mRecyclerView.setLayoutManager(mLayoutManager);

    // Create bundle to get the group passed from the GroupDetailActivity
    Bundle bundle = getArguments();
    group = (Group) bundle.get("group");

    //gets the expenses from the group
    dataset = group.getExpenses();

    expensesRecyclerAdapter = new ExpensesRecyclerAdapter(this.getContext(), dataset, group);
    mRecyclerView.setAdapter(expensesRecyclerAdapter);

    return rootView;
  }

  public ExpensesRecyclerAdapter getAdapter() {
    return expensesRecyclerAdapter;
  }

}
