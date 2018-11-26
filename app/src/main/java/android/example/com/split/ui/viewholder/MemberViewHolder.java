package android.example.com.split.ui.viewholder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.example.com.split.OnDeleteItemListener;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.detailactivity.MembersDetailActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class MemberViewHolder extends BaseViewHolder<User> {

  public ImageView deleteButton;
  // Each group data item is just a String presented as a textView in this case
  private TextView itemTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public MemberViewHolder(View itemView, OnDeleteItemListener onDeleteListener) {

    super(itemView, MembersDetailActivity.class, "Member", true);
    setOnDeleteItemListener(onDeleteListener);
  }

  private void deleteMemberDialog(final int position) {
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
    builder.setMessage("Delete member?");

    // Create the AlertDialog
    AlertDialog dialog = builder.create();
    dialog.show();

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
  public void bind(User user, final int position) {
    super.bind(user);
    //mTextView.setText(getItemData().getFirstName() + " " + getItemData().getLastName());
    itemTextView.setText(user.getFirstName() + "");
    deleteButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        deleteMemberDialog(position);
      }
    });
  }

  @Override
  public void bind(Group group, User expense, int position) {

  }
}
