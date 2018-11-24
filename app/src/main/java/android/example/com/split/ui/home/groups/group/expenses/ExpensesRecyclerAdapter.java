package android.example.com.split.ui.home.groups.group.expenses;

import android.content.Context;
import android.example.com.split.BaseRecyclerAdapter;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class ExpensesRecyclerAdapter extends BaseRecyclerAdapter<ExpenseViewHolder, Expense> {

  private Group group;

  // Create the adapter with a dataset
  public ExpensesRecyclerAdapter(Context context, List<Expense> myDataset, Group group) {
    super(myDataset, context);
    this.group = group;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_expense, parent, false);
    ExpenseViewHolder vh = new ExpenseViewHolder(this, v, this);
    return vh;
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(ExpenseViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    holder.bind(group, getDataset().get(position), position);
  }
}


