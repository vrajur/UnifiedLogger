package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vinay on 7/25/18.
 */

public class HelperFunctions {

    public enum Units {
        Bytes,
        KB,
        MB,
        GB,
        NANOS,
        MICROS,
        MILLIS,
        SECONDS
    }


    static public String getTimestamp() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        Date currentTime = Calendar.getInstance().getTime();
        return dateFormat.format(date);
    }

    static public String locationToString(Location loc, long startupTime) {
        String output = String.format("Time: %d Lat: %f Lon: %f Alt: %f Speed: %f Bearing: %f",
                loc.getTime()-startupTime, loc.getLatitude(), loc.getLongitude(), loc.getAltitude(), loc.getSpeed(), loc.getBearing());
        return output;
    }

    static public Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    static public double getAvailableSpace(Units units) {
        double KB = 1024;
        double MB = KB*KB;
        double GB = KB*KB*KB;

        double factor = 1.0;
        switch (units) {
            case Bytes:
                factor = 1.0;
                break;
            case KB:
                factor = 1.0/KB;
                break;
            case MB:
                factor = 1.0/MB;
                break;
            case GB:
                factor = 1.0/GB;
                break;
        }

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return stat.getAvailableBlocksLong() * stat.getBlockSizeLong() * factor;
    }

    static public double getTimeSince(long startTimeNanos, Units units) {
        double sec   = 1;
        double milli = 1000;
        double micro = milli * milli;
        double nano  = milli * milli * milli;
        double factor = 1;

        switch (units) {
            case NANOS:
                factor = nano/nano;
                break;
            case MICROS:
                factor = micro/nano;
                break;
            case MILLIS:
                factor = milli/nano;
                break;
            case SECONDS:
                factor = sec/nano;
                break;
        }

        double elapsedNanos = SystemClock.elapsedRealtimeNanos() - startTimeNanos;
        return elapsedNanos * factor;
    }

    static public double getVideoSize(File file, Units units) {
        double KB = 1024;
        double MB = KB*KB;
        double GB = KB*KB*KB;

        double factor = 1.0;
        switch (units) {
            case Bytes:
                factor = 1.0;
                break;
            case KB:
                factor = 1.0/KB;
                break;
            case MB:
                factor = 1.0/MB;
                break;
            case GB:
                factor = 1.0/GB;
                break;
        }

        long fileSize = file.length();
        return fileSize * factor;
    }

    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !TextUtils.isEmpty(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
        }
        return ssid;
    }

}
