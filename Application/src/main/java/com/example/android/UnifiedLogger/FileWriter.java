package com.example.android.UnifiedLogger;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by vinay on 7/1/18.
 */

public class FileWriter {

    public FileWriter(String fileName, Activity activity) throws IOException {
        this.file = getPublicDirectory(fileName, activity);
        if (!this.file.createNewFile()) {
            Log.e("createFile", "Failed to create file: " + this.file.getPath());
        }
        this.stream = new FileOutputStream(this.file);
        this.writer = new OutputStreamWriter(this.stream);
        valid = true;
    }

    public FileWriter() {
        valid = false;
    }

    public void write(String string) throws IOException {
        if (valid) {
            writer.write(string);
        }
    }

    public void close() throws IOException {
        if (valid) {
            writer.close();
            stream.flush();
            stream.close();
        }
    }

    boolean valid = false;
    public File file;
    public FileOutputStream stream;
    public OutputStreamWriter writer;


    /* Gets File */
    public File getPublicDirectory(String fileName, Activity activity) {

        // Get Permissions:
        Log.d("createFile", "Checking File Permissions");
        if (!PermissionsChecker.checkStoragePermissions(activity)) {
            PermissionsChecker.requestStoragePermissions(activity);
            Log.d("createFile", "Permissions Requested");
        }

        // Get external directory
        File path = Environment.getExternalStorageDirectory();
        path = new File(path, activity.getResources().getString(R.string.app_name));
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, fileName);
        return file;

//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//
//        File[] files = path.listFiles();
//        path = new File(path, "/UnifiedLogger/");
//        Log.d("dbg", "Files in: " + path.getPath());
//        for(File file : files) {
//            Log.d("dbg", "\t" + file.getPath());
//        }
//        if (!path.mkdirs()) {
//            Log.e("ERR", "File not created: " + path);
//        }
//        File file = new File(path, fileName);
//        return file;
    }

}