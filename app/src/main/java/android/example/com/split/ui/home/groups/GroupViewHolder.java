package android.example.com.split.ui.home.groups;

import android.content.Intent;
import android.example.com.split.BaseViewHolder;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.ui.home.groups.group.GroupDetailActivity;
import android.view.View;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
class GroupViewHolder extends BaseViewHolder<Group> {

  // Each group data item is just a String presented as a textView in this case
  private TextView mTextView;
  private TextView expenseTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public GroupViewHolder(View itemView, GroupsRecyclerAdapter adapter) {
    super(itemView);
  }

  @Override
  protected void findAllViews(View itemView) {
    mTextView = (TextView) itemView.findViewById(R.id.textView_group_item);
    expenseTextView = (TextView) itemView.findViewById(R.id.textView_group_item_expense_total);
  }

  @Override
  public void bind(final Group group) {
    mTextView.setText(group.getName());
    expenseTextView.setText("" + group.getExpenses().get(0).getPaymentAmount());
    getItemView().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), GroupDetailActivity.class);
        intent.putExtra("selected_group", group);
        view.getContext().startActivity(intent);
      }
    });
  }

  @Override
  public void bind(Group group, Group expense, int position) {

  }
}
