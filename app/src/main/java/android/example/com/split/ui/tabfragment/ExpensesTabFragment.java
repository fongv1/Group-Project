package android.example.com.split.ui.tabfragment;

import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.data.repository.ExpensesDataRepository;
import android.example.com.split.ui.recycleradapter.ExpensesRecyclerAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class ExpensesTabFragment extends BaseTabFragment<ExpensesRecyclerAdapter, Expense>
    implements ExpensesActions {

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
    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      setupRecyclerView(rootView, R.id.recyclerView_fragment_tab_expenses);
    }
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
    //fetchExpensesListFromRemote(group);
  }

  @Override
  public void addExpense(Group group, Expense expense) {

  }

  @Override
  public Expense getExpenseDetailFromUI(String title, double amount, String payerName) {

    return null;
  }

  @Override
  public void populateSpinnerWithMembers(Context context, List<User> users) {

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
  public List<Double> updateMembersBalance(User user, Group group, Expense expense) {
    return null;
  }

  @Override
  public void saveNewExpense(Expense expense) {

  }

  @Override
  public void writeExpenseToRemote(Group group, Expense expense) {
    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      String groupId = group.getGroupId();
      ExpensesDataRepository expensesDataRepository = new ExpensesDataRepository();
      expensesDataRepository.addExpenses(groupId, expense, new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

          if (msg.getData().getBoolean(ExpensesDataRepository.SUCCESS)) {
            Toast.makeText(getContext(), "Expense saved in remote", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getContext(), "Failed to save expense in remote", Toast.LENGTH_SHORT)
                 .show();
          }
          return false;
        }
      });
    }
  }

  @Override
  public void updateUIWithNewExpense() {

  }

  @Override
  public void fetchExpensesListFromRemote(Group group) {
    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      String groupId = group.getGroupId();
      ExpensesDataRepository expensesDataRepository = new ExpensesDataRepository();
      expensesDataRepository.getExpensesList(groupId, new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
          if (msg.getData().getBoolean(ExpensesDataRepository.SUCCESS, false)) {
            List<Expense> expenseList = (List<Expense>) msg.getData()
                                                           .getSerializable(
                                                               ExpensesDataRepository.EXPENSE_LIST);
            // are we adding here to local memory
            setData(expenseList);
            setupRecyclerView(getView(), R.id.recyclerView_fragment_tab_expenses);
          }
          return false;
        }
      });
    }
  }

  @Override
  public void fetchSingleExpenseFromRemote(Group group, Expense expense) {
    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      String groupId = group.getGroupId();
      String expenseId = expense.getId();
      ExpensesDataRepository expensesDataRepository = new ExpensesDataRepository();
      expensesDataRepository.fetchExpense(groupId, expenseId, new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
          if (msg.getData().getBoolean(ExpensesDataRepository.SUCCESS, false)) {
            Expense newExpense = (Expense) msg.getData()
                                              .getSerializable(ExpensesDataRepository.EXPENSE);
          }
          return false;
        }
      });
    }
  }

  @Override
  public void removeExpenseFromRemote(Group group, Expense expense) {
    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(
        Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      String groupId = group.getGroupId();
      String expenseId = expense.getId();
      ExpensesDataRepository expensesDataRepository = new ExpensesDataRepository();
      expensesDataRepository.removeExpense(groupId, expenseId, new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
          if (msg.getData().getBoolean(ExpensesDataRepository.SUCCESS)) {
            Toast.makeText(getContext(), "Expense saved in remote", Toast.LENGTH_SHORT).show();
          } else {
            Toast.makeText(getContext(), "Failed to save expense in remote", Toast.LENGTH_SHORT)
                 .show();
          }
          return false;
        }
      });
    }
  }


}
