package android.example.com.split.ui.viewholder;

import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.detailactivity.GroupDetailActivity;
import android.example.com.split.ui.recycleradapter.GroupsRecyclerAdapter;
import android.view.View;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
public class GroupViewHolder extends BaseViewHolder<Group> {

  // Each group data item is just a String presented as a textView in this case
  private TextView mTextView;
  private TextView expenseTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public GroupViewHolder(View itemView, GroupsRecyclerAdapter adapter) {
    super(itemView, GroupDetailActivity.class, "Group");
  }

  @Override
  protected void findAllViews(View itemView) {
    mTextView = (TextView) itemView.findViewById(R.id.textView_group_item);
    expenseTextView = (TextView) itemView.findViewById(R.id.textView_group_item_expense_total);
  }

  @Override
  public void bind(final Group group) {
    super.bind(group);
    mTextView.setText(getItemData().getName());
    expenseTextView.setText("" + getItemData().getExpenses().get(0).getPaymentAmount());
  }

  @Override
  public void bind(User user, int position) {

  }

  @Override
  public void bind(Group group, Group expense, int position) {

  }
}
