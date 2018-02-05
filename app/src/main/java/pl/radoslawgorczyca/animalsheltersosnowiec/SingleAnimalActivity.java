package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Radek on 18-Dec-17.
 */

public class SingleAnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_pet_layout);

        final Intent intent = getIntent();

        TextView animalName = findViewById(R.id.single_animal_name);
        animalName.setText(intent.getStringExtra("name"));


        String animalStatusString = intent.getStringExtra("status");
        TextView status = findViewById(R.id.single_animal_status);
        status.setText(animalStatusString);

        //Button reservationButton = findViewById(R.id.reservation);
        ImageView statusIcon = findViewById(R.id.single_animal_status_icon);

        if(animalStatusString.equals("Kwarantanna")){
            statusIcon.setImageResource(R.mipmap.orange_circle);
            //reservationButton.setEnabled(false);
        }else if(animalStatusString.equals("Rezerwacja")){
            statusIcon.setImageResource(R.mipmap.red_circle);
            //reservationButton.setEnabled(false);
        }else{
            statusIcon.setImageResource(R.mipmap.green_circle);
            //reservationButton.setEnabled(true);
        }

        ImageView animalImage = findViewById(R.id.single_animal_image);
        animalImage.setImageResource(intent.getIntExtra("imageId", 0));


        Button contactButton = findViewById(R.id.contact_with);

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = "+48792070625";
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

    }
}
