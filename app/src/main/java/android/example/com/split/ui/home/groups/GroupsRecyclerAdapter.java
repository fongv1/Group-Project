package android.example.com.split.ui.home.groups;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.ui.home.groups.group.GroupDetailActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


// Simple implementation for a data set that consists of a List of Groups displayed using TextView widgets
public class GroupsRecyclerAdapter extends RecyclerView.Adapter<GroupsRecyclerAdapter.GroupViewHolder> {

    private List<Group> mDataset;

    // Create the adapter with a dataset
    public GroupsRecyclerAdapter(List<Group> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group, parent, false);
        GroupViewHolder vh = new GroupViewHolder(v, this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GroupViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Group group = mDataset.get(position);
        holder.mTextView.setText(group.getName());
        holder.expenseTextView.setText("" + group.getExpenses().get(0).getPaymentAmount());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (mDataset == null) {
            mDataset = new ArrayList<>();
        }
        return mDataset.size();
    }

    // Provides reference to the views for each data item
    // When create more complex group view, it should be removed in a separate java file
    class GroupViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final GroupsRecyclerAdapter mAdapter;
        // Each group data item is just a String presented as a textView in this case
        public TextView mTextView;
        public TextView expenseTextView;

        // Initializes the ViewHolder TextView from the item_group XML resource
        public GroupViewHolder(View v, GroupsRecyclerAdapter adapter) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.textView_group_item);
            expenseTextView = (TextView) v.findViewById(R.id.textView_group_item_expense_total);
            this.mAdapter = adapter;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mDataset.
            Group group = mDataset.get(mPosition);
            // Show toast when clicked
            Intent intent = new Intent(v.getContext(), GroupDetailActivity.class);
            intent.putExtra("group name", mDataset.get(mPosition));
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
            v.getContext().startActivity(intent);
        }
    }
}


