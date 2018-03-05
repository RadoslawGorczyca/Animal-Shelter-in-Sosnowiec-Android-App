package pl.radoslawgorczyca.animalsheltersosnowiec.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import pl.radoslawgorczyca.animalsheltersosnowiec.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.utils.PetUtils;

/**
 * Created by Radek on 29-Jan-18.
 */

public class PetDeleteLoader extends AsyncTaskLoader<Pet> {

    private String mUrl;
    private Pet mPet;

    public PetDeleteLoader(Context context, String url, Pet pet) {
        super(context);
        this.mUrl = url;
        this.mPet = pet;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Pet loadInBackground() {
        if(mUrl == null){
            return null;
        }
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();


        int rowsDeleted = PetUtils.deletePet(mUrl, mPet);
        if(rowsDeleted == 0){
            mPet = null;
        }else{
            mPet.setmId(0);
        }
        return mPet;
    }
}
