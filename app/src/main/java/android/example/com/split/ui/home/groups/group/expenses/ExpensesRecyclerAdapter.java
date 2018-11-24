package android.example.com.split.ui.home.groups.group.expenses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using TextView widgets
public class ExpensesRecyclerAdapter extends RecyclerView.Adapter<ExpensesRecyclerAdapter.ExpenseViewHolder> {

    private Context context;
    private List<Expense> mDataset;
    private Group group;

    // Create the adapter with a dataset
    public ExpensesRecyclerAdapter(Context context, List<Expense> myDataset, Group group) {
        this.context = context;
        mDataset = myDataset;
        this.group = group;
    }

    public List<Expense> getDataset() {
        return mDataset;
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
        public ImageView editButton;
        public ImageView deleteButton;

        // Initializes the ViewHolder TextView from the item_group XML resource
        public ExpenseViewHolder(View v, ExpensesRecyclerAdapter adapter) {
            super(v);
            expenseTextView = (TextView) v.findViewById(R.id.textView_expense_item);
            amountTextView = (TextView) v.findViewById(R.id.textView_amount_item);
            editButton = (ImageView) v.findViewById(R.id.imageView_edit_expense_item);
            deleteButton = (ImageView) v.findViewById(R.id.imageView_delete_expense_item);
            this.mAdapter = adapter;
            v.setOnClickListener(this);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            final int mPosition = getLayoutPosition();
            // Use that to access the affected item in mDataset.
            final Expense expense = mDataset.get(mPosition);
            // Checks which button is clicked
            if (v.getId() == R.id.imageView_edit_expense_item) {
                editExpensePopupDialog(expense, mPosition);
            }
            else if (v.getId() == R.id.imageView_delete_expense_item) {
                deleteExpensePopupDialog(mPosition);
            }
            //Intent intent = new Intent(v.getContext(), ExpensesDetailActivity.class);
            //intent.putExtra("Expense", expense.getPaymentAmount());
            // v.getContext().startActivity(intent);
        }

        private void editExpensePopupDialog(final Expense expense, final int position) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mAdapter.context);
            final View view = LayoutInflater.from(mAdapter.context).inflate(R.layout.dialog_add_expense, null);

            dialogBuilder.setView(view);
            final AlertDialog dialog = dialogBuilder.create();
            //takes the already existing title and sets it to the text field
            final EditText editTitle = (EditText) view.findViewById(R.id.editText_dialog_add_expense_title);
            editTitle.setText(expense.getTittle());
            //takes the already existing amount and sets it to the amount field
            final EditText editAmount = (EditText) view.findViewById(R.id.editText_dialog_add_expense_amount);
            editAmount.setText("" + expense.getPaymentAmount());

            // set the spinner with all the members of this group
            final Spinner expenseSpinner = (Spinner) view.findViewById(R.id.spinner_choose_member);
            ArrayAdapter<User> adapter = new ArrayAdapter<User>(view.getContext(), android.R.layout.simple_spinner_item, group.getUserMembers());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            expenseSpinner.setAdapter(adapter);
            // select the correct member in the spinner
            // get the position in the member list of the user responsible for this expense
            // the group contains the member list and the expense contains the user
            int expenseMemberPosition = group.getMembers().indexOf(expense.getUser());
            expenseSpinner.setSelection(expenseMemberPosition);

            Button saveButton = (Button) view.findViewById(R.id.button_dialog_add_expense_save);
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //takes the title input from the text field
                    String newTitle = editTitle.getText().toString();
                    // updates the title
                    expense.setTittle(newTitle);
                    //takes the amount input from the text field
                    Double newAmount = Double.parseDouble(editAmount.getText().toString());
                    //updates the amount
                    expense.setPaymentAmount(newAmount);
                    // takes the selected member from its position in the spinner
                    int memberPosition = expenseSpinner.getSelectedItemPosition();
                    User member = group.getUserMembers().get(memberPosition);
                    expense.setUser(member);

                    Toast.makeText(v.getContext(), "Saved!", Toast.LENGTH_SHORT).show();
                    // Notifies tha adapter that the item at that position is changed
                    notifyItemChanged(position);
                    dialog.dismiss();
                }
            });

            dialog.show();
        }

        private void deleteExpensePopupDialog(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mAdapter.context);
            // Add the buttons
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    mDataset.remove(position);
                    notifyItemRemoved(position);
                }
            });

            //Set other dialog properties
            builder.setMessage("Delete expense?");

            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }
    }
}


