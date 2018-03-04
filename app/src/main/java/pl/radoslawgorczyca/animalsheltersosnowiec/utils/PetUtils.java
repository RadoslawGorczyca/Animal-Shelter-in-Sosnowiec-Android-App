package pl.radoslawgorczyca.animalsheltersosnowiec.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Debug;
import android.text.TextUtils;
import android.util.Log;

import org.jibble.simpleftp.SimpleFTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

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
        if (android.os.Debug.isDebuggerConnected())
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
                //byte[] imageBlob = LoadImageFromWebOperations(imageUrl);
                String breed = petJSON.getString("breed");
                String contactNumber = petJSON.getString("contactNumber");

                Pet pet = new Pet(id, species, code, name, status, gender, height, birthYear,
                        acceptanceDate, sterilized, summary, imageUrl, breed, contactNumber);
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

    public static int pushDataToDatabase(String requestUrl, Pet newPet) {
        if (android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        newPet = pushImageToFTP(newPet);
        URL url = createUrlWithParams(requestUrl, newPet);
        String newPetId = "0";

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                Log.e(LOG_TAG, "Success response code: " + urlConnection.getResponseCode());
                newPetId = readFromStream(urlConnection.getInputStream());
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

        return Integer.decode(newPetId);

    }

    public static String stripNonDigits(
            final CharSequence input /* inspired by seh's comment */) {
        final StringBuilder sb = new StringBuilder(
                input.length() /* also inspired by seh's comment */);
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private static Pet pushImageToFTP(Pet pet) {

        if (Debug.isDebuggerConnected())
            Debug.waitForDebugger();

        if(pet.getmImageUrl().isEmpty() || pet.getmImageUrl().equals("")){
            if(pet.getmSpecies() == PetEntry.SPECIES_DOG){
                pet.setmImageUrl(PetContract.FTP_PHOTO_URL + PetContract.FTP_NO_PHOTO_DOG);
            } else{
                pet.setmImageUrl(PetContract.FTP_PHOTO_URL + PetContract.FTP_NO_PHOTO_CAT);
            }
            return pet;
        }

        String mNewImageUrl;
        boolean folder;

        SimpleFTP ftp = new SimpleFTP();
        try {
            // Connect to an FTP server on port 21.
            ftp.connect(PetContract.FTP_URL, PetContract.FTP_PORT, PetContract.FTP_USERNAME, PetContract.FTP_PASSWORD);

            // Set binary mode.
            ftp.bin();

            ftp.cwd("gorczyca");
            ftp.cwd("schronisko-sosnowiec");
            ftp.cwd("photos");
            // Change to a new working directory on the FTP server.

            File file = new File(new URI(pet.getmImageUrl()));
            // Upload some files.
            ftp.stor(file);
            pet.setmImageUrl(PetContract.FTP_PHOTO_URL + file.getName());

            // Quit from the FTP server.
            ftp.disconnect();
            return pet;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return pet;

    }

    private static URL createUrlWithParams(String requestUrl, Pet pet) {

        Uri uri = Uri.parse(requestUrl);
        Uri.Builder builder = uri.buildUpon();
        builder
                .appendQueryParameter("species", String.valueOf(pet.getmSpecies()))
                .appendQueryParameter("status", String.valueOf(pet.getmStatus()))
                .appendQueryParameter("code", pet.getmCode())
                .appendQueryParameter("name", pet.getmName())
                .appendQueryParameter("status", String.valueOf(pet.getmStatus()))
                .appendQueryParameter("gender", String.valueOf(pet.getmGender()))
                .appendQueryParameter("height", String.valueOf(pet.getmHeight()))
                .appendQueryParameter("birthYear", pet.getmBirthYear())
                .appendQueryParameter("acceptanceDate", pet.getmAcceptanceDate())
                .appendQueryParameter("sterilized", String.valueOf(pet.getmSterilized()))
                .appendQueryParameter("summary", pet.getmSummary())
                .appendQueryParameter("image", pet.getmImageUrl())
                .appendQueryParameter("breed", pet.getmBreed())
                .appendQueryParameter("contactNumber", pet.getmContactNumber());

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    //TODO finish this
    /*public static void deletePet(String requestUrl, Pet pet){

        Uri uri = Uri.parse(requestUrl);
        Uri.Builder builder = uri.buildUpon();
        builder
                .appendQueryParameter("id", String.valueOf(pet.getmId()));

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                Log.e(LOG_TAG, "Success response code: " + urlConnection.getResponseCode());
                newPetId = readFromStream(urlConnection.getInputStream());
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
    }*/


    private static byte[] LoadImageFromWebOperations(String url) {

        InputStream in = null;
        try {
            final int IMAGE_MAX_SIZE = 200000; // 0.1MP
            in = (InputStream) new URL(url).getContent();

            // Decode image size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();


            int scale = 1;
            while ((options.outWidth * options.outHeight) * (1 / Math.pow(scale, 2)) >
                    IMAGE_MAX_SIZE) {
                scale++;
            }

            Bitmap resultBitmap = null;
            in = (InputStream) new URL(url).getContent();
            if (scale > 1) {
                scale--;
                // scale to max possible inSampleSize that still yields an image
                // larger than target
                options = new BitmapFactory.Options();
                options.inSampleSize = scale;
                resultBitmap = BitmapFactory.decodeStream(in, null, options);

                // resize to desired dimensions
                int height = resultBitmap.getHeight();
                int width = resultBitmap.getWidth();

                double y = Math.sqrt(IMAGE_MAX_SIZE
                        / (((double) width) / height));
                double x = (y / height) * width;

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(resultBitmap, (int) x,
                        (int) y, true);
                resultBitmap.recycle();
                resultBitmap = scaledBitmap;

                System.gc();
            } else {
                resultBitmap = BitmapFactory.decodeStream(in);
            }
            in.close();

            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            resultBitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
            return blob.toByteArray();

        } catch (IOException e) {
            Log.e("PetUtils.class", e.getMessage(), e);
            return null;
        }
    }

}
