package com.example.owner.theearthquakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Owner on 7/13/2017.
 */

public class EarthquakeLoader  extends AsyncTaskLoader<Event>{
    public EarthquakeLoader(Context context) {
        super(context);
    }

    @Override
    public Event loadInBackground() {
        return null;
    }
}
