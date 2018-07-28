//package com.example.android.UnifiedLogger;
//
//import android.app.Activity;
//import android.location.Location;
//import android.location.LocationListener;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
///**
// * Created by vinay on 7/2/18.
// */
//
//public class MyLocationListener implements LocationListener {
//
//    Activity activity;
//
//    MyLocationListener(Activity activity) {
//        this.activity = activity;
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        Toast.makeText(activity, "Location Update Received", Toast.LENGTH_SHORT).show();
//        ((TextView) activity.findViewById(R.id.textView)).append("\n" + HelperFunctions.locationToString(location));
//
//    }
//
//    @Override
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//        Toast.makeText(activity, "Location Status Changed", Toast.LENGTH_SHORT).show();
//        ((TextView) activity.findViewById(R.id.textView)).append("\nLocation Status Changed: " + s);
//    }
//
//    @Override
//    public void onProviderEnabled(String s) {
//        Toast.makeText(activity, "Location Provider Enabled", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onProviderDisabled(String s) {
//        Toast.makeText(activity, "Location Provider Disabled", Toast.LENGTH_SHORT).show();
//        ((TextView) activity.findViewById(R.id.textView)).append("\nLocation Provider Disabled: " + s);
//    }
//}
