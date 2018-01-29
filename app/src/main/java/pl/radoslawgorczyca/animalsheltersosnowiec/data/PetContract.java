package pl.radoslawgorczyca.animalsheltersosnowiec.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Radek on 09-Jan-18.
 */

public class PetContract {

    private PetContract() {
    }

    public static final int PET_LOADER_ID = 1;

    public static final String SHELTER_REQUEST_URL =
            "http://gorczyca.org/schronisko-sosnowiec/getJson.php";




    public static final String CONTENT_AUTHORITY = "pl.radoslawgorczyca.animalsheltersosnowiec";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_PETS = "pets";

    public static final class PetEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PETS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PETS;

        public final static String TABLE_NAME = "pets";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PET_SPECIES = "species";

        public final static String COLUMN_PET_CODE = "code";

        public final static String COLUMN_PET_STATUS = "status";

        public final static String COLUMN_PET_NAME = "name";

        public final static String COLUMN_PET_BREED = "breed";

        public final static String COLUMN_PET_GENDER = "gender";

        public final static String COLUMN_PET_HEIGHT = "height";

        public final static String COLUMN_PET_SUMMARY = "summary";

        public static final int SPECIES_DOG = 1;
        public static final int SPECIES_CAT = 2;

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        public static final int STATUS_ADOPTABLE = 1;
        public static final int STATUS_QUARANTINE = 2;
        public static final int STATUS_BOOKED = 3;

        public static final int HEIGHT_SMALL = 1;
        public static final int HEIGHT_MEDIUM = 2;
        public static final int HEIGHT_BIG = 3;

        public static boolean isValidSpecies(int species) {
            return species == SPECIES_DOG || species == SPECIES_CAT;
        }

        public static boolean isValidGender(int gender) {
            return gender == GENDER_UNKNOWN || gender == GENDER_MALE || gender == GENDER_FEMALE;
        }

        public static boolean isValidStatus(int status) {
            return status == STATUS_ADOPTABLE || status == STATUS_QUARANTINE || status == STATUS_BOOKED;
        }

        public static boolean isValidHeight(int height) {
            return height == HEIGHT_SMALL || height == HEIGHT_MEDIUM || height == HEIGHT_BIG;
        }
    }
}
