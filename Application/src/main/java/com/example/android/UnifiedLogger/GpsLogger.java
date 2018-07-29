package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by vinay on 7/27/18.
 */

public class GpsLogger implements LocationListener{

    Activity activity;
    LocationManager locationManager;
    FileWriter gpsWriter = null;
    long startupTime; // see here: https://stackoverflow.com/questions/10773505/android-gps-timestamp-vs-sensorevent-timestamp

    public GpsLogger(Activity activity) {
        this.activity = activity;
        TextView textView = activity.findViewById(R.id.textView);
        startupTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();

        if(PermissionsChecker.checkLocationPermissions(activity)) {
            Toast.makeText(activity, "Location Permissions Enabled", Toast.LENGTH_SHORT).show();
        } else {
            PermissionsChecker.requestLocationPermissions(activity);
            if (!PermissionsChecker.checkLocationPermissions(activity)) {
                Toast.makeText(activity, "Location Permissions Not Granted", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(activity, "Location Permissions Granted", Toast.LENGTH_SHORT).show();
        }

        locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        if (textView != null) {
            textView.append("\nGPS Initialized");
        }
    }

    public void subscribeToGPS(){
        TextView textView = (TextView) activity.findViewById(R.id.textView);
        Log.d("GPS", "Subscribed to GPS!");
        try {
            gpsWriter = new FileWriter("GpsLog_" + HelperFunctions.getTimestamp() + ".txt", activity);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            requestLocation();
            if (textView != null) {
                textView.append("\nCreated: " + gpsWriter.filename);
            }

        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public void unsubscribeToGPS() {
        TextView textView = (TextView) activity.findViewById(R.id.textView);

        locationManager.removeUpdates(this);
        try {
            if (gpsWriter != null) {
                gpsWriter.close();
                if (textView != null) {
                    textView.append(String.format(
                            "\nClosed: %s [%d bytes]", gpsWriter.filename, gpsWriter.getFileSize()
                    ));
                }
                gpsWriter = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Just to overload function with default value
    public String requestLocation() {
        return requestLocation(false);
    }

    public String requestLocation(boolean print) {
        String locationResult = "";
        try {
            Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            TextView textView = activity.findViewById(R.id.textView);
//            Toast.makeText(activity, "Location Requested", Toast.LENGTH_SHORT).show();
            Log.d("LocationRequest", "Location Requested");

            String locationGpsResult = "\nGPS Location: " +
                    (locationGps == null ? "Unknown" : HelperFunctions.locationToString(locationGps, startupTime));
            String locationNetworkResult = "\nNetwork Location: "  +
                    (locationNetwork == null ? "Unknown" : HelperFunctions.locationToString(locationNetwork, startupTime));
            locationResult = locationGpsResult + locationNetworkResult;
            if (print && textView != null) {
                textView.append(locationResult);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return locationResult;
    }

    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(activity, "Location Update Received", Toast.LENGTH_SHORT).show();
        try {
//            TextView textView = activity.findViewById(R.id.textView);
//            if (textView != null) {
//                textView.append("\n" + HelperFunctions.locationToString(location));
//            }
            if (gpsWriter != null) {
                gpsWriter.write("\n" + HelperFunctions.locationToString(location, startupTime));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(activity, "Location Status Changed", Toast.LENGTH_SHORT).show();
        TextView textView = activity.findViewById(R.id.textView);
        if (textView != null) {
            textView.append("\nLocation Status Changed: " + s);
        }
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(activity, "Location Provider Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(activity, "Location Provider Disabled", Toast.LENGTH_SHORT).show();
        TextView textView = activity.findViewById(R.id.textView);
        if (textView != null) {
            textView.append("\nLocation Provider Disabled: " + s);
        }
    }
}
