package android.example.com.split.ui.home.groups;


import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GroupsTabFragment extends Fragment {

    private List<Group> dataset;

    public GroupsTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize dataset, this data would usually come from a local content provider or remote server.
        initDataset();
    }

    // Create dummy data
    private void initDataset() {
        dataset = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            Group group = new Group();
            String groupName = "Group " + i;
            group.setName(groupName);

            for (int j = 0; j < 8; j++) {
                Expense expense = new Expense();
                expense.setPaymentAmount(rand.nextInt(1000));
                expense.setTittle("Expense " + j + " " + groupName);
                group.addExpense(expense);
            }
            for(int j = 0; j < 5; j++){
                User user = new User();
                user.setFirstName("Memeber " + j);
                user.setLastName(groupName);
                group.addMember(user);
            }
            dataset.add(group);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab_groups, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_fragment_tab_groups);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        GroupsRecyclerAdapter mAdapter = new GroupsRecyclerAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
