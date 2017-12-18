package pl.radoslawgorczyca.animalsheltersosnowiec;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DogsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_view, container, false);


        ArrayList<Animal> dogs = new ArrayList<>();

        dogs.add(new Animal("Reksio", "Do adopcji", R.mipmap.dog1));
        dogs.add(new Animal("Burek", "Rezerwacja", R.mipmap.dog2));
        dogs.add(new Animal("Azor", "Do adopcji", R.mipmap.dog3));
        dogs.add(new Animal("Max", "Kwarantanna", R.mipmap.dog4));
        dogs.add(new Animal("Dyzio", "Do adopcji", R.mipmap.dog5));
        dogs.add(new Animal("Lessi", "Do adopcji", R.mipmap.dog6));
        dogs.add(new Animal("Lucek", "Rezerwacja", R.mipmap.dog7));
        dogs.add(new Animal("Pysia", "Do adopcji", R.mipmap.dog8));
        dogs.add(new Animal("Luna", "Kwarantanna", R.mipmap.dog9));
        dogs.add(new Animal("Foxy", "Rezerwacja", R.mipmap.dog10));

        AnimalAdapter adapter =
                new AnimalAdapter(getActivity(), dogs);

        GridView gridView = rootView.findViewById(R.id.grid_view);

        gridView.setAdapter(adapter);

        return rootView;
    }


}
