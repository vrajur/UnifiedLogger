package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vinay on 7/25/18.
 */

public class HelperFunctions {

    static public String getTimestamp() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        Date date = new Date(Calendar.getInstance().getTimeInMillis());
        Date currentTime = Calendar.getInstance().getTime();
        return dateFormat.format(date);
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
}
