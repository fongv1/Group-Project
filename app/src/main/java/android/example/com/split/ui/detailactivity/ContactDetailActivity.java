package android.example.com.split.ui.detailactivity;

import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactDetailActivity extends BaseDetailActivity {

  TextView firstName;
  TextView lastName;
  TextView email;
  private User user;

  public ContactDetailActivity() {
    init(R.string.title_activity_contact, R.layout.activity_detail_contact, R.menu.activity_detail);

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Bundle bundle = getIntent().getExtras();

    if (bundle != null) {
      user = (User) bundle.get("Contact");
    }

    firstName = (TextView) findViewById(R.id.contact_detail_first_name);
    lastName = (TextView) findViewById(R.id.contact_detail_last_name);
    email = (TextView) findViewById(R.id.contact_detail_email);


    user = (User) bundle.get("Contact");
    firstName.setText(user.getFirstName());
    lastName.setText(user.getLastName());
    email.setText(user.getEmail());

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
