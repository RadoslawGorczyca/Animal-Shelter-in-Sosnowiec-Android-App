package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Radek on 24-Jan-18.
 */

public class PetAdapter extends ArrayAdapter<Pet> {

    private String mImageString;

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
        animalStatusTextView.setText(String.valueOf(currentPet.getmStatus()));

        ImageView animalImageView = listItemView.findViewById(R.id.animal_image);
        //animalImageView.setImageResource(currentPet.getmImageResourceId());
        animalImageView.setImageBitmap(currentPet.getmImage());

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

}
