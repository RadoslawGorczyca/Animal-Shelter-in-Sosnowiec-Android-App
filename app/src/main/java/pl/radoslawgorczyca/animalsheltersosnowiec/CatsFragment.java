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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;
import pl.radoslawgorczyca.animalsheltersosnowiec.utils.PetUtils;
import pl.radoslawgorczyca.animalsheltersosnowiec.PetLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Pet>> {

    private static final int PET_LOADER = 1;

    private PetAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_view, container, false);

        GridView petGridView = rootView.findViewById(R.id.grid_view);
        mAdapter = new PetAdapter(getActivity(), new ArrayList<Pet>());
        petGridView.setAdapter(mAdapter);
        getLoaderManager().initLoader(PET_LOADER, null, this);

        petGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Pet selectedPet = mAdapter.getItem(i);

                Intent intent = new Intent(getActivity(), SinglePetActivity.class);
                intent.putExtra("currentPet", selectedPet);
                startActivity(intent);
            }
        });


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


            for (int i = 0; i < pets.size(); i++) {
                if (pets.get(i).getmSpecies() == PetEntry.SPECIES_CAT) {
                    mAdapter.add(pets.get(i));
                }
            }
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Pet>> loader) {
        mAdapter.clear();
    }

}
