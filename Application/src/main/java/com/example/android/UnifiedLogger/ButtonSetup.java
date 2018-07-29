package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.location.LocationManager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * Created by vinay on 7/2/18.
 */

public class ButtonSetup {

    static public void setup(final View view) {

        Log.d("ButtonSetup", "Setup started");

        // Setup Location Listener:
//        MyLocationListener listener;

        // Get textview:
        final TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText("Unified Logger:");
        textView.setMovementMethod(new ScrollingMovementMethod());
        Log.d("ButtonSetup", "TextView Created: " + textView.toString());

        // Get buttons:
//        final Button startButton = (Button) activity.findViewById(R.id.button);
//        final Button stopButton = (Button) activity.findViewById(R.id.button2);
//        final ImageButton gpsLocationButton = (ImageButton) activity.findViewById(R.id.imageButton);
        Button clearLogButton = (Button) view.findViewById(R.id.button3);
        ToggleButton hideLogButton = (ToggleButton) view.findViewById(R.id.toggleButton);

        // Set Callbacks:
//        startButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.append("\nStart button clicked!");
//                try {
//                    activity.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, activity.locationListener);
//                    startButton.setBackgroundColor(0xFF00FF00);
//                    stopButton.setBackgroundColor(0xFFAAAAAA);
//
//                } catch (SecurityException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        stopButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                textView.append("\nStop button clicked!");
//                activity.locationManager.removeUpdates(activity.locationListener);
//                startButton.setBackgroundColor(0xFFAAAAAA);
//                stopButton.setBackgroundColor(0xFFFF0000);
//            }
//        });
//        gpsLocationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(activity.getApplicationContext(), "Location Requested", Toast.LENGTH_SHORT).show();
//                activity.requestLocation();
//            }
//        });
        clearLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Cleared Log", Toast.LENGTH_SHORT).show();
                Log.d("ClearButton", "Cleared Log");
                textView.setText("Unified Logger");
            }
        });
        hideLogButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    textView.setVisibility(View.INVISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }
        });
        // Check permissions: TODO Move to GPS Setup
//        Activity activity = HelperFunctions.getActivity(view);
//        PermissionsChecker.requestLocationPermissions(activity);
//        Log.d("ButtonSetup", "Activity: " + activity.toString());
//        if (PermissionsChecker.checkLocationPermissions(activity)) {
//            textView.append("\nLocation Permissions Enabled");
//        } else {
//            textView.append("\nLocation Permissions Disabled");
//            textView.append("\nRequesting Location Permissions...........");
//            PermissionsChecker.requestLocationPermissions(activity);
//
//            if (PermissionsChecker.checkLocationPermissions(activity)) {
//                textView.append("\nLocation Permissions Granted!");
//            } else {
//                textView.append("\nLocation Permissions Denied...please enable under settings");
//                activity.finish();
//            }
//        }
    }

}
