package pl.radoslawgorczyca.animalsheltersosnowiec.activities;

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
import pl.radoslawgorczyca.animalsheltersosnowiec.loaders.NewUserLoader;
import pl.radoslawgorczyca.animalsheltersosnowiec.loaders.PetPostLoader;
import pl.radoslawgorczyca.animalsheltersosnowiec.security.AESCrypt;
import pl.radoslawgorczyca.animalsheltersosnowiec.types.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.types.User;

/**
 * Created by Ebicom-RG on 06.03.2018.
 */

public class RegisterActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<User> {

    LoaderManager loaderManager;

    User newUser;

    private EditText nameET;
    private EditText surnameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText repeatPasswordET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.register);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_register);

        loaderManager = getSupportLoaderManager();

        nameET = (EditText) findViewById(R.id.name);
        surnameET = (EditText) findViewById(R.id.surname);
        emailET = (EditText) findViewById(R.id.email);
        passwordET = (EditText) findViewById(R.id.password);
        repeatPasswordET = (EditText) findViewById(R.id.repeat_password);
        Button registerButton = (Button) findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser() {
        String name = nameET.getText().toString().trim();
        String surname = surnameET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();
        String repeatPassword = passwordET.getText().toString().trim();

        //TODO Make this code robust

        try {
            password = AESCrypt.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        newUser = new User(email, name, surname, password);

        loaderManager.initLoader(5, null, this);
}

    @NonNull
    @Override
    public Loader<User> onCreateLoader(int id, @Nullable Bundle args) {
        Uri baseUri = Uri.parse(PetContract.SHELTER_USER_INSERT_URL);
        return new NewUserLoader(this, baseUri.toString(), newUser);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<User> loader, User addedUser) {

        if(addedUser.getIdUser() != 0){
            Toast.makeText(this, "User added", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "User adding failed", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<User> loader) {

    }
}
