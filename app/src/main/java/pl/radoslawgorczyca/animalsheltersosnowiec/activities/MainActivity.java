package pl.radoslawgorczyca.animalsheltersosnowiec.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import pl.radoslawgorczyca.animalsheltersosnowiec.adapters.CategoryFragmentPagerAdapter;
import pl.radoslawgorczyca.animalsheltersosnowiec.R;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.loaders.GetUserLoader;
import pl.radoslawgorczyca.animalsheltersosnowiec.security.AESCrypt;
import pl.radoslawgorczyca.animalsheltersosnowiec.security.UserSession;
import pl.radoslawgorczyca.animalsheltersosnowiec.types.User;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    UserSession session;

    User loggedUser = null;

    MenuItem actionSignIn;
    MenuItem actionLogOut;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        session = new UserSession(getApplicationContext());
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Setup FAB to open EditorActivity
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        if(session.isUserLoggedIn()){
            String name = sharedPreferences.getString(UserSession.KEY_NAME, null);
            String surname = sharedPreferences.getString(UserSession.KEY_SURNAME, null);

            Toast.makeText(this, getString(R.string.hello) + " " + name + " " + surname, Toast.LENGTH_SHORT).show();
        }

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.view_pager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryFragmentPagerAdapter adapter = new CategoryFragmentPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);


        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        invalidateOptionsMenu();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        invalidateOptionsMenu();
        if (session.isUserLoggedIn()) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (session.isUserLoggedIn()) {
            fab.setVisibility(View.VISIBLE);
            actionSignIn.setVisible(false);
            actionLogOut.setVisible(true);
        } else {
            fab.setVisibility(View.GONE);
            actionSignIn.setVisible(true);
            actionLogOut.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);

        actionSignIn = menu.findItem(R.id.action_sign_in);
        actionLogOut = menu.findItem(R.id.action_log_out);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_sign_in:
                startActivity(new Intent(this, LoggingActivity.class));
                return true;

            case R.id.action_log_out:
                session.logoutUser();
                invalidateOptionsMenu();
                Toast.makeText(this, R.string.successfuly_logged_out, Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
