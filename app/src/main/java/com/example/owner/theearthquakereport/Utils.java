package com.example.owner.theearthquakereport;

/**
 * Created by Owner on 7/6/2017.
 */

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Utility class with methods to help perform the HTTP request and
 * parse the response.
 */
public final class Utils {

    /** Tag for the log messages */
    public static final String LOG_TAG = Utils.class.getSimpleName();

    // Create an empty ArrayList that we can start adding earthquakes to
    static ArrayList<Earthquake> earthquakes = new ArrayList<>();

    /**
     * Query the USGS dataset and return an {@link Event} object to represent a single earthquake.
     */
    public static Event fetchEarthquakeData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        Event earthquake = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return earthquake;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
           // Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
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
     * Return an {@link Event} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    private static Event extractFeatureFromJson(String earthquakeJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(earthquakeJSON);
            JSONArray featureArray = baseJsonResponse.getJSONArray("features");

            for (int i = 0;i < featureArray.length();i++){
                JSONObject currentEarthquake = featureArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                //String magnitude = "13.0";
                String offsetLocation = "10 mi south of";
                String city = "Irmo";
                String location = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                Log.i("LOG","url is " + url);

                Date mDate = new Date(time);

                Date mTime = new Date(time);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
                String dateToDisplay = dateFormatter.format(mDate);

                SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
                String timeToDisplay = timeFormatter.format(mTime);

                offsetLocation = OffSetLocationCreator(location);
                city = CityCreator(location);

                Earthquake mEarthquake = new Earthquake(magnitude, offsetLocation, city, dateToDisplay, timeToDisplay, url);
                earthquakes.add(mEarthquake);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

    static String OffSetLocationCreator(String location){
        String offSetLocation = "";
        int ofIndex;

        ofIndex = location.indexOf("of");
        offSetLocation = location.substring(0, ofIndex + 2);
        if (ofIndex == -1)
        {
            offSetLocation = "Near the ";
        }
        // Log.i("LOG","offSetLocation is " + offSetLocation);
        return offSetLocation;
    }

    static String CityCreator(String location){
        String city = location;
        int ofIndex;

        ofIndex = location.indexOf("of");
        if (!(ofIndex == -1))
        {
            city = location.substring( ofIndex + 3, location.length());
        }
        Log.i("LOG","city is " + city);
        return city;
    }

    //ArrayList<String> arrList = new ArrayList<String>();

    public static ArrayList<Earthquake> getArrList() {
        return earthquakes;
    }

}
