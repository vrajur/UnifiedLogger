/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

public class CameraActivity extends Activity {

    GpsLogger gpsLogger;
    SensorLogger sensorLogger;
    CameraLogger camLogger;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        // Disable Screen Timeout:
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // To enable timeout again: getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        camLogger = new CameraLogger(CameraActivity.this);
        gpsLogger = new GpsLogger(CameraActivity.this);
        sensorLogger = new SensorLogger(CameraActivity.this);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance(), "Camera2VideoFragment")
                    .commit();
        }

        // Request Network Permissions:
        if (!PermissionsChecker.checkNetworkPermissions(this)) {
            PermissionsChecker.requestNetworkPermissions(this);
        }
        Log.d("MainActivity", "Active Network: " + HelperFunctions.getCurrentSsid(this.getApplicationContext()));

        // Register broadcast receiver:
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("MainActivity", "New Network Connection: " + HelperFunctions.getCurrentSsid(context));
            }
        };
        registerReceiver(receiver, filter);
        Log.d("MainActivity", "Registered Broadcast Listener");
    }



    @Override
    protected void onPause() {
        gpsLogger.unsubscribeToGPS();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();
    }

}
