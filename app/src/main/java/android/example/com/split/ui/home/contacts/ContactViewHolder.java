package android.example.com.split.ui.home.contacts;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
class ContactViewHolder extends RecyclerView.ViewHolder {

  // Each group data item is just a String presented as a textView in this case
  private final TextView mTextView;
  private final View itemView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public ContactViewHolder(View itemView, ContactsRecyclerAdapter adapter) {
    super(itemView);
    this.itemView = itemView;
    mTextView = (TextView) itemView.findViewById(R.id.textView_contact_item);
  }

  public void bind(final User user) {
    mTextView.setText(user.getFirstName());
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ContactDetailActivity.class);
        intent.putExtra("user", user);
        v.getContext().startActivity(intent);
      }
    });
  }
}
