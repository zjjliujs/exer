package com.cloudcousion.orderserver.utils;

import android.util.Log;

public class AndroidLogger implements OrderLoggerI {
    private static AndroidLogger instance = null;

    private AndroidLogger() {
    }

    public static synchronized OrderLoggerI getInstance() {
        if (instance == null) {
            instance = new AndroidLogger();
        }
        return instance;
    }

    @Override
    public void logDebug(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
    }

    @Override
    public void logInfo(String msg) {
        Log.i(this.getClass().getSimpleName(), msg);
    }

    @Override
    public void logWarning(String msg) {
        Log.w(this.getClass().getSimpleName(), msg);
    }

    @Override
    public void logError(String msg) {
        Log.e(this.getClass().getSimpleName(), msg);
    }
}
