package android.example.com.split.ui.home.groups.group.members;

import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class MembersDetailActivity extends AppCompatActivity {

    private User user;

    /**
     * The {@link ViewPager} that will host the section contents.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_members);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
           user =  (User) bundle.get("user");
        }

        TextView textView = findViewById(R.id.member_detail_name);
        textView.setText(user.getFirstName());
    }
}
