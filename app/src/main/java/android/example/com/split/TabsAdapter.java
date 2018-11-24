package android.example.com.split;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public abstract class TabsAdapter extends FragmentPagerAdapter {

  public TabsAdapter(FragmentManager fm) {
    super(fm);
  }
}
