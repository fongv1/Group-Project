package android.example.com.split;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }

    public void createGroup(View view){
        Intent createGroup = new Intent(this, AddMemberActivity.class);
        startActivity(createGroup);
    }

    public void back(View view){
        Intent back = new Intent(this, HomeActivity.class);
        startActivity(back);

    }
}
