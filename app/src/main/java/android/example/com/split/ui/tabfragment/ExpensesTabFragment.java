package android.example.com.split.ui.tabfragment;

import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
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

public class ExpensesTabFragment extends BaseTabFragment<ExpensesRecyclerAdapter, Expense> implements ExpensesActions{

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
    recyclerView = (RecyclerView) rootView.findViewById(recyclerViewId);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
    recyclerView.setLayoutManager(mLayoutManager);
    setData(group.getExpenses());
    setRecyclerAdapter(new ExpensesRecyclerAdapter(getData(), group));
    recyclerView.setAdapter(getRecyclerAdapter());
  }

  @Override
  public void addExpense(Group group, Expense expense) {

  }

  @Override
  public void getExpenseDetailFromUI(String title, double amount, String payerName) {

  }

  @Override
  public void populateSpinnerWithMembers(List<User> users) {

  }

  @Override
  public User selectPayer(Group group, int position) {
    return null;
  }

  @Override
  public User selectPayer(Group group, User payer) {
    return null;
  }

  @Override
  public boolean validateExpenseInput(String title, double amount, String payerName) {
    return false;
  }

  @Override
  public Expense initialiseNewExpense(String title, double amount, String payerName) {
    return null;
  }

  @Override
  public double updateMembersBalance(User user, Group group, Expense expense) {
    return 0;
  }

  @Override
  public void saveNewExpense(Expense expense) {

  }

  @Override
  public void writeExpenseToRemote(User user, Group group, Expense expense) {

  }

  @Override
  public void updateUIWithNewExpense() {

  }
}
