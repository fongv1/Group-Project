package android.example.com.split.ui.home.contacts;

import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using TextView widgets
public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactRowViewHolder> {

    private List<User> mDataset;

    // Create the adapter with a dataset
    public ContactsRecyclerAdapter(List<User> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        ContactRowViewHolder vh = new ContactRowViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ContactRowViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        User user = mDataset.get(position);
        holder.mTextView.setText(user.getFirstName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // Provides reference to the views for each data item
    // When create more complex group view, it should be removed in a separate java file
    class ContactRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ContactsRecyclerAdapter mAdapter;
        // Each group data item is just a String presented as a textView in this case
        public TextView mTextView;

        // Initializes the ViewHolder TextView from the item_group XML resource
        public ContactRowViewHolder(View v, ContactsRecyclerAdapter adapter) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.contactRowView);
            this.mAdapter = adapter;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mDataset.
            User user = mDataset.get(mPosition);
            // Show toast when clicked
            Toast.makeText(v.getContext(), "Clicked " + user.getFirstName(), Toast.LENGTH_SHORT).show();
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }
    }
}


