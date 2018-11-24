package android.example.com.split.ui.detailactivity;

import android.app.AlertDialog;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.tabfragment.ExpensesTabFragment;
import android.example.com.split.ui.tabfragment.MembersTabFragment;
import android.example.com.split.ui.tabsadapter.GroupTabsAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class GroupDetailActivity extends BaseDetailActivity {

  // add member and expense
  private AlertDialog.Builder dialogBuilder;
  private AlertDialog dialog;
  private EditText memberName;
  private Button addMemberButton;

  private EditText expenseTitle;
  private EditText expenseAmount;
  //private EditText expensePayee;
  private Button addExpenseButton;
  private Spinner expenseSpinner;

  private ExpensesTabFragment expensesTabFragment;
  private MembersTabFragment membersTabFragment;

  private Group group;

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide
   * fragments for each of the sections. We use a
   * {@link FragmentPagerAdapter} derivative, which will keep every
   * loaded fragment in memory. If this becomes too memory intensive, it
   * may be best to switch to a
   * {@link android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private GroupTabsAdapter mGroupTabsAdapter;
  private DrawerLayout drawer;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_group);

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      group = (Group) bundle.get("Group");
    }

    // add member and expense
    memberName = (EditText) findViewById(R.id.editText_dialog_add_member);
    addMemberButton = (Button) findViewById(R.id.button_dialog_add_member_save);

    expenseTitle = (EditText) findViewById(R.id.editText_dialog_add_expense_title);
    expenseAmount = (EditText) findViewById(R.id.editText_dialog_add_expense_amount);
    //expensePayee = (EditText) findViewById(R.id.);
    addExpenseButton = (Button) findViewById(R.id.button_dialog_add_expense_save);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_detail_group);
    setSupportActionBar(toolbar);

    drawer = findViewById(R.id.drawer_layout_group_detail);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                                             R.string.navigation_drawer_open,
                                                             R.string.navigation_drawer_close);

    drawer.addDrawerListener(toggle);
    toggle.syncState();

    // create bundle to pass the group to the TabFragments
    Bundle groupBundle = new Bundle();
    groupBundle.putSerializable("group", group);

    expensesTabFragment = new ExpensesTabFragment();
    expensesTabFragment.setArguments(groupBundle);
    membersTabFragment = new MembersTabFragment();
    membersTabFragment.setArguments(groupBundle);

    mGroupTabsAdapter = new GroupTabsAdapter(getSupportFragmentManager());

    mViewPager = findViewById(R.id.viewPager_activity_detail_group);
    setUpViewPager(mViewPager);

    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_activity_detail_group);
    tabLayout.setupWithViewPager(mViewPager);

    //Setting up Fab functionality
    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_activity_detail_group);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        addMemberPopupDialog();
      }
    });

    tabLayout.setupWithViewPager(mViewPager);


    tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 0) {
          FloatingActionButton floatingActionButton = findViewById(R.id.fab_activity_detail_group);
          floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //Toast.makeText(HomeActivity.this, "add contact", Toast.LENGTH_LONG).show();
              addMemberPopupDialog();
            }
          });

        } else if (tab.getPosition() == 1) {
          FloatingActionButton floatingActionButton = findViewById(R.id.fab_activity_detail_group);
          floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //Toast.makeText(HomeActivity.this, "add groups", Toast.LENGTH_LONG).show();
              addExpensePopupDialog();
            }
          });
        }
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

  }

  private void setUpViewPager(ViewPager viewPager) {
    GroupTabsAdapter adapter = new GroupTabsAdapter(getSupportFragmentManager());
    adapter.addFragment(membersTabFragment, "Members");
    adapter.addFragment(expensesTabFragment, "Expenses");
    viewPager.setAdapter(adapter);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_group, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void addMemberPopupDialog() {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    final View view = getLayoutInflater().inflate(R.layout.dialog_add_member, null);
    dialogBuilder.setView(view);
    dialog = dialogBuilder.create();

    Button saveButton = (Button) view.findViewById(R.id.button_dialog_add_member_save);
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        User user = new User();
        // takes the name user input from the text field
        memberName = (EditText) view.findViewById(R.id.editText_dialog_add_member);
        String newName = memberName.getText().toString();
        user.setFirstName(newName);
        // add the new user to the dataset in the MembersRecyclerAdapter
        List<User> dataset = membersTabFragment.getAdapter().getmDataset();
        dataset.add(user);

        // Notifies that the item at the last position is created
        int position = dataset.size() - 1;
        membersTabFragment.getAdapter().notifyItemInserted(position);

        dialog.dismiss();
      }
    });

    dialog.show();
  }

  private void addExpensePopupDialog() {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    final View view = getLayoutInflater().inflate(R.layout.dialog_add_expense, null);
    dialogBuilder.setView(view);
    dialog = dialogBuilder.create();

    expenseSpinner = (Spinner) view.findViewById(R.id.spinner_choose_member);
    ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item,
                                                        group.getUserMembers());
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    expenseSpinner.setAdapter(adapter);

    Button saveButton = (Button) view.findViewById(R.id.button_dialog_add_expense_save);
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Expense expense = new Expense();
        // takes the title user input from the text field
        expenseTitle = (EditText) view.findViewById(R.id.editText_dialog_add_expense_title);
        String newTitle = expenseTitle.getText().toString();
        expense.setTittle(newTitle);
        // takes the amount user input from the text field
        expenseAmount = (EditText) view.findViewById(R.id.editText_dialog_add_expense_amount);
        Double newAmount = Double.parseDouble(expenseAmount.getText().toString());
        expense.setPaymentAmount(newAmount);
        // takes the selected member from its position in the spinner
        int memberPosition = expenseSpinner.getSelectedItemPosition();
        User member = group.getUserMembers().get(memberPosition);
        expense.setUser(member);

        // add the new expense to the dataset in the ExpensesRecyclerAdapter
        List<Expense> dataset = expensesTabFragment.getAdapter().getDataset();
        dataset.add(expense);

        // Notifies that the item at the last position is created
        int position = dataset.size() - 1;
        expensesTabFragment.getAdapter().notifyItemInserted(position);

        dialog.dismiss();
      }
    });
    dialog.show();
  }

}
