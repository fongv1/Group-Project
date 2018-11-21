package android.example.com.split.ui.home.groups.group.expenses;

import android.example.com.split.R;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ExpensesDetailActivity extends AppCompatActivity {
    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_expense_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_detail_group);
        setSupportActionBar(toolbar);

    }
}
