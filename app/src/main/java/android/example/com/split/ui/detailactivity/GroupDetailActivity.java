package android.example.com.split.ui.detailactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.example.com.split.R;
import android.example.com.split.data.entity.Expense;
import android.example.com.split.data.entity.Group;
import android.example.com.split.data.entity.User;
import android.example.com.split.ui.tabfragment.ExpensesTabFragment;
import android.example.com.split.ui.tabfragment.MembersTabFragment;
import android.example.com.split.ui.tabsadapter.GroupTabsAdapter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
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
  private static final String TAG = "GroupDetailActivity";

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

  public GroupDetailActivity() {
    init(R.string.title_activity_group, R.layout.activity_detail_group, R.menu.menu_group);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      group = (Group) bundle.get("Group");
      setTitle(group.getName());
    }

    // add member and expense
    memberName = (EditText) findViewById(R.id.editText_dialog_add_member);
    addMemberButton = (Button) findViewById(R.id.button_dialog_add_member_save);

    expenseTitle = (EditText) findViewById(R.id.editText_dialog_add_expense_title);
    expenseAmount = (EditText) findViewById(R.id.editText_dialog_add_expense_amount);
    //expensePayee = (EditText) findViewById(R.id.);
    addExpenseButton = (Button) findViewById(R.id.button_dialog_add_expense_save);

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
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
            .getSystemService(
            Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (!isConnected) {
          Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT).show();
        } else {
          addMemberPopupDialog();
        }
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
              ConnectivityManager connectivityManager = (ConnectivityManager)
                  getApplicationContext()
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
              NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
              boolean isConnected =
                  activeNetwork != null && activeNetwork.isConnectedOrConnecting();

              if (!isConnected) {
                Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT).show();
              } else {
                //Toast.makeText(HomeActivity.this, "add contact", Toast.LENGTH_LONG).show();
                addMemberPopupDialog();
              }
            }
          });

        } else if (tab.getPosition() == 1) {
          FloatingActionButton floatingActionButton = findViewById(R.id.fab_activity_detail_group);
          floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //Toast.makeText(HomeActivity.this, "add groups", Toast.LENGTH_LONG).show();
              ConnectivityManager connectivityManager = (ConnectivityManager)
                  getApplicationContext()
                  .getSystemService(Context.CONNECTIVITY_SERVICE);
              NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
              boolean isConnected =
                  activeNetwork != null && activeNetwork.isConnectedOrConnecting();

              if (!isConnected) {
                Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT).show();
              } else {
                addExpensePopupDialog();
              }
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
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    } else if (id == R.id.home) {
      Intent upIntent = NavUtils.getParentActivityIntent(this);
      if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
        // This activity is NOT part of this app's task, so create a new task
        // when navigating up, with a synthesized back stack.
        TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent
                        .startActivities();
      } else {
        // This activity is part of this app's task, so simply
        // navigate up to the logical parent activity.
        NavUtils.navigateUpTo(this, upIntent);
      }
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  private void addMemberPopupDialog() {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    final View view = getLayoutInflater().inflate(R.layout.dialog_add_member, null);
    dialogBuilder.setView(view);
    dialog = dialogBuilder.create();

    // set the spinner to show all the contacts
    final Spinner contactsSpinner = (Spinner) view.findViewById(R.id.spinner_choose_contact);
    ArrayAdapter<User> adapter = new ArrayAdapter<User>(this, android.R.layout.simple_spinner_item,
                                                        getContactsData());
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    contactsSpinner.setAdapter(adapter);

    contactsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        User user = (User) parent.getSelectedItem();
        // Toast.makeText(getBaseContext(),user.getId(),Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onItemSelected: " + position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    Button saveButton = (Button) view.findViewById(R.id.button_dialog_add_member_save);
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        User user = new User();
        // takes the name user input from the text field
        memberName = (EditText) view.findViewById(R.id.editText_dialog_add_member);
        String newName = memberName.getText().toString();

        if (!newName.trim().isEmpty()) {
          user.setFirstName(newName);

          membersTabFragment.saveNewMemberInGroupToRemoteDb(group, user);

          // add the new user to the dataset in the MembersRecyclerAdapter
          List<User> dataset = membersTabFragment.getRecyclerAdapter().getDataset();
          dataset.add(user);

          // Notifies that the item at the last position is created
          int position = dataset.size() - 1;
          membersTabFragment.getRecyclerAdapter().notifyItemInserted(position);
        }

        dialog.dismiss();
      }
    });

    dialog.show();
  }

  @NonNull
  private List<User> getContactsData() {
    //initDataset();
    final List<User> loadedContacts = new ArrayList<>();
    //setData(loadedContacts);
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    db.collection("users")
      .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
      .get()
      .addOnCompleteListener(

          new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              if (task.isSuccessful()) {
                DocumentSnapshot userDocumentSnapshot = task.getResult();
                User currentUser = userDocumentSnapshot.toObject(User.class);

                List<String> myContactsIds = currentUser.getContacts();
                if (myContactsIds != null && !myContactsIds.isEmpty()) {
                  for (String contactId : myContactsIds) {
                    db.collection("users")
                      .document(contactId)
                      .get()
                      .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                          DocumentSnapshot contactUserDocumentSnapshot = task.getResult();
                          if (contactUserDocumentSnapshot.exists()) {
                            User contactUser = contactUserDocumentSnapshot.toObject(User.class);
                            loadedContacts.add(contactUser);
                          }

                        }
                      });
                  }

                }
              }
            }
          });

    return loadedContacts;
  }

  private void addExpensePopupDialog() {
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    final View view = getLayoutInflater().inflate(R.layout.dialog_add_expense, null);
    dialogBuilder.setView(view);
    dialog = dialogBuilder.create();

    final Spinner expenseSpinner = (Spinner) view.findViewById(R.id.spinner_choose_member);
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
        expenseTitle = view.findViewById(R.id.editText_dialog_add_expense_title);
        String newTitle = expenseTitle.getText().toString();

        // takes the amount user input from the text field
        expenseAmount = view.findViewById(R.id.editText_dialog_add_expense_amount);
        double newAmount = ParseDouble(expenseAmount.getText().toString());

        // takes the selected member from its position in the spinner
        int memberPosition = expenseSpinner.getSelectedItemPosition();
        User member = group.getUserMembers().get(memberPosition);

        if (!newTitle.trim().isEmpty() && newAmount > 0.0) {

          expense.setTittle(newTitle);
          expense.setPaymentAmount(newAmount);
          expense.setUser(member);
          expense.setPayerName(member.getFirstName());

          // add the new expense to the dataset in the ExpensesRecyclerAdapter
          List<Expense> dataset = expensesTabFragment.getData();
          dataset.add(expense);

          // Notifies that the item at the last position is created
          int position = dataset.size() - 1;
          expensesTabFragment.getRecyclerAdapter().notifyItemInserted(position);

        }

        dialog.dismiss();
      }
    });
    dialog.show();
  }

  double ParseDouble(String strNumber) {
    if (strNumber != null && strNumber.length() > 0) {
      try {
        return Double.parseDouble(strNumber);
      } catch (Exception e) {
        return -1;   // -1 marks this field is wrong. or make a function validates field first ...
      }
    } else
      return 0;
  }

}
