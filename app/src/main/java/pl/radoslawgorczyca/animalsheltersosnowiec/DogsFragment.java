package pl.radoslawgorczyca.animalsheltersosnowiec;


import android.content.AsyncTaskLoader;
import android.content.Context;

import android.content.Intent;


import android.content.Loader;
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


/*        AsyncTaskLoader<List<Pet>> mAsyncTaskLoader = new AsyncTaskLoader<List<Pet>>() {
            @Override
            public List<Pet> loadInBackground() {
                return PetUtils.fetchPetData(PetContract.SHELTER_REQUEST_URL);
            }
        }*/

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

//    @Override
//    public Loader onCreateLoader(int i, Bundle bundle) {
//        Uri baseUri = Uri.parse(PetContract.SHELTER_REQUEST_URL);
//        return new PetLoader(getActivity(), baseUri.toString());
//    }

    /*    @Override
    public PetLoader onCreateLoader(int id, Bundle args) {
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
    }*/

/*    @Override
    protected void onStartLoading() {
        forceLoad();
    }



    @Override
    public List<Pet> loadInBackground() {
//        if(PetContract.SHELTER_REQUEST_URL == null){
//            return null;
//        }

        return PetUtils.fetchPetData(PetContract.SHELTER_REQUEST_URL);
    }*/


    /*    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                PetEntry._ID,
                PetEntry.COLUMN_PET_NAME,
                PetEntry.COLUMN_PET_STATUS};

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(getActivity(),   // Parent activity context
                PetEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }*/

}
