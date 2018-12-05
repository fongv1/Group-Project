package android.example.com.split.ui.viewholder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.detailactivity.MembersDetailActivity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class MemberViewHolder extends BaseViewHolder<User> {
  private Group group;

  public ImageView deleteButton;
  // Each group data item is just a String presented as a textView in this case
  private TextView itemTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public MemberViewHolder(View itemView, OnDeleteItemListener onDeleteListener, Group group) {

    super(itemView, MembersDetailActivity.class, "Member", true);
    setOnDeleteItemListener(onDeleteListener);
    this.group = group;
  }

  private void deleteMemberDialog(final User user, final int position) {
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

      deleteMember(group, user.getId(), position );
      }
    });

    //Set other dialog properties
    builder.setMessage("Delete member?");

    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();

  }

  public void deleteMember(Group group, String memberId, final int position) {
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
      db.collection("groups").document(groupId).update("members", FieldValue.arrayRemove(memberId)).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
          Toast.makeText(getContext(), "Member deleted", Toast.LENGTH_SHORT).show();
          deleteItem(position);
        }
      }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          Toast.makeText(getContext(), "Failed to delete member", Toast.LENGTH_SHORT).show();
        }
      });
    }
  }

  @Override
  protected void findAllViews(View itemView) {
    itemTextView = (TextView) itemView.findViewById(R.id.textView_item_group_member);
    deleteButton = (ImageView) itemView.findViewById(R.id.imageView_delete_member_item);
  }

  @Override
  public void bind(final User user) {
    super.bind(user);
  }

  @Override
  public void bind(final User user, final int position) {
    super.bind(user);
    //mTextView.setText(getItemData().getFirstName() + " " + getItemData().getLastName());
    itemTextView.setText(user.getFirstName() + "");
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteMemberDialog(user, position);
      }
    });
  }

  @Override
  public void bind(Group group, User expense, int position) {

  }
}
