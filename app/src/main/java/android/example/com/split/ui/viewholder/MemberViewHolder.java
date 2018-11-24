package android.example.com.split.ui.viewholder;

import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.detailactivity.MembersDetailActivity;
import android.view.View;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class MemberViewHolder extends BaseViewHolder<User> {

  // Each group data item is just a String presented as a textView in this case
  private TextView mTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public MemberViewHolder(View itemView) {
    super(itemView, MembersDetailActivity.class, "Member");
  }

  @Override
  protected void findAllViews(View itemView) {
    mTextView = (TextView) itemView.findViewById(R.id.textView_item_group_member);
  }

  @Override
  public void bind(final User user) {
    super.bind(user);
    //mTextView.setText(getItemData().getFirstName() + " " + getItemData().getLastName());
    mTextView.setText(user.getFirstName() + "");

  }

  @Override
  public void bind(User user, int position) {

  }

  @Override
  public void bind(Group group, User expense, int position) {

  }
}
