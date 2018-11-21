package android.example.com.split.ui.home.groups.group.expenses;

import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
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
import java.util.Random;

public class ExpensesTabFragment extends Fragment {

    private static final String TAG = "ExpensesTabFragment";
    private List<Expense> dataset;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDataset();

        View rootView = inflater.inflate(R.layout.fragment_tab_expenses, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_fragment_tab_expenses);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ExpensesRecyclerAdapter mAdapter = new ExpensesRecyclerAdapter(dataset);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // Create dummy data
    private void initDataset() {
        dataset = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            Expense expense = new Expense();
            expense.setTittle("Expense " + i);
            expense.setPaymentAmount(rand.nextInt(1000));
            dataset.add(expense);
        }
    }
}
