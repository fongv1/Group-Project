package android.example.com.split.ui.detailactivity;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;

public class ExpensesDetailActivity extends BaseDetailActivity {

  private Double amount;

  public ExpensesDetailActivity() {
    init(R.string.title_activity_expense, R.layout.activity_detail_expense, R.menu.activity_detail);
  }

  /**
   * The {@link ViewPager} that will host the section contents.
   */

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      Expense expense = (Expense) bundle.get("Expense");
      amount = expense.getPaymentAmount();
      setTitle(expense.getTittle());
    }

    TextView textView = findViewById(R.id.textView_group_item_expense_total);
    String test = "" + amount;
    textView.setText(test);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
          // This activity is NOT part of this app's task, so create a new task
          // when navigating up, with a synthesized back stack.
          TaskStackBuilder.create(this)
                          // Add all of this activity's parents to the back stack
                          .addNextIntentWithParentStack(upIntent)
                          // Navigate up to the closest parent
                          .startActivities();
        } else {
          // This activity is part of this app's task, so simply
          // navigate up to the logical parent activity.
          NavUtils.navigateUpTo(this, upIntent);
        }
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
