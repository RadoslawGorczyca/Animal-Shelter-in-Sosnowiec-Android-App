package pl.radoslawgorczyca.animalsheltersosnowiec.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pl.radoslawgorczyca.animalsheltersosnowiec.R;
import pl.radoslawgorczyca.animalsheltersosnowiec.types.User;

/**
 * Created by Radek on 05-Mar-18.
 */

public class LoggingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<User> {

    private EditText emailET;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.sign_in);
        setContentView(R.layout.activity_logging);

        emailET = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);

        Button loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
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

    }

    @NonNull
    @Override
    public Loader<User> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<User> loader, User data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<User> loader) {

    }
}
