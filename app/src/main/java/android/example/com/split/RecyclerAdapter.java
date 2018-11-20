package android.example.com.split;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using TextView widgets
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.GroupRowViewHolder> {

    private final int layout;
    private List<String> mDataset;

    // Provides reference to the views for each data item
    // When create more complex group view, it should be removed in a separate java file
    class GroupRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Each group data item is just a String presented as a textView in this case
        public TextView mTextView;
        final RecyclerAdapter mAdapter;
        // Initializes the ViewHolder TextView from the group_row_view XML resource
        public GroupRowViewHolder(View v, RecyclerAdapter adapter) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.groupRowView);
            this.mAdapter = adapter;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mDataset.
            String element = mDataset.get(mPosition);
            // Show toast when clicked
            Toast.makeText(v.getContext(), "Clicked " + element, Toast.LENGTH_SHORT).show();
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }
    }

    // Create the adapter with a dataset
    public RecyclerAdapter(List<String> myDataset, int layout) {
        mDataset = myDataset;
        this.layout = layout;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GroupRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        GroupRowViewHolder vh = new GroupRowViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GroupRowViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


