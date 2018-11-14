package android.example.com.split;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GroupsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_tab);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new GroupsFragment())
                .commit();
    }
}
