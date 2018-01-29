package pl.radoslawgorczyca.animalsheltersosnowiec;


import android.content.AsyncTaskLoader;
import android.content.Context;

import android.content.Intent;


import android.content.Loader;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.content.CursorLoader;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.CursorAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;
import pl.radoslawgorczyca.animalsheltersosnowiec.utils.PetUtils;
import pl.radoslawgorczyca.animalsheltersosnowiec.PetLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class DogsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Pet>> {

    private static final int PET_LOADER = 0;

    private PetAdapter mAdapter;

    PetCursorAdapter mCursorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_view, container, false);

/*

        final ArrayList<Animal> dogs = new ArrayList<>();

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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Animal selectedDog = dogs.get(i);

                Intent intent = new Intent(getActivity(), SingleAnimalActivity.class);
                intent.putExtra("name", selectedDog.getName());
                intent.putExtra("status", selectedDog.getStatus());
                intent.putExtra("imageId", selectedDog.getImageResourceId());
                startActivity(intent);
            }
        });
*/

//        GridView petGridView = rootView.findViewById(R.id.grid_view);
//        mCursorAdapter = new PetCursorAdapter(getActivity(), null);
//        petGridView.setAdapter(mCursorAdapter);
//        getLoaderManager().initLoader(PET_LOADER, null, this);

        GridView petGridView = rootView.findViewById(R.id.grid_view);
        mAdapter = new PetAdapter(getActivity(), new ArrayList<Pet>());
        petGridView.setAdapter(mAdapter);
        getLoaderManager().initLoader(PET_LOADER, null, this);


        return rootView;
    }


    @Override
    public android.support.v4.content.Loader<List<Pet>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(PetContract.SHELTER_REQUEST_URL);
        return new PetLoader(getActivity(), baseUri.toString());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Pet>> loader, List<Pet> pets) {
        mAdapter.clear();

        if (pets != null && !pets.isEmpty()) {
            mAdapter.addAll(pets);
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Pet>> loader) {
        mAdapter.clear();
    }

}
