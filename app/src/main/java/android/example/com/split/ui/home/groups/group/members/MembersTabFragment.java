package android.example.com.split.ui.home.groups.group.members;

import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class MembersTabFragment extends Fragment {

    private static final String TAG = "MembersTabFragment";
    private List<User> dataset;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDataset();

        View rootView = inflater.inflate(R.layout.fragment_tab_members, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_fragment_tab_members);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        MembersRecyclerAdapter mAdapter = new MembersRecyclerAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // Create dummy data
    private void initDataset() {
        dataset = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = new User();
            user.setFirstName("Member " + i);
            dataset.add(user);
        }
    }
}
