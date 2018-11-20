package android.example.com.split;

import android.content.Intent;
import android.example.com.split.data.entity.Group;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


// Simple implementation for a data set that consists of a List of Groups displayed using TextView widgets
public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.GroupRowViewHolder> {

    private List<android.example.com.split.data.entity.Group> mDataset;

    // Provides reference to the views for each data item
    // When create more complex group view, it should be removed in a separate java file
    class GroupRowViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Each group data item is just a String presented as a textView in this case
        public TextView mTextView;
        public TextView expenseTextView;
        final GroupsRecyclerAdapter mAdapter;

        // Initializes the ViewHolder TextView from the group_row_view XML resource
        public GroupRowViewHolder(View v, GroupsRecyclerAdapter adapter) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.groupRowView);
            expenseTextView = (TextView) v.findViewById(R.id.groupExpense);
            this.mAdapter = adapter;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mDataset.
            android.example.com.split.data.entity.Group group = mDataset.get(mPosition);
            // Show toast when clicked
            Intent intent = new Intent(v.getContext(), GroupDetailActivity.class);
            //intent.put("group name", mDataset.get(mPosition));
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
            //v.getContext().startActivity(intent);
        }
    }

    // Create the adapter with a dataset
    public GroupsRecyclerAdapter(List<Group> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GroupRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.group_row_view, parent, false);
        GroupRowViewHolder vh = new GroupRowViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GroupRowViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        android.example.com.split.data.entity.Group group = mDataset.get(position);
        holder.mTextView.setText(group.getName());
        holder.expenseTextView.setText("" + group.getExpenses().get(0).getPaymentAmount());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


