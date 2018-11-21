package android.example.com.split.ui.home;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.example.com.split.FeatureFlags;
import android.example.com.split.R;
import android.example.com.split.data.entity.User;
import android.example.com.split.data.repository.UserDataRepository;
import android.example.com.split.ui.FullscreenActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final boolean AUTHENTICATION = FeatureFlags.AUTHENTICATION;
    public static final int TAB_CONTACTS = 0;
    public static final int TAB_GROUPS = 1;
    @SuppressWarnings("unused")
    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth auth;
    private final FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if (auth.getCurrentUser() == null) {
                startSignInActivity();
            }
        }
    };
    // Fab functionality
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText contactItem;
    private final View.OnClickListener addContactFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createContactPopupDialog();
        }
    };
    private final View.OnClickListener addGroupFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createGroupPopupDialog();
        }
    };
    private EditText contactName;
    private TabLayout.BaseOnTabSelectedListener onTabSelectedListener;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Contract(pure = true)
    @NonNull
    private static TabLayout.BaseOnTabSelectedListener getOnTabSelectedListener(final FloatingActionButton fab, final View.OnClickListener addContactFabListener, final View.OnClickListener addGroupFabListener) {
        return new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == TAB_CONTACTS) {
                    fab.setOnClickListener(addContactFabListener);

                } else if (tab.getPosition() == TAB_GROUPS) {
                    fab.setOnClickListener(addGroupFabListener);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (AUTHENTICATION) {
            if (auth == null) {
                auth = FirebaseAuth.getInstance();
            }
            auth.addAuthStateListener(authStateListener);
            if (auth.getCurrentUser() == null) {
                startSignInActivity();
            } else {
                final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                // to check if authenticated user exists on data base
                handleUserDatabase(firebaseUser);
            }
        }
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hi
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        toolbar = findViewById(R.id.toolbar_app_bar_main);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                                           R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view_activity_home);
        navigationView.setNavigationItemSelectedListener(this);
        ViewPager viewPager = findViewById(R.id.viewpager_app_bar_main);
        HomeTabsAdapter adapter = new HomeTabsAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabLayout_app_bar_main);
        tabLayout.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab_app_bar_main);
        fab.setOnClickListener(addContactFabListener);
        onTabSelectedListener = getOnTabSelectedListener(fab, addContactFabListener, addGroupFabListener);
        tabLayout.addOnTabSelectedListener(onTabSelectedListener);
    }

    private void handleUserDatabase(final FirebaseUser firebaseUser) {
        final String currentUserId = firebaseUser.getUid();
        if (firebaseUser != null) {

            final UserDataRepository userRepository = new UserDataRepository();
            userRepository.isUserExist(currentUserId, new UserDataRepository.IsUserExist() {

                @Override
                public void isUserExist(Boolean userExist) {
                    Toast.makeText(HomeActivity.this, "Checking", Toast.LENGTH_SHORT).show();
                    if (userExist == false) {
                        User user = getUser();
                        userRepository.createNewUser(user, currentUserId, new UserDataRepository.OnUserCreated() {
                            @Override
                            public void onUserCreated(Boolean userCreated) {
                                if (userCreated == true)
                                    Toast.makeText(HomeActivity.this, "Current user added", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(HomeActivity.this, "Current user error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(HomeActivity.this, "User exists", Toast.LENGTH_SHORT).show();
                    }
                }

                @NonNull
                private User getUser() {
                    String name = firebaseUser.getDisplayName();
                    String[] nameArray = name.split("\\s+");
                    List<String> fullName = Arrays.asList(nameArray);

                    String mail = firebaseUser.getEmail();
                    String phone = firebaseUser.getPhoneNumber();
                    return new User(currentUserId, fullName.get(0), fullName.get(1), mail, phone);
                }
            });
        }
    }


    private void startSignInActivity() {
        Intent intent = new Intent(this, FullscreenActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (AUTHENTICATION) {
                //hideSystemUI();
            }
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_IMMERSIVE
                                        // Set the content to appear under the system bars so that the
                                        // content doesn't resize when the system bars hide and show.
                                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        // Hide the nav bar and status bar
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AUTHENTICATION) {
            if (auth == null) {
                auth = FirebaseAuth.getInstance();
            }
            if (auth.getCurrentUser() == null) {
                startSignInActivity();
            }
        }
    }

    private void signOut() {
        if (AUTHENTICATION) {
            AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    // ...
                    Toast.makeText(HomeActivity.this, "Signed out", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    @Override
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        } else if (id == R.id.action_sign_out) {
            if (AUTHENTICATION) {
                signOut();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new) {
            // Handle the camera action
        } else if (id == R.id.nav_join) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createContactPopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        contactItem = findViewById(R.id.editText_dialog_add_contact_firstName);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    private void createGroupPopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_group, null);
        contactItem = findViewById(R.id.editText_dialog_add_group);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

    }
}