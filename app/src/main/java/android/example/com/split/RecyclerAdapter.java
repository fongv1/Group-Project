package android.example.com.split;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


// Simple implementation for a data set that consists of a List of Strings displayed using TextView widgets
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.GroupRowViewHolder> {
   private List<String> mDataset;

    // Provides reference to the views for each data item
    // When create more complex group view, it should be removed in a separate java file
    public static class GroupRowViewHolder extends RecyclerView.ViewHolder {

        // each group data item is just a String presented as a textView in this case
        public TextView mTextView;

        public GroupRowViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.groupRowView);
        }
    }

    // Create the adapter with a dataset
    public RecyclerAdapter(List<String> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GroupRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_row_view, parent, false);

        GroupRowViewHolder vh = new GroupRowViewHolder(v);
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


