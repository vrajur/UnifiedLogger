package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.SystemClock;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
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
        this.camlogWriter = null;
    }


    public void startLogging(String videoName) throws IOException {
        TextView textView = (TextView) activity.findViewById(R.id.textView);

        String logname = videoName.replaceFirst("^Video_", "CameraLog_");
        logname = logname.replaceFirst(".mp4", ".txt");
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


        Camera2VideoFragment fragment = (Camera2VideoFragment) activity.getFragmentManager().findFragmentByTag("Camera2VideoFragment");
        if (!fragment.mIsRecordingVideo) {
            return;
        }

        // Stop recording if not enough space left on device
        double remainingMB = HelperFunctions.getAvailableSpace(HelperFunctions.Units.MB);
        Log.d("PhoneTrigger", String.format("Remaining Space: %3.2fMB", remainingMB));
        if (fragment.mIsRecordingVideo && remainingMB < Settings.requiredFreeSpaceMB) {
            fragment.phoneTriggers.onNotEnoughSpaceLeft(remainingMB);
            return;
        }

        // Truncate File if past limits:
        double elapsedTimeSeconds = HelperFunctions.getTimeSince(fragment.mVideoStartTimeNanos, HelperFunctions.Units.SECONDS);
        double videoSizeMB = HelperFunctions.getVideoSize(new File(fragment.mNextVideoAbsolutePath), HelperFunctions.Units.MB);
        Log.d(TAG, String.format("Elapsed Time: %3.2f seconds\tVideo Size: %3.2fMB", elapsedTimeSeconds, videoSizeMB));
        if (elapsedTimeSeconds > Settings.maxVideoLengthSeconds || videoSizeMB > Settings.maxVideoSizeMB) {
            fragment.phoneTriggers.onTruncateVideo(elapsedTimeSeconds, videoSizeMB);
            return;
        }

        // Write Log Data to File:
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
    }
}
