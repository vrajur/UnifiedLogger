package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by vinay on 7/30/18.
 */

interface PhoneTriggersCallback {

    void onNotEnoughSpaceLeft(double remainingMB);

    void onTruncateVideo(double videoLengthSeconds, double videoSizeMB);

}

public class PhoneTriggers implements PhoneTriggersCallback {

    Activity activity;
    public PhoneTriggers(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onNotEnoughSpaceLeft(final double remainingMB) {

        final TextView textView = activity.findViewById(R.id.textView);
        final Button videoButton = activity.findViewById(R.id.video);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (textView != null) {
                    textView.append(String.format(
                            "\nEnding Recording - Not enough space left on device!\n\tRequired: %3.3fMB\n\tAvailable: %3.3fMB",
                            Settings.requiredFreeSpaceMB, remainingMB));
                }

                // Stop logging:
                videoButton.performClick();
            }
        });
    }

    @Override
    public void onTruncateVideo(double videoLengthSeconds, double videoSizeMB) {
        final TextView textView = activity.findViewById(R.id.textView);
        final Button videoButton = activity.findViewById(R.id.video);

        String tmpReason = "";
        double tmpUsage = 0;
        double tmpLimit = 0;
        String tmpUnits = "";
        if (videoLengthSeconds > Settings.maxVideoLengthSeconds) {
            tmpReason = "Max Video Runtime";
            tmpUsage = videoLengthSeconds;
            tmpLimit = Settings.maxVideoLengthSeconds;
            tmpUnits = "seconds";
        } else if (videoSizeMB > Settings.maxVideoSizeMB) {
            tmpReason = "Max Video Size (MB)";
            tmpUsage = videoSizeMB;
            tmpLimit = Settings.maxVideoSizeMB;
            tmpUnits = "MB";
        } else {
            tmpReason = "Unknown Reason";
        }

        final String reason = tmpReason;
        final double usage = tmpUsage;
        final double limit = tmpLimit;
        final String units = tmpUnits;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (textView != null) {
                    textView.append(String.format(
                            "\nTruncating Recordings - %s\n\tUsed: %3.3f%s\n\tLimit: %3.3f%s",
                            reason, usage, units, limit, units));
                }

                // Stop logging:
                videoButton.performClick();

                // Restart logging:
                videoButton.performClick();
            }
        });
    }
}
