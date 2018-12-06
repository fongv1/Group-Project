package android.example.com.split.ui.viewholder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.example.com.split.Calculator;
import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.detailactivity.GroupDetailActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class GroupViewHolder extends BaseViewHolder<Group> {

  public ImageView deleteButton;
  // Each group data item is just a String presented as a textView in this case
  private TextView mTextView;
  private TextView expenseTextView;
  private Group group;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public GroupViewHolder(View itemView, OnDeleteItemListener listener) {
    super(itemView, GroupDetailActivity.class, "Group", true);
    setOnDeleteItemListener(listener);

  }

  private void deleteGroupPopupDialog(final Group group , final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getItemView().getContext());
    // Add the buttons
    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        // User cancelled the dialog
      }
    });

    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getItemView().getContext()
                                                                                     .getSystemService(
                                                                                         Context
                                                                                             .CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
          Toast.makeText(getItemView().getContext(), "Not connected", Toast.LENGTH_SHORT).show();
        } else {
          // User clicked OK button
          // notifyItemRemoved is done in onDelete method in BaseRecyclerAdapter
          deleteGroup(group,position);

        }
      }
    });

    //Set other dialog properties
    builder.setMessage("Delete group " + group.getName() + "?");

    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();

  }

  public void deleteGroup(Group group, final int position) {
    ConnectivityManager connectivityManager = (ConnectivityManager) getItemView().getContext()
                                                                                 .getSystemService(
                                                                                     Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

    if (!isConnected) {
      Toast.makeText(getContext(), "Not connected", Toast.LENGTH_SHORT).show();
    } else {
      String groupId = group.getGroupId();
      FirebaseFirestore db = FirebaseFirestore.getInstance();
      db.collection("groups").
          document(groupId).delete().
            addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
          Toast.makeText(getContext(), "Group deleted", Toast.LENGTH_SHORT).show();
          deleteItem(position);
        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(getContext(), "Failed to delete group", Toast.LENGTH_SHORT).show();
        }
      });
    }
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
  public void bind(final Group group, Group expense, final int position) {
    super.bind(group);
    mTextView.setText(getItemData().getName());
    expenseTextView.setText("" + Calculator.getTotalGroupExpenses(group));
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteGroupPopupDialog(group,position);
      }
    });
  }
}
