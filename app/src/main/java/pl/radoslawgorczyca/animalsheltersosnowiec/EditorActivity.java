package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;

/**
 * Created by Radek on 09-Jan-18.
 */

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Pet> {

    private static final int EXISTING_PET_LOADER = 0;

    LoaderManager loaderManager;
    ProgressDialog ringProgressDialog;

    private String mUrl;

    private Pet mPet;

    private boolean isExistingPetFlag = false;
    private boolean isUploadingImage = false;

    private ImageButton mImageButton;
    Uri mCropImageUri;
    Uri mResultUri;

    private Spinner mSpeciesSpinner;
    private Spinner mGenderSpinner;
    private Spinner mHeightSpinner;
    private Spinner mStatusSpinner;
    private Spinner mSterilizedSpinner;

    private EditText mCodeEditText;
    private EditText mNameEditText;
    private EditText mBreedEditText;
    private EditText mSummaryEditText;
    private EditText mBirthYearEditText;
    private EditText mAcceptanceDateEditText;
    private EditText mContactNumberEditText;

    private int mSpecies = PetEntry.SPECIES_DOG;
    private int mGender = PetEntry.GENDER_MALE;
    private int mHeight = PetEntry.HEIGHT_SMALL;
    private int mStatus = PetEntry.STATUS_ADOPTABLE;
    private int mSterilized = PetEntry.STERILIZED_YES;

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mPet = (Pet) getIntent().getSerializableExtra("currentPet");
            isExistingPetFlag = true;
        }

        mImageButton = findViewById(R.id.edit_pet_image);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.startPickImageActivity(EditorActivity.this);
            }
        });

        mSpeciesSpinner = findViewById(R.id.spinner_species);
        mGenderSpinner = findViewById(R.id.spinner_gender);
        mHeightSpinner = findViewById(R.id.spinner_height);
        mStatusSpinner = findViewById(R.id.spinner_status);
        mSterilizedSpinner = findViewById(R.id.spinner_sterilized);

        mCodeEditText = findViewById(R.id.edit_pet_code);
        mNameEditText = findViewById(R.id.edit_pet_name);
        mBreedEditText = findViewById(R.id.edit_pet_breed);
        mSummaryEditText = findViewById(R.id.edit_pet_summary);
        mBirthYearEditText = findViewById(R.id.edit_pet_birth_year);
        mAcceptanceDateEditText = findViewById(R.id.edit_pet_acceptance_date);
        mContactNumberEditText = findViewById(R.id.edit_pet_contact_number);

        loaderManager = getSupportLoaderManager();

        if (!isExistingPetFlag) {
            setTitle(getString(R.string.editor_activity_title_new_pet));
            invalidateOptionsMenu();
        } else {
            setTitle(getString(R.string.editor_activity_title_edit_pet));
            invalidateOptionsMenu();
            showDataOnActivity();
        }

        setupSpinners();

    }

    private void showDataOnActivity() {
        Picasso.with(this).load(mPet.getmImageUrl()).into(mImageButton);
        mSpecies = mPet.getmSpecies();
        mGender = mPet.getmGender();
        mHeight = mPet.getmHeight();
        mStatus = mPet.getmStatus();
        mSterilized = mPet.getmSterilized();

        mCodeEditText.setText(mPet.getmCode().equals("null") ? "" : mPet.getmCode());
        mNameEditText.setText(mPet.getmName().equals("null") ? "" : mPet.getmName());
        mBreedEditText.setText(mPet.getmBreed().equals("null") ? "" : mPet.getmBreed());
        mSummaryEditText.setText(mPet.getmSummary().equals("null") ? "" : mPet.getmSummary());
        mBirthYearEditText.setText(mPet.getmBirthYear().equals("null") ? "" : mPet.getmBirthYear());
        mAcceptanceDateEditText.setText(mPet.getmAcceptanceDate().equals("null") ? "" : mPet.getmAcceptanceDate());
        mContactNumberEditText.setText(mPet.getmContactNumber().equals("null") ? "" : mPet.getmContactNumber());
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

        ArrayAdapter sterilizedSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_sterilized_options, android.R.layout.simple_spinner_item);


        // Specify dropdown layout style - simple list view with 1 item per line
        speciesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        heightSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sterilizedSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSpeciesSpinner.setAdapter(speciesSpinnerAdapter);
        mGenderSpinner.setAdapter(genderSpinnerAdapter);
        mHeightSpinner.setAdapter(heightSpinnerAdapter);
        mStatusSpinner.setAdapter(statusSpinnerAdapter);
        mSterilizedSpinner.setAdapter(sterilizedSpinnerAdapter);

        if(isExistingPetFlag){
            String currentStringResource;
            //species
            if(mPet.getmSpecies() == PetEntry.SPECIES_DOG){
                currentStringResource = getString(R.string.species_dog);
            }else{
                currentStringResource = getString(R.string.species_cat);
            }

            for(int i=0; i < speciesSpinnerAdapter.getCount(); i++) {
                if(currentStringResource.trim().equals(speciesSpinnerAdapter.getItem(i).toString())){
                    mSpeciesSpinner.setSelection(i);
                    break;
                }
            }
            //gender
            if(mPet.getmGender() == PetEntry.GENDER_MALE){
                currentStringResource = getString(R.string.gender_male);
            }else{
                currentStringResource = getString(R.string.gender_female);
            }

            for(int i=0; i < genderSpinnerAdapter.getCount(); i++) {
                if(currentStringResource.trim().equals(genderSpinnerAdapter.getItem(i).toString())){
                    mGenderSpinner.setSelection(i);
                    break;
                }
            }
            //height
            if(mPet.getmHeight() == PetEntry.HEIGHT_SMALL){
                currentStringResource = getString(R.string.height_small);
            }else if(mPet.getmHeight() == PetEntry.HEIGHT_MEDIUM){
                currentStringResource = getString(R.string.height_medium);
            } else{
                currentStringResource = getString(R.string.height_big);
            }

            for(int i=0; i < heightSpinnerAdapter.getCount(); i++) {
                if(currentStringResource.trim().equals(heightSpinnerAdapter.getItem(i).toString())){
                    mHeightSpinner.setSelection(i);
                    break;
                }
            }
            //status
            if(mPet.getmStatus() == PetEntry.STATUS_ADOPTABLE){
                currentStringResource = getString(R.string.status_adoptable);
            }else if(mPet.getmStatus() == PetEntry.STATUS_QUARANTINE){
                currentStringResource = getString(R.string.status_quarantine);
            } else{
                currentStringResource = getString(R.string.status_booked);
            }

            for(int i=0; i < statusSpinnerAdapter.getCount(); i++) {
                if(currentStringResource.trim().equals(statusSpinnerAdapter.getItem(i).toString())){
                    mStatusSpinner.setSelection(i);
                    break;
                }
            }
            //sterilized
            if(mPet.getmSterilized() == PetEntry.STERILIZED_YES){
                currentStringResource = getString(R.string.sterilized_yes);
            }else{
                currentStringResource = getString(R.string.sterilized_no);
            }

            for(int i=0; i < sterilizedSpinnerAdapter.getCount(); i++) {
                if(currentStringResource.trim().equals(sterilizedSpinnerAdapter.getItem(i).toString())){
                    mSterilizedSpinner.setSelection(i);
                    break;
                }
            }
        }

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

        mSterilizedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selection = (String) adapterView.getItemAtPosition(i);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.sterilized_yes))) {
                        mSterilized = PetEntry.STERILIZED_YES;
                    } else {
                        mSterilized = PetEntry.STERILIZED_NO;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mStatus = PetEntry.STERILIZED_YES;
            }
        });
    }

    public void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .setRequestedSize(500, 500, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                .setFixAspectRatio(true)
                .start(this);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // handle result of pick image chooser
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);


            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {
                // no permissions required or already granted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mResultUri = result.getUri();
                mImageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageButton.setImageURI(mResultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CropImage.startPickImageActivity(this);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE) {
            if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // required permissions granted, start crop image activity
                startCropImageActivity(mCropImageUri);
            } else {
                Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    private void savePet() {

        ringProgressDialog = ProgressDialog.show(
                EditorActivity.this,
                getString(isExistingPetFlag ? R.string.editing_pet : R.string.adding_pet),
                getString(R.string.please_wait),
                true);
        //you usually don't want the user to stop the current process, and this will make sure of that
        ringProgressDialog.setCancelable(false);

        String codeString = mCodeEditText.getText().toString().trim();
        String nameString = mNameEditText.getText().toString().trim();
        String breedString = mBreedEditText.getText().toString().trim();
        String summaryString = mSummaryEditText.getText().toString().trim();
        String birthYearString = mBirthYearEditText.getText().toString().trim();
        String acceptanceDateString = mAcceptanceDateEditText.getText().toString().trim();
        String contactNumberString = mContactNumberEditText.getText().toString().trim();


        if (mPet == null &&
                TextUtils.isEmpty(codeString) && TextUtils.isEmpty(nameString) &&
                TextUtils.isEmpty(breedString) && TextUtils.isEmpty(summaryString) &&
                TextUtils.isEmpty(birthYearString) && TextUtils.isEmpty(acceptanceDateString) &&
                TextUtils.isEmpty(contactNumberString)) {
            return;
        }

        // Determine if this is a new or existing pet by checking if mCurrentPetUri is null or not
        if (mPet == null) {
            mUrl = PetContract.SHELTER_POST_URL;
        } else {
            mUrl = PetContract.SHELTER_UPDATE_URL;
        }

        String imageUrl = "";
        if (mResultUri != null) {
            imageUrl = mResultUri.toString();
            isUploadingImage = true;
        } else if(isExistingPetFlag) {
            imageUrl = mPet.getmImageUrl();
        }

        if(isExistingPetFlag){
            mPet = new Pet(mPet.getmId(), mSpecies, codeString, nameString, mStatus, mGender,
                    mHeight, birthYearString, acceptanceDateString, mSterilized, summaryString, imageUrl, breedString, contactNumberString);
        } else{
            mPet = new Pet(mSpecies, codeString, nameString, mStatus, mGender, mHeight,
                    birthYearString, acceptanceDateString, mSterilized, summaryString, imageUrl, breedString, contactNumberString);
        }

        loaderManager.initLoader(2, null, this);


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
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        Uri baseUri = Uri.parse(mUrl);
        return new PetPostLoader(this, baseUri.toString(), mPet, isExistingPetFlag, isUploadingImage);
    }

    @Override
    public void onLoadFinished(Loader<Pet> loader, Pet addedPet) {
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        mPet = addedPet;

        if(isExistingPetFlag){

        }
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
        ringProgressDialog.hide();
        Intent intent = new Intent(this, SinglePetActivity.class);
        intent.putExtra("currentPet", mPet);
        startActivity(intent);
    }

    @Override
    public void onLoaderReset(Loader<Pet> loader) {

    }
}
