package android.example.com.split.ui.home.contacts;

import android.example.com.split.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ContactDetailActivity extends AppCompatActivity {

    TextView firstName;
    TextView lastName;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        firstName = (TextView) findViewById(R.id.contact_firstName);
        lastName = (TextView) findViewById(R.id.contact_lastName);
        email = (TextView) findViewById(R.id.contact_email);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            firstName.setText(bundle.getString("first name"));
            lastName.setText(bundle.getString("last name"));
            email.setText(bundle.getString("email"));
        }
        

    }
}
