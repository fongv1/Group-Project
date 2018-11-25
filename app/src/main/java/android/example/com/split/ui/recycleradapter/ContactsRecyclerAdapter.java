package android.example.com.split.ui.recycleradapter;

import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.viewholder.ContactViewHolder;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using
// TextView widgets
public class ContactsRecyclerAdapter extends BaseRecyclerAdapter<ContactViewHolder, User> {

  // Create the adapter with a dataset
  public ContactsRecyclerAdapter(List<User> contacts) {
    super(contacts);
  }

  // Create new views (invoked by the layout manager)
  @NonNull
  @Override
  public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    View v = (View) LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_contact, parent, false);
    return getViewHolder(v);
  }

  @NonNull
  @Override
  protected ContactViewHolder getViewHolder(View v) {
    return new ContactViewHolder(v, this);
  }

  // Replace the contents of a view (invoked by the layout manager)
  @Override
  public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    holder.bind(null, getDataset().get(position), position);
  }
}


