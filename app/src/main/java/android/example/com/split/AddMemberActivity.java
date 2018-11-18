package android.example.com.split;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AddMemberActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private EditText txtInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        ListView listView = (ListView) findViewById(R.id.listv);
        arrayList = new ArrayList<>();

        // ArrayAdapter(Context context, int resource, int textViewResourceId, List<T> objects)
        adapter = new ArrayAdapter<>(this, R.layout.list_member, R.id.textMember, arrayList);
        listView.setAdapter(adapter);
        txtInput = (EditText) findViewById(R.id.editTextGroupName);
        Button btAdd = (Button) findViewById(R.id.add_member_button);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMember = txtInput.getText().toString();
                if (!newMember.trim().matches("")) {
                    arrayList.add(newMember);
                    adapter.notifyDataSetChanged();
                }

            }
        });
    }

    public void back(View view){
        Intent back = new Intent(this, CreateGroupActivity.class);
        startActivity(back);

    }

    public void done(View view) {

    }

}
