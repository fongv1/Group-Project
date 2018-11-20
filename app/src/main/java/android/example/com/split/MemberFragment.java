package android.example.com.split;

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

public class MemberFragment extends Fragment {

    private static final String TAG = "MemberFragment";
    private List<String> dataset;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDataset();

        View rootView = inflater.inflate(R.layout.fragment_member, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.memberRecycler);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        RecyclerAdapter mAdapter = new RecyclerAdapter(dataset, R.layout.contact_row_view);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // Create dummy data
    private void initDataset() {
        dataset = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            dataset.add("Member " + i);
        }
    }
}
