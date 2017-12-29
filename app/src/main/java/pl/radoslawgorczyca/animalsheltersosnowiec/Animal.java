package pl.radoslawgorczyca.animalsheltersosnowiec;

/**
 * Created by Radek on 25-Nov-17.
 */

public class Animal {

    private String mName;

    private String mStatus;

    private int mImageResourceId;

    public Animal(String name, String status, int imageResourceId){
        mName = name;
        mStatus = status;
        mImageResourceId = imageResourceId;

    }

    public String getName(){
        return mName;
    }

    public String getStatus(){
        return mStatus;
    }

    public int getImageResourceId(){
        return mImageResourceId;
    }


}
