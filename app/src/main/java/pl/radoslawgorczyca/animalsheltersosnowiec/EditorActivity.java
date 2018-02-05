package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;

/**
 * Created by Radek on 09-Jan-18.
 */

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Pet> {

    private static final int EXISTING_PET_LOADER = 0;

    LoaderManager loaderManager;

    private Uri mCurrentPetUri;

    private Pet mPet;

    private Spinner mSpeciesSpinner;
    private Spinner mGenderSpinner;
    private Spinner mHeightSpinner;
    private Spinner mStatusSpinner;

    private EditText mCodeEditText;
    private EditText mNameEditText;
    private EditText mBreedEditText;
    private EditText mSummaryEditText;

    private int mSpecies = PetEntry.SPECIES_DOG;
    private int mGender = PetEntry.GENDER_MALE;
    private int mHeight = PetEntry.HEIGHT_SMALL;
    private int mStatus = PetEntry.STATUS_ADOPTABLE;

    private boolean mPetHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mPetHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        setTitle("Add Pet");

        Intent intent = getIntent();
        mCurrentPetUri = intent.getData();

        if (mCurrentPetUri == null) {
            setTitle(getString(R.string.editor_activity_title_new_pet));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.editor_activity_title_edit_pet));
            //getLoaderManager().initLoader(EXISTING_PET_LOADER, null, this);
        }

        mSpeciesSpinner = findViewById(R.id.spinner_species);
        mGenderSpinner = findViewById(R.id.spinner_gender);
        mHeightSpinner = findViewById(R.id.spinner_height);
        mStatusSpinner = findViewById(R.id.spinner_status);

        setupSpinners();

        mCodeEditText = findViewById(R.id.edit_pet_code);
        mNameEditText = findViewById(R.id.edit_pet_name);
        mBreedEditText = findViewById(R.id.edit_pet_breed);
        mSummaryEditText = findViewById(R.id.edit_pet_summary);

        loaderManager = getSupportLoaderManager();
    }

    private void setupSpinners() {

        ArrayAdapter speciesSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_species_options, android.R.layout.simple_spinner_item);

        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        ArrayAdapter heightSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_height_options, android.R.layout.simple_spinner_item);

        ArrayAdapter statusSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_status_options, android.R.layout.simple_spinner_item);


        // Specify dropdown layout style - simple list view with 1 item per line
        speciesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        heightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSpeciesSpinner.setAdapter(speciesSpinnerAdapter);
        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mHeightSpinner.setAdapter(heightSpinnerAdapter);
        mStatusSpinner.setAdapter(statusSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSpeciesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.species_dog))) {
                        mSpecies = PetEntry.SPECIES_DOG;
                    } else {
                        mSpecies = PetEntry.SPECIES_CAT;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSpecies = PetEntry.SPECIES_DOG;
            }
        });

        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = PetEntry.GENDER_MALE;
                    } else {
                        mGender = PetEntry.GENDER_FEMALE;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = PetEntry.GENDER_MALE;
            }
        });

        mHeightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.height_small))) {
                        mHeight = PetEntry.HEIGHT_SMALL;
                    } else if (selection.equals(getString(R.string.height_medium))) {
                        mHeight = PetEntry.HEIGHT_MEDIUM;
                    } else {
                        mHeight = PetEntry.HEIGHT_BIG;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mHeight = PetEntry.HEIGHT_SMALL;
            }
        });

        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.status_adoptable))) {
                        mStatus = PetEntry.STATUS_ADOPTABLE;
                    } else if (selection.equals(getString(R.string.status_quarantine))) {
                        mStatus = PetEntry.STATUS_QUARANTINE;
                    } else {
                        mStatus = PetEntry.STATUS_BOOKED;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mStatus = PetEntry.STATUS_ADOPTABLE;
            }
        });
    }

    private void savePet() {

        String codeString = mCodeEditText.getText().toString().trim();
        String nameString = mNameEditText.getText().toString().trim();
        String breedString = mBreedEditText.getText().toString().trim();
        String summaryString = mSummaryEditText.getText().toString().trim();

        if(mCurrentPetUri == null &&
                TextUtils.isEmpty(codeString) && TextUtils.isEmpty(nameString) &&
                TextUtils.isEmpty(breedString) && TextUtils.isEmpty(summaryString)){
            return;
        }

        /*ContentValues values = new ContentValues();
        values.put(PetEntry.COLUMN_PET_SPECIES, mSpecies);
        values.put(PetEntry.COLUMN_PET_GENDER, mGender);
        values.put(PetEntry.COLUMN_PET_HEIGHT, mHeight);
        values.put(PetEntry.COLUMN_PET_STATUS, mStatus);
        values.put(PetEntry.COLUMN_PET_CODE, codeString);
        values.put(PetEntry.COLUMN_PET_NAME, nameString);
        values.put(PetEntry.COLUMN_PET_BREED, breedString);
        values.put(PetEntry.COLUMN_PET_SUMMARY, summaryString);*/

        //mPet = new Pet(0, mSpecies, mStatus, codeString, nameString, mGender, breedString, null);

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mCurrentPetUri == null) {
            // This is a NEW pet, so insert a new pet into the provider,
            // returning the content URI for the new pet.
            //Uri newUri = getContentResolver().insert(PetEntry.CONTENT_URI, values);

            loaderManager.initLoader(2, null, this);


        /*} else {
            // Otherwise this is an EXISTING pet, so update the pet with content URI: mCurrentPetUri
            // and pass in the new ContentValues. Pass in null for the selection and selection args
            // because mCurrentPetUri will already identify the correct row in the database that
            // we want to modify.
            int rowsAffected = getContentResolver().update(mCurrentPetUri, values, null, null);

            // Show a toast message depending on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_pet_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_pet_successful),
                        Toast.LENGTH_SHORT).show();
            }*/
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save pet to database
                savePet();
                // Exit activity
                //finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Pet> onCreateLoader(int id, Bundle args) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        Uri baseUri = Uri.parse(PetContract.SHELTER_POST_URL);
        return new PetPostLoader(this, baseUri.toString(), mPet);
    }

    @Override
    public void onLoadFinished(Loader<Pet> loader, Pet addedPet) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        mPet = addedPet;
        // Show a toast message depending on whether or not the insertion was successful.
        if (mPet.getmId() == 0) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, getString(R.string.editor_insert_pet_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, getString(R.string.editor_insert_pet_successful),
                    Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @Override
    public void onLoaderReset(Loader<Pet> loader) {

    }
}
