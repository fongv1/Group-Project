package android.example.com.split.ui.home.groups.group.expenses;

import android.app.Activity;
import android.example.com.split.R;
import android.example.com.split.ui.home.HomeActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ExpensesDetailActivity extends AppCompatActivity {

    private Double amount;
    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_expense_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            amount = (Double) bundle.get("Expense");
        }

        TextView textView = findViewById(R.id.textView_group_item_expense_total);
        String test = "" + amount;
        textView.setText(test);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_detail_group);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_activity_detail_expense);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View v) {
                Toast.makeText(ExpensesDetailActivity.this, "Edit expense", Toast.LENGTH_LONG).show();
            }
        });
    }
}
