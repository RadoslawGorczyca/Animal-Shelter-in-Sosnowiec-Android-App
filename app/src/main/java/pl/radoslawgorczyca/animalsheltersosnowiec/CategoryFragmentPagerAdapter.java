package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Radek on 17-Dec-17.
 */

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {

    public CategoryFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DogsFragment();
        } else {
            return new CatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }




    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Psy";
        } else {
            return "Koty";
        }
    }


}
