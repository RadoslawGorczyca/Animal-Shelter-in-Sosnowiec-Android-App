package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Radek on 25-Nov-17.
 */

public class AnimalAdapter extends ArrayAdapter<Animal> {


    public AnimalAdapter(Activity context, ArrayList<Animal> animals) {

        super(context, 0, animals);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grid_view_item, parent, false);
        }


        Animal currentAnimal = getItem(position);

        TextView animalNameTextView = listItemView.findViewById(R.id.animal_name);
        animalNameTextView.setText(currentAnimal.getName());

        TextView animalStatusTextView = listItemView.findViewById(R.id.animal_status);
        animalStatusTextView.setText(currentAnimal.getStatus());

        ImageView animalImageView = listItemView.findViewById(R.id.animal_image);
        animalImageView.setImageResource(currentAnimal.getImageResourceId());

        ImageView statusIconView = listItemView.findViewById(R.id.status_icon);
        if(currentAnimal.getStatus().equals("Kwarantanna")){
            statusIconView.setImageResource(R.mipmap.orange_circle);
        }else if(currentAnimal.getStatus().equals("Rezerwacja")){
            statusIconView.setImageResource(R.mipmap.red_circle);
        }else{
            statusIconView.setImageResource(R.mipmap.green_circle);
        }

        return listItemView;


    }
}
