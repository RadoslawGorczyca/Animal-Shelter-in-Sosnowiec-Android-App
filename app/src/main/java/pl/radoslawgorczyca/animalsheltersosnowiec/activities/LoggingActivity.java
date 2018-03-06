package pl.radoslawgorczyca.animalsheltersosnowiec.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import pl.radoslawgorczyca.animalsheltersosnowiec.types.User;

/**
 * Created by Radek on 05-Mar-18.
 */

public class LoggingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<User> {

    LoaderManager loaderManager;

    private EditText emailET;
    private EditText passwordET;

    String email;
    String password;

    User fetchedUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.sign_in);
        setContentView(R.layout.activity_logging);

        loaderManager = getSupportLoaderManager();

        emailET = (EditText) findViewById(R.id.login_email);
        passwordET = (EditText) findViewById(R.id.login_password);

        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailET.getText().toString().trim();
                password = passwordET.getText().toString().trim();
                loaderManager.initLoader(7, null, LoggingActivity.this);
            }
        });

        Button registerButton = (Button) findViewById(R.id.go_to_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoggingActivity.this, RegisterActivity.class));
            }
        });
    }

    private void login() {

        if(fetchedUser == null){
            Toast.makeText(this, "No user with this email found", Toast.LENGTH_SHORT).show();
        } else {
            String fetchedDecryptedPassword = "";
            try {
                fetchedDecryptedPassword = AESCrypt.decrypt(fetchedUser.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(!fetchedDecryptedPassword.equals(password)){
                Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public Loader<User> onCreateLoader(int id, @Nullable Bundle args) {

        return new GetUserLoader(this, PetContract.SHELTER_USER_GET_URL, email);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<User> loader, User data) {
        fetchedUser = data;
        login();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<User> loader) {

    }
}
