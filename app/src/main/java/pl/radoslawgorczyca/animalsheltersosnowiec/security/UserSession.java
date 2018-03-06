package pl.radoslawgorczyca.animalsheltersosnowiec.security;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;

import pl.radoslawgorczyca.animalsheltersosnowiec.activities.LoggingActivity;
import pl.radoslawgorczyca.animalsheltersosnowiec.activities.MainActivity;

/**
 * Created by Ebicom-RG on 06.03.2018.
 */

public class UserSession {
    // Shared Preferences reference
    private SharedPreferences pref;

    // Editor reference for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Shared preferences mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // password
    public static final String KEY_PASSWORD = "password";

    // Constructor
    public UserSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String uMail, String uPassword, String uName, String uSurname){
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing name in preferences
        editor.putString(KEY_EMAIL, uMail);

        // Storing email in preferences
        editor.putString(KEY_PASSWORD,  uPassword);

        editor.putString(KEY_NAME,  uName);

        editor.putString(KEY_SURNAME, uSurname);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isUserLoggedIn()){

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoggingActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    /*public HashMap<String, String> getUserDetails(){

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        user.put(KEY_SURNAME, pref.getString(KEY_SURNAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }*/

    /**
     * Clear session details
     * */
    public void logoutUser(){

        // Clearing all user data from Shared Preferences
        String email = pref.getString(KEY_EMAIL, null);
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(_context, MainActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        editor.putString(email, null);
        editor.commit();

        // Staring Login Activity
        _context.startActivity(i);
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
