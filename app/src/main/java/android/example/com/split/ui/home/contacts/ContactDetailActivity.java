package android.example.com.split.ui.home.contacts;

import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ContactDetailActivity extends AppCompatActivity {

    private User user;
    TextView firstName;
    TextView lastName;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_contact);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            user = (User) bundle.get("user");
        }

        firstName = (TextView) findViewById(R.id.contact_detail_first_name);
        lastName = (TextView) findViewById(R.id.contact_detail_last_name);
        email = (TextView) findViewById(R.id.contact_detail_email);


        user = (User) bundle.get("user");
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        email.setText(user.getEmail());

    }
}
