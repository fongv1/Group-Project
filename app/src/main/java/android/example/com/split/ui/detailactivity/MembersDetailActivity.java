package android.example.com.split.ui.detailactivity;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;


public class MembersDetailActivity extends BaseDetailActivity {

  private User user;
  private TextView firstName;
  private TextView lastName;
  private TextView email;
  private TextView balance;
  private TextView share;

  public MembersDetailActivity() {
    init(R.string.title_activity_member, R.layout.activity_detail_members, R.menu.activity_detail);
  }

  /**
   * The {@link ViewPager} that will host the section contents.
   */


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle bundle = getIntent().getExtras();

    if (bundle != null) {
      user = (User) bundle.get("Member");
      setTitle(user.getFirstName());
    }

    firstName = findViewById(R.id.member_detail_first_name);
    firstName.setText(user.getFirstName());

    lastName = findViewById(R.id.member_detail_last_name);
    lastName.setText(user.getLastName());

    //email = findViewById(R.id.member_detail_email);
    //email.setText(user.getEmail());

    share = findViewById(R.id.member_detail_share);
    //TODO replace the share
    share.setText("Share");

    balance = findViewById(R.id.member_detail_balance);
    //TODO replace the real value from calculator
    balance.setText("Balance");


    //balance = (TextView) findViewById()
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
