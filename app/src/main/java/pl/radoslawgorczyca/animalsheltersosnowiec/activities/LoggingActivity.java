package pl.radoslawgorczyca.animalsheltersosnowiec.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import pl.radoslawgorczyca.animalsheltersosnowiec.R;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.loaders.GetUserLoader;
import pl.radoslawgorczyca.animalsheltersosnowiec.security.AESCrypt;
import pl.radoslawgorczyca.animalsheltersosnowiec.security.UserSession;
import pl.radoslawgorczyca.animalsheltersosnowiec.types.User;

/**
 * Created by Radek on 05-Mar-18.
 */

public class LoggingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<User> {

    LoaderManager loaderManager;
    ProgressDialog ringProgressDialog;

    private static final String PREFER_NAME = "Reg";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    UserSession session;

    private EditText emailET;
    private EditText passwordET;

    String email;
    String password;

    User fetchedUser;

    boolean isLoaderInitialized = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.sign_in);
        setContentView(R.layout.activity_logging);

        loaderManager = getSupportLoaderManager();

        emailET = findViewById(R.id.login_email);
        passwordET = findViewById(R.id.login_password);

        session = new UserSession(getApplicationContext());

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        String savedEmail = sharedPreferences.getString("email", "");
        if(!savedEmail.equals("")){
            emailET.setText(savedEmail);
        }

        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isLoaderInitialized){
                    loaderManager.initLoader(7, null, LoggingActivity.this);
                    isLoaderInitialized = true;
                }else {
                    loaderManager.restartLoader(7, null, LoggingActivity.this);
                }
            }
        });

        /*Button registerButton = (Button) findViewById(R.id.go_to_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggingActivity.this, RegisterActivity.class));
            }
        });*/
    }

    private void login() {

        if(fetchedUser == null){
            Toast.makeText(this, getString(R.string.email_not_found), Toast.LENGTH_SHORT).show();
        } else {
            String fetchedDecryptedPassword = "";
            try {
                fetchedDecryptedPassword = AESCrypt.decrypt(fetchedUser.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(!fetchedDecryptedPassword.equals(password)){
                Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, R.string.login_successful, Toast.LENGTH_SHORT).show();
                session.createUserLoginSession(fetchedUser.getEmail(), fetchedUser.getPassword(), fetchedUser.getName(), fetchedUser.getSurname());
                editor.putString("name", fetchedUser.getName());
                editor.putString("surname", fetchedUser.getSurname());
                editor.putString("email",fetchedUser.getEmail());
                editor.putString("password",fetchedUser.getPassword());
                editor.commit();
                finish();
            }
        }
    }

    @NonNull
    @Override
    public Loader<User> onCreateLoader(int id, @Nullable Bundle args) {
        ringProgressDialog = ProgressDialog.show(
                LoggingActivity.this,
                getString(R.string.logging),
                getString(R.string.please_wait),
                true);
        //you usually don't want the user to stop the current process, and this will make sure of that
        ringProgressDialog.setCancelable(false);
        email = emailET.getText().toString().trim();
        password = passwordET.getText().toString().trim();
        return new GetUserLoader(this, PetContract.SHELTER_USER_GET_URL, email);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<User> loader, User data) {
        fetchedUser = data;
        ringProgressDialog.hide();
        login();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<User> loader) {

    }
}
