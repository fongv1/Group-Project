package android.example.com.split.ui.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.detailactivity.GroupDetailActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class GroupViewHolder extends BaseViewHolder<Group> {

  // Each group data item is just a String presented as a textView in this case
  private TextView mTextView;
  private TextView expenseTextView;
  public ImageView deleteButton;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public GroupViewHolder(View itemView, OnDeleteItemListener listener) {
    super(itemView, GroupDetailActivity.class, "Group", true);
    setOnDeleteItemListener(listener);
  }

  private void deleteGroupPopupDialog(final int position) {
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
        // notifyItemRemoved is done in onDelete method in BaseRecyclerAdapter
        deleteItem(position);
      }
    });

    //Set other dialog properties
    builder.setMessage("Delete group?");

    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();

  }

  @Override
  protected void findAllViews(View itemView) {
    mTextView = (TextView) itemView.findViewById(R.id.textView_group_item);
    expenseTextView = (TextView) itemView.findViewById(R.id.textView_group_item_expense_total);
    deleteButton = (ImageView) itemView.findViewById(R.id.imageView_delete_group_item);

  }

  @Override
  public void bind(final Group group) {
    super.bind(group);
  }

  @Override
  public void bind(User user, final int position) {

  }

  @Override
  public void bind(Group group, Group expense, final int position) {
    super.bind(group);
    mTextView.setText(getItemData().getName());
    if(group.getExpenses().size()>0){
      expenseTextView.setText("" + getItemData().getExpenses().get(0).getPaymentAmount());
    }
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteGroupPopupDialog(position);
      }
    });
  }
}
