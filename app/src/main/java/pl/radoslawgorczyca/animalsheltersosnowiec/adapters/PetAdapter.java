package pl.radoslawgorczyca.animalsheltersosnowiec.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import pl.radoslawgorczyca.animalsheltersosnowiec.types.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.R;

/**
 * Created by Radek on 24-Jan-18.
 */

public class PetAdapter extends ArrayAdapter<Pet> {

    public PetAdapter(@NonNull Context context, ArrayList<Pet> pets) {
        super(context, 0, pets);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_view_item, parent, false);

        }

        Pet currentPet = getItem(position);


        TextView animalNameTextView = listItemView.findViewById(R.id.animal_name);
        animalNameTextView.setText(currentPet.getmName());

        TextView animalStatusTextView = listItemView.findViewById(R.id.animal_status);
        //animalStatusTextView.setText(String.valueOf(currentPet.getmStatus()));
        if (currentPet.getmStatus() == 2) {
            animalStatusTextView.setText(R.string.status_quarantine);
        } else if (currentPet.getmStatus() == 3) {
            animalStatusTextView.setText(R.string.status_booked);
        } else {
            animalStatusTextView.setText(R.string.status_adoptable);
        }

        ImageView animalImageView = listItemView.findViewById(R.id.animal_image);
        //animalImageView.setImageBitmap(decodeBlobToBitmap(currentPet.getmImageBlob()));
        if (!currentPet.getmImageUrl().isEmpty()) {
            Picasso.with(getContext()).load(currentPet.getmImageUrl()).into(animalImageView);
        }


        ImageView statusIconView = listItemView.findViewById(R.id.status_icon);
        if (currentPet.getmStatus() == 2) {
            statusIconView.setImageResource(R.mipmap.orange_circle);
        } else if (currentPet.getmStatus() == 3) {
            statusIconView.setImageResource(R.mipmap.red_circle);
        } else {
            statusIconView.setImageResource(R.mipmap.green_circle);
        }

        return listItemView;

    }

    public void updateData(ArrayList<Pet> list) {

    }

    private Bitmap decodeBlobToBitmap(byte[] imageBlob) {

        if (imageBlob != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBlob, 0, imageBlob.length);
            return bitmap;
        }
        return null;
    }

}
