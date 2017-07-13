package com.example.owner.theearthquakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Owner on 7/13/2017.
 */

public class EarthquakeLoader  extends AsyncTaskLoader<List<Earthquake>>{

    /** Query URL */
    private String mUrl;



    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }



    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Earthquake> earthquakes = Utils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
