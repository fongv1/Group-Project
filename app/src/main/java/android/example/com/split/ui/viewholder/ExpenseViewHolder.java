package android.example.com.split.ui.viewholder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.OnEditItemListener;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.data.repository.ExpensesDataRepository;
import android.example.com.split.ui.detailactivity.ExpensesDetailActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class ExpenseViewHolder extends BaseViewHolder<Expense> {

  private Group group;

  public TextView payerTextView;
  // Each group data item is just a String presented as a textView in this case
  private TextView expenseTextView;
  private TextView amountTextView;
  private ImageView editButton;
  private ImageView deleteButton;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public ExpenseViewHolder(View itemView, OnDeleteItemListener deleteListener, OnEditItemListener
      editListener, Group group) {
    super(itemView, ExpensesDetailActivity.class, "Expense", false);
    setOnDeleteItemListener(deleteListener);
    setOnEditItemListener(editListener);
    this.group = group;
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
    final EditText editAmount = (EditText) view.findViewById(
        R.id.editText_dialog_add_expense_amount);
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
        ConnectivityManager connectivityManager = (ConnectivityManager) getItemView().getContext()
                                                                                     .getSystemService(
                                                                                         Context
                                                                                             .CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
          Toast.makeText(getItemView().getContext(), "Not connected", Toast.LENGTH_SHORT).show();
        } else {
          //takes the title input from the text field
          String newTitle = editTitle.getText().toString();

          //takes the amount input from the text field
          Double newAmount = Double.parseDouble(editAmount.getText().toString());


          if (!newTitle.trim().equals("") && newAmount != 0.0) {
            // updates the title
            expense.setTittle(newTitle);

            //updates the amount
            expense.setPaymentAmount(newAmount);
            // takes the selected member from its position in the spinner
            int memberPosition = expenseSpinner.getSelectedItemPosition();
            User member = group.getUserMembers().get(memberPosition);
            expense.setUser(member);

            Toast.makeText(v.getContext(), "Saved!", Toast.LENGTH_SHORT).show();
            // Notifies tha adapter that the item at that position is changed
            editItem(position);
          } else {
            Toast.makeText(v.getContext(), "Not saved!", Toast.LENGTH_SHORT).show();

          }
        }

        dialog.dismiss();
      }
    });

    dialog.show();
  }

  private void deleteExpensePopupDialog(final Expense expense, final int position) {
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
        deleteExpense(group, expense.getId(), position);
      }
    });

    //Set other dialog properties
    builder.setMessage("Delete expense?");

    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();

  }

  public void deleteExpense(final Group group, String expenseId, final int position) {
    ConnectivityManager connectivityManager = (ConnectivityManager) getItemView().getContext()
                                                                                 .getSystemService(
                                                                                     Context
                                                                                         .CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      String groupId = group.getGroupId();
      ExpensesDataRepository expensesDataRepository = new ExpensesDataRepository();
      expensesDataRepository.removeExpense(groupId, expenseId, new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
          if (msg.getData().getBoolean(ExpensesDataRepository.SUCCESS)) {
            Toast.makeText(getContext(), "Expense deleted", Toast.LENGTH_SHORT).show();
            group.getExpenses().remove(position);
            deleteItem(position);
            Intent intent = new Intent("update-balance");
            getActivity().sendBroadcast(intent);

          } else {
            Toast.makeText(getContext(), "Failed to delete expense", Toast.LENGTH_SHORT)
                 .show();
          }
          return false;
        }
      });
    }
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
    payerTextView.setText(getItemData().getPayerName());
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
        deleteExpensePopupDialog(expense, position);
      }
    });
  }

  private Activity getActivity() {
    Context context = getContext();
    while (context instanceof ContextWrapper) {
      if (context instanceof Activity) {
        return (Activity)context;
      }
      context = ((ContextWrapper)context).getBaseContext();
    }
    return null;
  }
}
