package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import pl.radoslawgorczyca.animalsheltersosnowiec.utils.PetUtils;

/**
 * Created by Radek on 29-Jan-18.
 */

public class PetPostLoader extends AsyncTaskLoader<Pet> {

    private String mUrl;
    private Pet mPet;

    public PetPostLoader(Context context, String url, Pet pet) {
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

        int newPetId = PetUtils.pushDataToDatabase(mUrl, mPet);
        mPet.setmId(newPetId);
        return mPet;
    }
}
