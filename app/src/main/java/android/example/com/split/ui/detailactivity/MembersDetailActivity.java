package android.example.com.split.ui.detailactivity;

import android.example.com.split.Calculator;
import android.example.com.split.R;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.widget.TextView;


public class MembersDetailActivity extends BaseDetailActivity {

  private User user;
  private Group group;
  private TextView firstName;
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
      group = (Group) bundle.get("Group");
      setTitle(user.getFirstName());
    }

    firstName = findViewById(R.id.member_detail_first_name);
    firstName.setText(user.getFirstName() + " " + user.getLastName());

    //email = findViewById(R.id.member_detail_email);
    //email.setText(user.getEmail());

    share = findViewById(R.id.member_detail_share);
    String shareBalance = String.format("%.1f", Calculator.getExpensesPerMember(group));
    share.setText("Share: " +  shareBalance + " SEK");

    balance = findViewById(R.id.member_detail_balance);
    String memberBalance = String.format("%.1f", Calculator.getMemberBalance(group, user));
    balance.setText("Balance: " + memberBalance + " SEK");


    //balance = (TextView) findViewById()
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
