package android.example.com.split.ui.recycleradapter;

import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.ui.viewholder.ExpenseViewHolder;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class ExpensesRecyclerAdapter extends BaseRecyclerAdapter<ExpenseViewHolder, Expense> {

  private Group group;

  // Create the adapter with a dataset
  public ExpensesRecyclerAdapter(List<Expense> expenses, Group group) {
    super(expenses);
    this.group = group;
  }

  // Create new views (invoked by the layout manager)
  @NonNull
  @Override
  public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_expense, parent, false);
    return getViewHolder(v);
  }

  @NonNull
  @Override
  protected ExpenseViewHolder getViewHolder(View v) {
    return new ExpenseViewHolder(v, this, this);
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(@NonNull ExpenseViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    holder.bind(group, getDataset().get(position), position);
  }
}


