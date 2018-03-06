package pl.radoslawgorczyca.animalsheltersosnowiec.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import pl.radoslawgorczyca.animalsheltersosnowiec.types.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.utils.PetUtils;

/**
 * Created by Radek on 29-Jan-18.
 */

public class PetPostLoader extends AsyncTaskLoader<Pet> {

    private String mUrl;
    private Pet mPet;
    private boolean isUpdate;
    private boolean isUploadingImage;

    public PetPostLoader(Context context, String url, Pet pet, boolean isUpdate, boolean isUploadingImage) {
        super(context);
        this.mUrl = url;
        this.mPet = pet;
        this.isUpdate = isUpdate;
        this.isUploadingImage = isUploadingImage;
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

        int updatedRows = 0;
        int newPetId = 0;

        Pet newPet = mPet;
        if (isUpdate) {
            PetUtils.updatePetInDatabase(mUrl, mPet, isUploadingImage);

        }else{
            newPetId = PetUtils.pushDataToDatabase(mUrl, mPet);
            newPet = mPet;
            newPet.setmId(newPetId);
        }

        return newPet;
    }
}
