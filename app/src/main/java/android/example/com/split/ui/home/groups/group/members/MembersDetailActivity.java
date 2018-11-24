package android.example.com.split.ui.home.groups.group.members;

import android.example.com.split.DetailActivity;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TextView;


public class MembersDetailActivity extends DetailActivity {

  private User user;
  private TextView firstName;
  private TextView lastName;
  private TextView email;
  private TextView balance;

  /**
   * The {@link ViewPager} that will host the section contents.
   */


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_members);


    Bundle bundle = getIntent().getExtras();

    if (bundle != null) {
      user = (User) bundle.get("user");
    }

    firstName = findViewById(R.id.member_detail_first_name);
    firstName.setText(user.getFirstName());

    lastName = findViewById(R.id.member_detail_last_name);
    lastName.setText(user.getLastName());

    email = findViewById(R.id.member_detail_email);
    email.setText(user.getEmail());

    //balance = (TextView) findViewById()
  }
}
