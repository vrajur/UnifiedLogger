package com.example.android.UnifiedLogger;

import android.location.Location;
import android.location.LocationManager;
import android.widget.TextView;

/**
 * Created by vinay on 7/27/18.
 */

public class GpsLogger {

    LocationManager locationManager;
    MyLocationListener locationListener;

//    public void requestLocation() {
//        try {
//            Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            TextView textView = (TextView) findViewById(R.id.textView);
//
//            if ((locationGps == null) && (locationNetwork == null)) {
//                textView.append("\nLast Location Unknown");
//            } else if ((locationGps != null) && (locationNetwork == null)) {
//                textView.append("\nGPS Location: " + locationToString(locationGps));
//                textView.append("\nNetwork Location: Unknown");
//            } else if ((locationGps == null) && (locationNetwork != null)) {
//                textView.append("\nGPS Location: Unknown");
//                textView.append("\nNetwork Location: " +  locationToString(locationNetwork));
//            } else if ((locationGps != null) && (locationNetwork != null)) {
//                textView.append("\nGPS Location: " + locationToString(locationGps));
//                textView.append("\nNetwork Location: "  + locationToString(locationNetwork));
//            }
//        } catch (SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private String locationToString(Location loc) {
//        String output = String.format("Time: %d Lat: %f Lon: %f Alt: %f Speed: %f Bearing: %f",
//                loc.getTime(), loc.getLatitude(), loc.getLongitude(), loc.getAltitude(), loc.getSpeed(), loc.getBearing());
//        return output;
//    }
}
