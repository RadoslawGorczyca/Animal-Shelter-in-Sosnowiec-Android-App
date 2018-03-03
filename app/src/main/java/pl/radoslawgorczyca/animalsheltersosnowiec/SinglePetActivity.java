package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;

/**
 * Created by Radek on 18-Dec-17.
 */

public class SinglePetActivity extends AppCompatActivity {

    private Pet currentPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_pet_layout);

        ImageLoader imageLoader = ImageLoader.getInstance();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            currentPet = (Pet) getIntent().getSerializableExtra("currentPet");
        }

        ImageView animalImage = findViewById(R.id.single_animal_image);
        Picasso.with(this).load(currentPet.getmImageUrl()).into(animalImage);


        TextView animalName = findViewById(R.id.single_animal_name);
        animalName.setText(currentPet.getmName());

        //Button reservationButton = findViewById(R.id.reservation);
        ImageView statusIcon = findViewById(R.id.single_animal_status_icon);
        TextView animalStatusTextView = findViewById(R.id.single_animal_status);
        if (currentPet.getmStatus() == 2) {
            animalStatusTextView.setText(R.string.status_quarantine);
            statusIcon.setImageResource(R.mipmap.orange_circle);
            //reservationButton.setEnabled(false);
        } else if (currentPet.getmStatus() == 3) {
            animalStatusTextView.setText(R.string.status_booked);
            statusIcon.setImageResource(R.mipmap.red_circle);
            //reservationButton.setEnabled(false);
        } else {
            animalStatusTextView.setText(R.string.status_adoptable);
            statusIcon.setImageResource(R.mipmap.green_circle);
            //reservationButton.setEnabled(true);
        }

        Button contactButton = findViewById(R.id.contact_with);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = currentPet.getmContactNumber();
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(i);
            }
        });

        /*reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Brak połączenia z serwerem. Spróbuj później.", Toast.LENGTH_SHORT).show();
            }
        });*/

        TextView genderTV = findViewById(R.id.gender);
        TextView sterilizedTitleTV = findViewById(R.id.sterilizedTitle);
        if(currentPet.getmGender() == PetContract.PetEntry.GENDER_MALE){
            genderTV.setText(R.string.gender_male);
            sterilizedTitleTV.setText(R.string.castrated);
        } else {
            genderTV.setText(R.string.gender_female);
            sterilizedTitleTV.setText(R.string.sterilized);
        }

        TextView heightTV = findViewById(R.id.height);
        if(currentPet.getmHeight() == PetContract.PetEntry.HEIGHT_SMALL){
            heightTV.setText(R.string.height_small);
        } else if(currentPet.getmHeight() == PetContract.PetEntry.HEIGHT_MEDIUM){
            heightTV.setText(R.string.height_medium);
        } else {
            heightTV.setText(R.string.height_big);
        }

        TextView birthYearTV = findViewById(R.id.birth_year);
        birthYearTV.setText(currentPet.getmBirthYear());

        TextView acceptanceDateTV = findViewById(R.id.acceptance_date);
        acceptanceDateTV.setText(currentPet.getmAcceptanceDate());

        TextView sterilizedTV = findViewById(R.id.sterilized);
        if(currentPet.getmSterilized() == PetContract.PetEntry.STERILIZED_YES){
            sterilizedTV.setText(R.string.yes);
        } else {
            sterilizedTV.setText(R.string.no);
        }

        TextView summaryTV = findViewById(R.id.summary);
        summaryTV.setText(currentPet.getmSummary());
    }

    private Bitmap decodeBlobToBitmap(byte[] imageBlob) {

        if(imageBlob != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            return bitmap;
        }
        return null;
    }
}
