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
import android.os.Bundle;

public class CameraActivity extends Activity {

    GpsLogger gpsLogger;
    SensorLogger sensorLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

//        TextView textView = this.findViewById(R.id.textView);
//        textView.setText("Camera Activity Initialized");
        gpsLogger = new GpsLogger(CameraActivity.this);
        sensorLogger = new SensorLogger(CameraActivity.this);

        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsLogger.unsubscribeToGPS();
    }


}
