package com.cloudcousion.orderproc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cloudcousion.orderproc.config.SysConfigs;

public class PreferenceUtils {

    private static final String PREF_ID = "com.cloudcousion.orderproc";
    private static final String KEY_DISPATCH_RATE = "dispatch_rate";

    public static int getDispatchRatePreference(Context context) {
        return getIntPreference(context, KEY_DISPATCH_RATE, SysConfigs.defaultDispatchRate);
    }

    public static void saveDispatchRatePreference(Context context, int rate) {
        saveIntPreference(context, KEY_DISPATCH_RATE, rate);
    }

    public static int getIntPreference(Context context, String key, int defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, Context.MODE_PRIVATE);
        return preferences.getInt(key, defaultValue);
    }

    public static void saveIntPreference(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
}
