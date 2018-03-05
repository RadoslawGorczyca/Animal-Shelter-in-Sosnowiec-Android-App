package pl.radoslawgorczyca.animalsheltersosnowiec.loaders;

import android.content.Context;

import java.util.List;

import pl.radoslawgorczyca.animalsheltersosnowiec.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.utils.PetUtils;

/**
 * Created by Radek on 24-Jan-18.
 */

public class PetLoader extends android.support.v4.content.AsyncTaskLoader<List<Pet>> {

    private static final String LOG_TAG = PetLoader.class.getName();

    private String mUrl;



    public PetLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Pet> loadInBackground() {
        if(mUrl == null){
            return null;
        }

        return PetUtils.fetchPetData(mUrl);
    }
}
