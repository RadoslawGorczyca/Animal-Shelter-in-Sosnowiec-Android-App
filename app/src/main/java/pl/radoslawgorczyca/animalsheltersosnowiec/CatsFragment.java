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
public class CatsFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_view, container, false);


        ArrayList<Animal> cats = new ArrayList<>();

        cats.add(new Animal("Słonik", "Rezerwacja", R.mipmap.cat1));
        cats.add(new Animal("Cypis", "Rezerwacja", R.mipmap.cat2));
        cats.add(new Animal("Darek", "Do adopcji", R.mipmap.cat3));
        cats.add(new Animal("Niełapek", "Do adopcji", R.mipmap.cat4));
        cats.add(new Animal("Henry", "Do adopcji", R.mipmap.cat5));
        cats.add(new Animal("Tessa", "Kwarantanna", R.mipmap.cat6));
        cats.add(new Animal("Majka", "Rezerwacja", R.mipmap.cat7));
        cats.add(new Animal("Tereska", "Do adopcji", R.mipmap.cat8));
        cats.add(new Animal("Jami", "Do adopcji", R.mipmap.cat9));
        cats.add(new Animal("Emilka", "Do adopcji", R.mipmap.cat10));

        AnimalAdapter adapter =
                new AnimalAdapter(getActivity(), cats);

        GridView gridView = rootView.findViewById(R.id.grid_view);

        gridView.setAdapter(adapter);

        return rootView;
    }


}
