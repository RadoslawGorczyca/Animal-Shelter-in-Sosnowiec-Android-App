package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.graphics.Bitmap;

/**
 * Created by Radek on 24-Jan-18.
 */

public class Pet {

    private double mId;
    private int mSpecies;
    private int mStatus;
    private String mCode;
    private String mName;
    private int mGender;
    private String mBreed;
    private Bitmap mImage;

    public Pet(double mId, int mSpecies, int mStatus, String mCode, String mName, int mGender, String mBreed, Bitmap mImage) {
        this.mId = mId;
        this.mSpecies = mSpecies;
        this.mStatus = mStatus;
        this.mCode = mCode;
        this.mName = mName;
        this.mGender = mGender;
        this.mBreed = mBreed;
        this.mImage = mImage;
    }

    public Bitmap getmImage() {
        return mImage;
    }

    public void setmImage(Bitmap mImage) {
        this.mImage = mImage;
    }

    public double getmId() {
        return mId;
    }

    public void setmId(double mId) {
        this.mId = mId;
    }

    public int getmSpecies() {
        return mSpecies;
    }

    public void setmSpecies(int mSpecies) {
        this.mSpecies = mSpecies;
    }

    public int getmStatus() {
        return mStatus;
    }

    public void setmStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmGender() {
        return mGender;
    }

    public void setmGender(int mGender) {
        this.mGender = mGender;
    }

    public String getmBreed() {
        return mBreed;
    }

    public void setmBreed(String mBreed) {
        this.mBreed = mBreed;
    }
}
