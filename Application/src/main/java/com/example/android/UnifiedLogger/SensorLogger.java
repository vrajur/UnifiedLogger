package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by vinay on 7/25/18.
 */

public class SensorLogger implements SensorEventListener {

    Activity activity;

    SensorManager sensorManager;
    Sensor accelSensor;
    Sensor gyroSensor;
    FileWriter accelWriter;
    FileWriter gyroWriter;

    boolean accelActive = false;
    boolean gyroActive = false;

    public SensorLogger(Activity activity) {
        this.activity = activity;
    }

    public void initializeSensors() {
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        TextView textView = activity.findViewById(R.id.textView);
        textView.append("\nSensors Initialized");
        Log.d("Sensor", "Created sensor objects");
    }

    public void startLogging() throws IOException {
        TextView textView = activity.findViewById(R.id.textView);

        if (accelWriter == null && accelActive == false) {
            String accelFile = "AccelerometerLog_" + HelperFunctions.getTimestamp() + ".txt";
            accelWriter = new FileWriter(accelFile, activity);
            textView.append("\nCreated: " + accelFile);

            // Set Sensor Active and Register Listener:
            accelActive = true;
            sensorManager.registerListener(this, accelSensor, sensorManager.SENSOR_DELAY_FASTEST);
        }
        if (gyroWriter == null && gyroActive == false) {
            String gyroFile = "GyroscopeLog_" + HelperFunctions.getTimestamp() + ".txt";
            gyroWriter = new FileWriter(gyroFile, activity);
            textView.append("\nCreated: " + gyroFile);

            // Set Sensor Active and Register Listener:
            gyroActive = true;
            sensorManager.registerListener(this, gyroSensor, sensorManager.SENSOR_DELAY_FASTEST);
        }

    }

    public void stopLogging() throws IOException {
        TextView textView = activity.findViewById(R.id.textView);
        sensorManager.unregisterListener(this);

        if (accelWriter != null) {
            accelActive = false;
            accelWriter.close();
            textView.append("\nClosed: " + accelWriter.file.getName());
            accelWriter = null;
        }

        if (gyroWriter != null) {
            gyroActive = false;
            gyroWriter.close();
            textView.append("\nClosed: " + gyroWriter.file.getName());
            gyroWriter = null;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        try {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    if (accelActive && accelWriter != null) {
                        String sensorData = String.format("%d; ACC; %f; %f; %f; %f; %f; %f\n", event.timestamp, event.values[0], event.values[1], event.values[2], 0.f, 0.f, 0.f);
                        accelWriter.write(sensorData);
                        Log.d("Accelerometer", sensorData);
                    }
                    break;
                case Sensor.TYPE_GYROSCOPE:
                    if (gyroActive && gyroWriter != null) {
                        String sensorData = String.format("%d; GYR; %f; %f; %f; %f; %f; %f\n", event.timestamp, event.values[0], event.values[1], event.values[2], 0.f, 0.f, 0.f);
                        gyroWriter.write(sensorData);
                        Log.d("Gyroscope", sensorData);
                    }
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
