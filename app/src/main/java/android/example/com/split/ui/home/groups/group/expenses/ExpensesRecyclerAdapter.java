package android.example.com.split.ui.home.groups.group.expenses;

import android.app.AlertDialog;
import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using TextView widgets
public class ExpensesRecyclerAdapter extends RecyclerView.Adapter<ExpensesRecyclerAdapter.ExpenseViewHolder> {

    private Context context;
    private List<Expense> mDataset;

    // Create the adapter with a dataset
    public ExpensesRecyclerAdapter(Context context, List<Expense> myDataset) {
        this.context = context;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        ExpenseViewHolder vh = new ExpenseViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Expense expense = mDataset.get(position);
        holder.expenseTextView.setText(expense.getTittle());
        holder.amountTextView.setText("" + expense.getPaymentAmount());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provides reference to the views for each data item
    // When create more complex group view, it should be removed in a separate java file
    class ExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ExpensesRecyclerAdapter mAdapter;
        // Each group data item is just a String presented as a textView in this case
        public TextView expenseTextView;
        public TextView amountTextView;

        // Initializes the ViewHolder TextView from the item_group XML resource
        public ExpenseViewHolder(View v, ExpensesRecyclerAdapter adapter) {
            super(v);
            expenseTextView = (TextView) v.findViewById(R.id.textView_expense_item);
            amountTextView = (TextView) v.findViewById(R.id.textView_amount_item);
            this.mAdapter = adapter;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mDataset.
            final Expense expense = mDataset.get(mPosition);

            //Intent intent = new Intent(v.getContext(), ExpensesDetailActivity.class);
            //intent.putExtra("Expense", expense.getPaymentAmount());

            ImageView editImageView = (ImageView) v.findViewById(R.id.imageView_edit_expense_item);
            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editExpensePopupDialog(expense);
                }
            });
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();

            // v.getContext().startActivity(intent);
        }

        private void editExpensePopupDialog(Expense expense) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mAdapter.context);
            View view = LayoutInflater.from(mAdapter.context).inflate(R.layout.dialog_add_expense, null);
            EditText editTitle = (EditText) view.findViewById(R.id.editText_dialog_add_expense_title);
            editTitle.setText(expense.getTittle());
            EditText editAmount = (EditText) view.findViewById(R.id.editText_dialog_add_expense_amount);
            editAmount.setText("" + expense.getPaymentAmount());
            dialogBuilder.setView(view);
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
    }
}


