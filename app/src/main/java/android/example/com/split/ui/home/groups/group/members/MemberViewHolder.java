package android.example.com.split.ui.home.groups.group.members;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

// Provides reference to the views for each data item
// When create more complex group view, it should be removed in a separate java file
class MemberViewHolder extends RecyclerView.ViewHolder {

  // Each group data item is just a String presented as a textView in this case
  public TextView mTextView;

  // Initializes the ViewHolder TextView from the item_group XML resource
  public MemberViewHolder(View itemView, MembersRecyclerAdapter adapter) {
    super(itemView);
    mTextView = (TextView) itemView.findViewById(R.id.textView_item_group_member);
  }

  public void bind(final User user) {
    mTextView.setText(user.getFirstName() + " " + user.getLastName());
    itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MembersDetailActivity.class);
        intent.putExtra("user", user);
        v.getContext().startActivity(intent);
      }
    });
  }
}
