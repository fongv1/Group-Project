package android.example.com.split.ui.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.detailactivity.ExpensesDetailActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class ExpenseViewHolder extends BaseViewHolder<Expense> {

  // Each group data item is just a String presented as a textView in this case
  private TextView expenseTextView;
  private TextView amountTextView;
  public TextView payerTextView;
  private ImageView editButton;
  private ImageView deleteButton;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public ExpenseViewHolder(View itemView, OnDeleteItemListener listener) {
    super(itemView, ExpensesDetailActivity.class, "Expense");
    setOnDeleteItemListener(listener);
  }

  private void editExpensePopupDialog(final Group group, final Expense expense, final int
      position) {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getItemView().getContext());
    final View view = LayoutInflater.from(getItemView().getContext())
                                    .inflate(R.layout.dialog_add_expense, null);

    dialogBuilder.setView(view);
    final AlertDialog dialog = dialogBuilder.create();
    //takes the already existing title and sets it to the text field
    final EditText editTitle = (EditText) view.findViewById(R.id.editText_dialog_add_expense_title);
    editTitle.setText(expense.getTittle());
    //takes the already existing amount and sets it to the amount field
    final EditText editAmount = (EditText) view
        .findViewById(R.id.editText_dialog_add_expense_amount);
    editAmount.setText("" + expense.getPaymentAmount());

    // set the spinner with all the members of this group
    final Spinner expenseSpinner = (Spinner) view.findViewById(R.id.spinner_choose_member);
    ArrayAdapter<User> adapter = new ArrayAdapter<User>(view.getContext(),
                                                        android.R.layout.simple_spinner_item,
                                                        group.getUserMembers());
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
        dialog.dismiss();
      }
    });

    dialog.show();
  }

  private void deleteExpensePopupDialog(final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getItemView().getContext());
    // Add the buttons
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        // User cancelled the dialog
      }
    });

    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        // User clicked OK button
        deleteItem(position);
      }
    });

    //Set other dialog properties
    builder.setMessage("Delete expense?");

    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();

  }

  @Override
  protected void findAllViews(View itemView) {
    expenseTextView = (TextView) itemView.findViewById(R.id.textView_expense_item);
    amountTextView = (TextView) itemView.findViewById(R.id.textView_amount_item);
    payerTextView = (TextView) itemView.findViewById(R.id.textView_payer_item);
    editButton = (ImageView) itemView.findViewById(R.id.imageView_edit_expense_item);
    deleteButton = (ImageView) itemView.findViewById(R.id.imageView_delete_expense_item);
  }

  @Override
  public void bind(Expense expense) {
    super.bind(expense);
  }

  @Override
  public void bind(User user, int position) {
  }

  @Override
  public void bind(final Group group, final Expense expense, final int position) {
    super.bind(expense);
    expenseTextView.setText(getItemData().getTittle());
    amountTextView.setText("" + getItemData().getPaymentAmount());
    payerTextView.setText(getItemData().getPayeeName());
    //payerTextView.setText(getItemData().getUser().getFirstName());
    editButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        editExpensePopupDialog(group, getItemData(), position);
      }
    });
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteExpensePopupDialog(position);
      }
    });
  }
}
