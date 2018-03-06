package pl.radoslawgorczyca.animalsheltersosnowiec.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import pl.radoslawgorczyca.animalsheltersosnowiec.types.User;
import pl.radoslawgorczyca.animalsheltersosnowiec.utils.UserUtils;

/**
 * Created by Ebicom-RG on 06.03.2018.
 */

public class NewUserLoader extends AsyncTaskLoader<User> {

    private String mUrl;
    private User newUser;

    public NewUserLoader(@NonNull Context context, String mUrl, User newUser) {
        super(context);
        this.mUrl = mUrl;
        this.newUser = newUser;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public User loadInBackground() {
        User addedUser = newUser;
        addedUser.setIdUser(0);
        long newId = UserUtils.insertUserToDatabase(mUrl, newUser);
        if(newId != 0){
            addedUser.setIdUser(newId);
        }

        return addedUser;
    }
}
