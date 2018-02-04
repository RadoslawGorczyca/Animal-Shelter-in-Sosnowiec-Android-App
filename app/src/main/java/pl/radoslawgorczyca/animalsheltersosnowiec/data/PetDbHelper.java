package pl.radoslawgorczyca.animalsheltersosnowiec.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;

/**
 * Created by Radek on 09-Jan-18.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PetDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "shelterSosnowiec.db";

    private static final int DATABASE_VERSION = 1;

    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_PETS_TABLE =  "CREATE TABLE " + PetEntry.TABLE_NAME + " ("
                + PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PetEntry.COLUMN_PET_SPECIES + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_CODE + " TEXT, "
                + PetEntry.COLUMN_PET_STATUS + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL, "
                + PetEntry.COLUMN_PET_BREED + " TEXT, "
                + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_HEIGHT + " INTEGER NOT NULL, "
                + PetEntry.COLUMN_PET_SUMMARY + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
