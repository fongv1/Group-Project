package android.example.com.split.ui.home.groups;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.ui.home.groups.group.GroupDetailActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
class GroupViewHolder extends RecyclerView.ViewHolder {

  // Each group data item is just a String presented as a textView in this case
  private final TextView mTextView;
  private final TextView expenseTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public GroupViewHolder(View itemView, GroupsRecyclerAdapter adapter) {
    super(itemView);
    mTextView = (TextView) itemView.findViewById(R.id.textView_group_item);
    expenseTextView = (TextView) itemView.findViewById(R.id.textView_group_item_expense_total);
  }

  public void bind(final Group group) {
    mTextView.setText(group.getName());
    expenseTextView.setText("" + group.getExpenses().get(0).getPaymentAmount());
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), GroupDetailActivity.class);
        intent.putExtra("selected_group", group);
        view.getContext().startActivity(intent);
      }
    });
  }
}
