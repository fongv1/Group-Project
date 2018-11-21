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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressWarnings("unused")
    private static final int RC_SIGN_IN = 0;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    // Fab functionality
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    @SuppressWarnings("unused")
    private EditText contactItem;
    @SuppressWarnings("unused")
    private EditText contactName;
    @SuppressWarnings("unused")
    private Button saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        if (FeatureFlags.AUTHENTICATION) {
            auth = FirebaseAuth.getInstance();
            // to check if authenticated user exists on data base
            handleUserDatabase(firebaseUser);
        }
        if (FeatureFlags.AUTHENTICATION && (auth.getCurrentUser() == null)) {
            Intent intent = new Intent(this, FullscreenActivity.class);
            startActivity(intent);
            finish();
        } else {

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_app_bar_main);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_app_bar_main);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Intent addGroup = new Intent(HomeActivity.this, CreateGroupActivity.class);
                    //startActivity(addGroup);

                    /*Fragment f = getSupportFragmentManager().findFragmentById(R.id.tabLayout);

                    if (f instanceof GroupsTabFragment){
                        Toast.makeText(getBaseContext(), "Groups", Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(getBaseContext(), "Contacts", Toast.LENGTH_LONG).show();
                    }*/
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                                                                     R.string.navigation_drawer_open,
                                                                     R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_activity_home);
            navigationView.setNavigationItemSelectedListener(this);

            ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_app_bar_main);
            HomeTabsAdapter adapter = new HomeTabsAdapter(this, getSupportFragmentManager());
            viewPager.setAdapter(adapter);

            FloatingActionButton floatingActionButton = findViewById(R.id.fab_app_bar_main);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FloatingActionButton floatingActionButton = findViewById(R.id.fab_app_bar_main);
                    floatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createContactPopupDialog();
                            //Toast.makeText(HomeActivity.this, "add contact", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_app_bar_main);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    if (tab.getPosition() == 0) {
                        FloatingActionButton floatingActionButton = findViewById(R.id.fab_app_bar_main);
                        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createContactPopupDialog();
                                //Toast.makeText(HomeActivity.this, "add contact", Toast.LENGTH_LONG).show();
                            }
                        });

                    } else if (tab.getPosition() == 1) {
                        FloatingActionButton floatingActionButton = findViewById(R.id.fab_app_bar_main);
                        floatingActionButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createGroupPopupDialog();
                                //Toast.makeText(HomeActivity.this, "add groups", Toast.LENGTH_LONG).show();
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


            if (FeatureFlags.AUTHENTICATION) {
                authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (auth.getCurrentUser() != null) {
                            // Signed in
                        } else {
                            Intent intent = new Intent(HomeActivity.this, FullscreenActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                auth.addAuthStateListener(authStateListener);
            }
        }
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (FeatureFlags.AUTHENTICATION) {
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
        if (FeatureFlags.AUTHENTICATION) {
            auth = FirebaseAuth.getInstance();
            if (auth.getCurrentUser() == null) {
                Intent intent = new Intent(this, FullscreenActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    private void signOut() {
        if (FeatureFlags.AUTHENTICATION) {
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
            if (FeatureFlags.AUTHENTICATION) {
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void createContactPopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_contact, null);
        contactItem = (EditText) findViewById(R.id.editText_dialog_add_contact);
        saveButton = (Button) findViewById(R.id.button_dialog_add_contact_save);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

    }

    private void createGroupPopupDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_add_group, null);
        contactItem = (EditText) findViewById(R.id.editText_dialog_add_group);
        saveButton = (Button) findViewById(R.id.button_dialog_add_group_save);

        dialogBuilder.setView(view);
        dialog = dialogBuilder.create();
        dialog.show();

    }


}