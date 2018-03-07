package pl.radoslawgorczyca.animalsheltersosnowiec.fragments;


import android.content.Intent;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.radoslawgorczyca.animalsheltersosnowiec.types.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.adapters.PetAdapter;
import pl.radoslawgorczyca.animalsheltersosnowiec.loaders.PetLoader;
import pl.radoslawgorczyca.animalsheltersosnowiec.R;
import pl.radoslawgorczyca.animalsheltersosnowiec.activities.SinglePetActivity;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Pet>> {

    private static final int PET_LOADER = 1;

    private SwipeRefreshLayout swipeContainer;

    private PetAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private ProgressBar mProgressIndicator;
    GridView petGridView;
    public static Parcelable state;

    private boolean isLoaderInitialized = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_view, container, false);

        petGridView = rootView.findViewById(R.id.grid_view);
        mAdapter = new PetAdapter(getActivity(), new ArrayList<Pet>());
        petGridView.setAdapter(mAdapter);

        petGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Pet selectedPet = mAdapter.getItem(i);

                state = petGridView.onSaveInstanceState();
                Intent intent = new Intent(getActivity(), SinglePetActivity.class);
                intent.putExtra("currentPet", selectedPet);
                startActivity(intent);
            }
        });

        mEmptyStateTextView = rootView.findViewById(R.id.empty_state);
        swipeContainer = rootView.findViewById(R.id.swipe_container);
        petGridView.setEmptyView(mEmptyStateTextView);

        swipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mEmptyStateTextView.setText("");
                if(!isLoaderInitialized){
                    getLoaderManager().initLoader(PET_LOADER, null, CatsFragment.this);
                    isLoaderInitialized = true;
                }else {
                    getLoaderManager().restartLoader(PET_LOADER, null, CatsFragment.this);
                }
            }
        });

        mProgressIndicator = rootView.findViewById(R.id.progress_indicator);

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            getLoaderManager().initLoader(PET_LOADER, null, this);
        }else {
            mProgressIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (petGridView != null && state != null) {
            petGridView.onRestoreInstanceState(state);
        }

    }


    @Override
    public android.support.v4.content.Loader<List<Pet>> onCreateLoader(int id, Bundle args) {
        isLoaderInitialized = true;
        Uri baseUri = Uri.parse(PetContract.SHELTER_REQUEST_URL);
        return new PetLoader(getActivity(), baseUri.toString());
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<List<Pet>> loader, List<Pet> pets) {
        mProgressIndicator.setVisibility(View.GONE);
        swipeContainer.setRefreshing(false);
        mAdapter.clear();

        if (pets != null && !pets.isEmpty()) {


            for (int i = 0; i < pets.size(); i++) {
                if (pets.get(i).getmSpecies() == PetEntry.SPECIES_CAT) {
                    mAdapter.add(pets.get(i));
                }
            }
        }

        if (petGridView != null && state != null) {
            petGridView.onRestoreInstanceState(state);
        }

        mEmptyStateTextView.setText(R.string.no_pets_found);
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<List<Pet>> loader) {
        mAdapter.clear();
    }

}
