package pl.radoslawgorczyca.animalsheltersosnowiec.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import pl.radoslawgorczyca.animalsheltersosnowiec.types.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.types.User;

/**
 * Created by Ebicom-RG on 06.03.2018.
 */

public class UserUtils {

    public static final String LOG_TAG = UserUtils.class.getSimpleName();

    public UserUtils(){

    }

    public static User fetchUserData(String requestURL, String login){
        URL url = createGetUserUrl(requestURL, login);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractUser(jsonResponse);
    }

    private static URL createGetUserUrl(String requestUrl, String login) {

        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        Uri uri = Uri.parse(requestUrl);
        Uri.Builder builder = uri.buildUpon();
        builder
                .appendQueryParameter("email", login);

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonresponse = "";

        if (url == null) {
            return jsonresponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonresponse = readFromStream(inputStream);
            } else if (urlConnection.getResponseCode() == 408) {
                urlConnection.disconnect();
                urlConnection.connect();
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the pet JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonresponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static User extractUser(String JSONResponse) {

        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding pets to
        User user = null;

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONArray root = new JSONArray(JSONResponse);
            for (int i = 0; i < root.length(); i++) {
                JSONObject petJSON = root.getJSONObject(i);

                long id = petJSON.getLong("idUser");
                String email = petJSON.getString("email");
                String password = petJSON.getString("password");
                String name = petJSON.getString("name");
                String surname = petJSON.getString("surname");

                user = new User(email, name, surname, password);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the pet JSON results", e);
        }

        // Return the list of pets
        return user;
    }

    public static long insertUserToDatabase(String requestUrl, User newUser){

        URL url = createInsertUserUrl(requestUrl, newUser);
        String newUserId = "0";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                Log.e(LOG_TAG, "Success response code: " + urlConnection.getResponseCode());
                newUserId = readFromStream(urlConnection.getInputStream());
            } else if (urlConnection.getResponseCode() == 408) {
                urlConnection.disconnect();
                urlConnection.connect();
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem parsing data to database", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return Integer.decode(newUserId);
    }

    private static URL createInsertUserUrl(String requestUrl, User newUser) {

        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        Uri uri = Uri.parse(requestUrl);
        Uri.Builder builder = uri.buildUpon();
        builder
                .appendQueryParameter("email", newUser.getEmail())
        .appendQueryParameter("password", newUser.getPassword())
        .appendQueryParameter("name", newUser.getName())
        .appendQueryParameter("surname", newUser.getSurname());

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
        }
        return url;

    }
}
