package android.example.com.split;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_tab);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ContactsFragment())
                .commit();
    }
}
