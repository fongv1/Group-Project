package android.example.com.split.ui.home.contacts;

import android.content.Intent;
import android.example.com.split.BaseViewHolder;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.view.View;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
class ContactViewHolder extends BaseViewHolder<User> {

  // Each group data item is just a String presented as a textView in this case
  private TextView mTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public ContactViewHolder(View itemView, ContactsRecyclerAdapter adapter) {
    super(itemView);
  }

  @Override
  protected void findAllViews(View itemView) {
    mTextView = (TextView) itemView.findViewById(R.id.textView_contact_item);
  }

  @Override
  public void bind(final User user) {
    mTextView.setText(user.getFirstName());
    getItemView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ContactDetailActivity.class);
        intent.putExtra("user", user);
        v.getContext().startActivity(intent);
      }
    });
  }

  @Override
  public void bind(Group group, User expense, int position) {

  }
}
