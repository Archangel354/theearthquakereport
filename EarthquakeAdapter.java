package com.example.owner.theearthquakereport;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Owner on 7/6/2017.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakeRecords) {
        super(context,0, earthquakeRecords);
    }


    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        // Get the EarthquakeRecord object located at this "position" in the list
        Earthquake currentEarthquakeRecord = getItem(position);

        TextView txtMagnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

        // Format the magnitude to show 1 decimal place
        String formattedMagnitude = formatMagnitude(currentEarthquakeRecord.getmMagnitude());
        // Display the magnitude of the current earthquake in that TextView
        txtMagnitudeView.setText(formattedMagnitude);


        // Find the TextView in the list_item.xml layout with the ID txtMagnitude
        //TextView textMagnitude = (TextView) listItemView.findViewById(txtMagnitude);
        // Get the magnitude from the current EarthquakeRecord object and
        // set this text on the magnitude TextView
        txtMagnitudeView.setText(String.valueOf(currentEarthquakeRecord.getmMagnitude()));

        // Find the TextView in the list_item.xml layout with the ID txtOffsetLocation
        TextView textOffsetLocation = (TextView) listItemView.findViewById(R.id.location_offset);
        // Get the city from the current EarthquakeRecord object and
        // set this text on the city TextView
        textOffsetLocation.setText(currentEarthquakeRecord.getmOffsetLocation());

        // Find the TextView in the list_item.xml layout with the ID txtCity
        TextView textCity = (TextView) listItemView.findViewById(R.id.primary_location);
        // Get the city from the current EarthquakeRecord object and
        // set this text on the city TextView
        textCity.setText(currentEarthquakeRecord.getmCity());

        // Find the TextView in the list_item.xml layout with the ID txtDate
        TextView textDate = (TextView) listItemView.findViewById(R.id.date);
        // Get the city from the current EarthquakeRecord object and
        // set this text on the city TextView
        textDate.setText(currentEarthquakeRecord.getmDate());

        // Find the TextView in the list_item.xml layout with the ID txtTime
        TextView textTime = (TextView) listItemView.findViewById(R.id.time);
        // Get the city from the current EarthquakeRecord object and
        // set this text on the city TextView
        textTime.setText(currentEarthquakeRecord.getmTime());

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) txtMagnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquakeRecord.getmMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        return listItemView;

    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

// Return the whole list item layout (containing 2 TextViews and an ImageView)
// so that it can be shown in the ListView

}

