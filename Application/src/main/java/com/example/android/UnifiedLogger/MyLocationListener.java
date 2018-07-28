package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vinay on 7/2/18.
 */

public class MyLocationListener implements LocationListener {

    Activity mActivity;

    MyLocationListener(Activity activity) {
        mActivity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(mActivity.getApplicationContext(), "Location Update Received", Toast.LENGTH_SHORT).show();
        ((TextView) mActivity.findViewById(R.id.textView)).append(location.toString());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Toast.makeText(mActivity.getApplicationContext(), "Location Status Changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(mActivity.getApplicationContext(), "Location Provider Enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(mActivity.getApplicationContext(), "Location Provider Disabled", Toast.LENGTH_SHORT).show();
    }
}
