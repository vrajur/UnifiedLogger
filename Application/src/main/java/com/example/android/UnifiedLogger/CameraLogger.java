package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by vinay on 7/29/18.
 */

public class CameraLogger extends CameraCaptureSession.CaptureCallback {

    String TAG = "CameraLogger";
    FileWriter camlogWriter;
    Activity activity;

    public CameraLogger(Activity activity) {
        this.activity = activity;
        camlogWriter = null;
    }


    public void startLogging(String videoName) throws IOException {
        TextView textView = (TextView) activity.findViewById(R.id.textView);

        String logname = videoName.replaceFirst("^Video_", "CameraLog_");
        camlogWriter = new FileWriter(logname, activity);
        if (textView != null) {
            textView.append("\nCreated: " + camlogWriter.filename);
        }
    }

    public void stopLogging() throws IOException {
        TextView textView = (TextView) activity.findViewById(R.id.textView);

        if (camlogWriter != null) {
            camlogWriter.close();
            if (textView != null) {
                textView.append(String.format(
                        "\nClosed: %s [%d bytes]", camlogWriter.filename, camlogWriter.getFileSize()
                ));
            }
            camlogWriter = null;
        }

    }

    @Override
    public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult results) {
        long timestamp = SystemClock.elapsedRealtimeNanos();

        for (CaptureResult result: results.getPartialResults()) {
            try {
                if (camlogWriter != null) {
                    camlogWriter.write(String.format(
                            "\nID: %d, Frame: %d, Timestamp: %d", result.getSequenceId(), result.getFrameNumber(), timestamp
                            ));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



//        Log.d(TAG, "Results:");
//        for (CaptureResult result : results.getPartialResults()) {
//            int i = result.getSequenceId();
//            Log.d(TAG, "Result ["+i+"]: " + result.toString());
//            Log.d(TAG, "Frame Number: " + result.getFrameNumber());
//            Log.d(TAG, "Timestamp: " + result.get(CaptureResult.SENSOR_TIMESTAMP));
//            Log.d(TAG, "System Time: " + timestamp);
//            i += 1;
//        }

    }


}
