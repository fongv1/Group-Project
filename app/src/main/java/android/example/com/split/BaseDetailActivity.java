package android.example.com.split;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class BaseDetailActivity extends AppCompatActivity {

  private int titleStringId;

  public BaseDetailActivity(int titleStringId) {
    this.titleStringId = titleStringId;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base_detail);

    android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Get a support ActionBar corresponding to this toolbar
    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle(titleStringId);
    // Enable the Up button
    actionBar.setDisplayHomeAsUpEnabled(true);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.activity_detail, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      default:
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
  }
}
