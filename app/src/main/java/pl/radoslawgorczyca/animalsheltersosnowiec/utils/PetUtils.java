package pl.radoslawgorczyca.animalsheltersosnowiec.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import pl.radoslawgorczyca.animalsheltersosnowiec.Animal;
import pl.radoslawgorczyca.animalsheltersosnowiec.Pet;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract;
import pl.radoslawgorczyca.animalsheltersosnowiec.data.PetContract.PetEntry;

/**
 * Created by Radek on 24-Jan-18.
 */

public final class PetUtils {

    public static final String LOG_TAG = PetUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link PetUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private PetUtils() {
    }


    public static List<Pet> fetchPetData(String requestURL) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        URL url = createUrl(requestURL);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractPets(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
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

    /**
     * Return a list of {@link Pet} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<Pet> extractPets(String JSONResponse) {

        if (TextUtils.isEmpty(JSONResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding pets to
        List<Pet> pets = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONArray root = new JSONArray(JSONResponse);
            for (int i = 0; i < root.length(); i++) {
                JSONObject petJSON = root.getJSONObject(i);

                double id = petJSON.getDouble("idPet");
                int species = petJSON.getInt("species");
                String code = petJSON.getString("code");
                String name = petJSON.getString("name");
                int status = petJSON.getInt("status");
                int gender = petJSON.getInt("gender");
                int height = petJSON.getInt("height");
                String birthYear = petJSON.getString("birthYear");
                String acceptanceDate = petJSON.getString("acceptanceDate");
                int sterilized = petJSON.getInt("sterilized");
                String summary = petJSON.getString("summary");
                String imageUrl = petJSON.getString("image");
                byte[] imageBlob = LoadImageFromWebOperations(imageUrl);
                String breed = petJSON.getString("breed");
                String contactNumber = petJSON.getString("contactNumber");

                Pet pet = new Pet(id, species, code, name, status, gender, height, birthYear,
                        acceptanceDate, sterilized, summary, imageBlob, breed, contactNumber);
                pets.add(pet);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the pet JSON results", e);
        }

        // Return the list of pets
        return pets;
    }

    public static void pushDataToDatabase(String requestUrl, Pet newPet){
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();
        URL url = createUrlWithParams(requestUrl, newPet);

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                Log.e(LOG_TAG, "Success response code: " + urlConnection.getResponseCode());
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

    }

    private static URL createUrlWithParams(String requestUrl, Pet pet){

        //TODO Popraw Uri builder dla dodawania zwierząt do bazy
        Uri uri = Uri.parse(requestUrl);
        Uri.Builder builder = uri.buildUpon();
        builder
                .appendQueryParameter("species", String.valueOf(pet.getmSpecies()))
                .appendQueryParameter("status", String.valueOf(pet.getmStatus()))
                .appendQueryParameter("code", pet.getmCode())
                .appendQueryParameter("name", pet.getmName())
                .appendQueryParameter("gender", String.valueOf(pet.getmGender()))
                .appendQueryParameter("breed", pet.getmBreed());

        URL url = null;
        try{
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    private static byte[] LoadImageFromWebOperations(String url) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
            //return bitmap;

            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
            return blob.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
