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

public class GetUserLoader extends AsyncTaskLoader<User> {

    private String mUrl;
    private String mEmail;
    private User mUser;

    public GetUserLoader(@NonNull Context context, String mUrl, String email) {
        super(context);
        this.mUrl = mUrl;
        this.mEmail = email;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public User loadInBackground() {
        mUser = null;
        mUser = UserUtils.fetchUserData(mUrl, mEmail);

        return mUser;
    }
}
