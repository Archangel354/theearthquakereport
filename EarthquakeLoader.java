package com.example.owner.theearthquakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

/**
 * Created by Owner on 7/13/2017.
 */


import java.util.List;

/**
 * Loads a list of earthquakes by using an AsyncTask to perform the
 * network request to the given URL.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;
    public String[] urls;


    /**
     * Constructs a new {@link EarthquakeLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }



    @Override
    protected void onStartLoading() {
        forceLoad();
    }



    /**
     * This is on a background thread.
     */
    @Override
    public List<Earthquake> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        // List<Earthquake> earthquakes = Utils.fetchEarthquakeData(mUrl);
        List<Earthquake> earthquakes = Utils.fetchEarthquakeData(mUrl);
        Log.i("LOG","The array list earthquakes is: " + earthquakes);
        return earthquakes;
    }
}
