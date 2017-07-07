package com.example.owner.theearthquakereport;

/**
 * Created by Owner on 7/6/2017.
 */



// Earthquake represents a single set of data for a location.
// Each object has 3 propertiesL magnitude, city, and date of the earthquake.

public class Earthquake {

    // magnitude of the earthquake for the particular location
    private Double mMagnitude;

    // Offset location from the nearest city of the earthquake
    private String mOffsetLocation;

    // location of the earthquake
    private String mCity;

    // What date the earthquake occured
    private String mDate;

    // What time the earthquake occured
    private String mTime;

    // The webstie of the earthquake
    private String mUrl;

    public Earthquake(Double Magnitude, String OffsetLocation, String City, String Date, String Time, String Url) {
        mMagnitude = Magnitude;
        mOffsetLocation = OffsetLocation;
        mCity = City;
        mDate = Date;
        mTime = Time;
        mUrl = Url;
    }

    public Double getmMagnitude() {
        return mMagnitude;
    }

    public String getmOffsetLocation() { return mOffsetLocation;  }

    public String getmCity() {
        return mCity;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmUrl() { return mUrl; }
}

