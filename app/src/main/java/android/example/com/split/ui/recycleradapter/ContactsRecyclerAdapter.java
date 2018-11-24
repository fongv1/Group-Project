package android.example.com.split.ui.recycleradapter;

import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.viewholder.ContactViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class ContactsRecyclerAdapter extends BaseRecyclerAdapter<ContactViewHolder, User> {

  // Create the adapter with a dataset
  public ContactsRecyclerAdapter(List<User> myDataset, Context context) {
    super(myDataset, context);
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
    holder.bind(null, getDataset().get(position), position);
  }
}


