package pl.radoslawgorczyca.animalsheltersosnowiec;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Radek on 24-Jan-18.
 */

public class Pet implements Serializable {

    private double mId;
    private int mSpecies;
    private String mCode;
    private String mName;
    private int mStatus;
    private int mGender;
    private int mHeight;
    private String mBirthYear;
    private String mAcceptanceDate;
    private int mSterilized;
    private String mSummary;
    private String mImageUrl;
    //private byte[] mImageBlob;
    private String mBreed;
    private String mContactNumber;

    public Pet(int mSpecies, String mCode, String mName, int mStatus,
               int mGender, int mHeight, String mBirthYear, String mAcceptanceDate,
               int mSterilized, String mSummary, String mImageUrl, String mBreed, String mContactNumber) {
        this.mSpecies = mSpecies;
        this.mCode = mCode;
        this.mName = mName;
        this.mStatus = mStatus;
        this.mGender = mGender;
        this.mHeight = mHeight;
        this.mBirthYear = mBirthYear;
        this.mAcceptanceDate = mAcceptanceDate;
        this.mSterilized = mSterilized;
        this.mSummary = mSummary;
        this.mImageUrl = mImageUrl;
        this.mBreed = mBreed;
        this.mContactNumber = mContactNumber;

    }

    public Pet(double mId, int mSpecies, String mCode, String mName, int mStatus,
               int mGender, int mHeight, String mBirthYear, String mAcceptanceDate,
               int mSterilized, String mSummary, String mImageUrl, String mBreed, String mContactNumber) {
        this.mId = mId;
        this.mSpecies = mSpecies;
        this.mCode = mCode;
        this.mName = mName;
        this.mStatus = mStatus;
        this.mGender = mGender;
        this.mHeight = mHeight;
        this.mBirthYear = mBirthYear;
        this.mAcceptanceDate = mAcceptanceDate;
        this.mSterilized = mSterilized;
        this.mSummary = mSummary;
        this.mImageUrl = mImageUrl;
        this.mBreed = mBreed;
        this.mContactNumber = mContactNumber;

    }

    public String getmContactNumber() {
        return mContactNumber;
    }

    public void setmContactNumber(String mContactNumber) {
        this.mContactNumber = mContactNumber;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public String getmBirthYear() {
        return mBirthYear;
    }

    public void setmBirthYear(String mBirthYear) {
        this.mBirthYear = mBirthYear;
    }

    public String getmAcceptanceDate() {
        return mAcceptanceDate;
    }

    public void setmAcceptanceDate(String mAcceptanceDate) {
        this.mAcceptanceDate = mAcceptanceDate;
    }

    public int getmSterilized() {
        return mSterilized;
    }

    public void setmSterilized(int mSterilized) {
        this.mSterilized = mSterilized;
    }

    public String getmSummary() {
        return mSummary;
    }

    public void setmSummary(String mSummary) {
        this.mSummary = mSummary;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
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
