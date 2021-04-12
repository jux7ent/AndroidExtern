package com.example.stackoverflowapp;

import android.util.Log;

public class MyDebug {
    private static final String LOG_TAG = "MY_TAG";

    public static void Log(String message) {
        Log.d(LOG_TAG, message);
    }
}
