package android.example.com.split.ui.tabsadapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class GroupTabsAdapter extends TabsAdapter {

  private final List<Fragment> mFragmentList = new ArrayList<>();
  private final List<String> mTitleList = new ArrayList<>();

  public GroupTabsAdapter(FragmentManager fm) {
    super(fm);
  }

  public void addFragment(Fragment fragment, String title) {
    mFragmentList.add(fragment);
    mTitleList.add(title);
  }

  @Override
  public Fragment getItem(int position) {
    // getItem is called to instantiate the fragment for the given page.
    // Return a PlaceholderFragment (defined as a static inner class below).
    return mFragmentList.get(position);
  }


  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return mTitleList.get(position);
  }

  @Override
  public int getCount() {
    // Show 2 total pages.
    return mFragmentList.size();
  }
}
