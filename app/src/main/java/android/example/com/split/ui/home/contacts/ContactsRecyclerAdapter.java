package android.example.com.split.ui.home.contacts;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter
    .ContactViewHolder> {

  private List<User> mDataset;

  // Create the adapter with a dataset
  public ContactsRecyclerAdapter(List<User> myDataset) {
    mDataset = myDataset;
  }

  public List<User> getDataset() {
    return mDataset;
  }

  // Create new views (invoked by the layout manager)
  @Override
  public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_contact, parent, false);
    ContactViewHolder vh = new ContactViewHolder(v, this);
    return vh;
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(ContactViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    holder.bind(mDataset.get(position));
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return mDataset.size();
  }

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
}


