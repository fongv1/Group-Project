package android.example.com.split.ui.detailactivity;

import android.example.com.split.R;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BaseDetailActivity extends AppCompatActivity {

  private int optionsMenuId;
  private int titleStringId;
  private int activityLayoutId;
  private ActionBar actionBar;

  public BaseDetailActivity() {
  }

  protected void init(int titleStringId, int activityLayoutId, int optionsMenuId) {
    this.titleStringId = titleStringId;
    this.activityLayoutId = activityLayoutId;
    this.optionsMenuId = optionsMenuId;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(activityLayoutId);

    android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Get a support ActionBar corresponding to this toolbar
    actionBar = getSupportActionBar();
    actionBar.setTitle(titleStringId);
    // Enable the Up button
    actionBar.setDisplayHomeAsUpEnabled(true);
    setTitle("My Group");
  }

  protected void setTitle(String title) {
    actionBar.setTitle(title);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(optionsMenuId, menu);
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
