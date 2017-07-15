package com.example.owner.theearthquakereport;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an AsyncTask to perform the HTTP request to the given URL on a background
        // thread.  when the result is received on the main UI thread, then update the UI.
        DownloadFilesTask task = new DownloadFilesTask();
        task.execute(USGS_REQUEST_URL);
    }



    private class DownloadFilesTask extends AsyncTask<String, Void, List> {

        protected List doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response.
            List result = Utils.fetchEarthquakeData(urls[0]);
            Log.i("LOG","JSON string is: " + result);
            return result;
        }

        @Override
        protected void onPostExecute(List result) {
            // If there is no result, do nothing.

            // Get the list of earthquakes from QueryUtils
            ArrayList<Earthquake> earthquakes = Utils.getArrList();

            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link CustomAdapter} of earthquakes
            final EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(MainActivity.this, earthquakes);

            // Set the adapter on the {@link ListView} so the list can be populated in the user interface
            earthquakeListView.setAdapter(earthquakeAdapter);
            Log.i("LOG","...AND WE'RE DONE!!!!");
            if (result == null) {
                return;
            }

        }

    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL

        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        // TODO: Update the UI with the result
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.
    }
}
