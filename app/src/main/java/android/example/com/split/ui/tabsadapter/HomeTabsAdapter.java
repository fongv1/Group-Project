package android.example.com.split.ui.tabsadapter;

import android.content.Context;
import android.example.com.split.R;
import android.example.com.split.ui.tabfragment.GroupsTabFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class HomeTabsAdapter extends TabsAdapter {

  /**
   * Context of the app
   */
  private Context mContext;

  private ContactsTabFragment contactsTabFragment;
  private GroupsTabFragment groupsTabFragment;

  /**
   * Create a new {@link HomeTabsAdapter} object.
   *
   * @param context is the context of the app
   * @param fm      is the fragment manager that will keep each fragment's state in the adapter
   *                across swipes.
   */
  public HomeTabsAdapter(Context context, FragmentManager fm) {
    super(fm);
    mContext = context;
  }

  /**
   * Return the {@link Fragment} that should be displayed for the given page number.
   */
  @Override
  public Fragment getItem(int position) {
    if (position == 0) {
      contactsTabFragment = new ContactsTabFragment();
      return contactsTabFragment;
    } else {
      groupsTabFragment = new GroupsTabFragment();
      return groupsTabFragment;
    }
  }

  /**
   * Return the total number of pages.
   */
  @Override
  public int getCount() {
    return 2;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    if (position == 0) {
      return mContext.getString(R.string.category_contacts);
    } else {
      return mContext.getString(R.string.category_groups);
    }
  }

  public ContactsTabFragment getContactsTabFragment() {
    return contactsTabFragment;
  }

  public GroupsTabFragment getGroupsTabFragment() {
    return groupsTabFragment;
  }
}
