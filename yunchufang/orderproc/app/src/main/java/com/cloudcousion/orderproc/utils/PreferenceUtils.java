package com.cloudcousion.orderproc.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.cloudcousion.orderproc.OrderSimulatorActivity;

public class PreferenceUtils {

    private static final String PREF_ID = "com.cloudcousion.orderproc";
    private static final String KEY_DISPATCH_RATE = "dispatch_rate";
    private static final String KEY_HOT_SHELF_CAP = "hot_shelf_cap";
    private static final String KEY_COLD_SHELF_CAP = "cold_shelf_cap";
    private static final String KEY_FROZEN_SHELF_CAP = "frozen_shelf_cap";
    private static final String KEY_OVERFLOW_SHELF_CAP = "overflow_shelf_cap";

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

    public static int getDispatchRatePref(Context context, int defaultValue) {
        return getIntPreference(context, KEY_DISPATCH_RATE, defaultValue);
    }

    public static void saveDispatchRatePreference(Context context, int rate) {
        saveIntPreference(context, KEY_DISPATCH_RATE, rate);
    }

    public static Integer getHotShelfCapPref(Context context, int defaultValue) {
        return getIntPreference(context, KEY_HOT_SHELF_CAP, defaultValue);
    }

    public static void saveHotShelfCapPref(Context context, int value) {
        saveIntPreference(context, KEY_HOT_SHELF_CAP, value);
    }

    public static Integer getColdShelfCapPref(Context context, int defaultValue) {
        return getIntPreference(context, KEY_COLD_SHELF_CAP, defaultValue);
    }

    public static void saveColdShelfCapPref(Context context, int value) {
        saveIntPreference(context, KEY_COLD_SHELF_CAP, value);
    }

    public static Integer getFrozenShelfCapPref(Context context, int value) {
        return getIntPreference(context, KEY_FROZEN_SHELF_CAP, value);
    }

    public static void saveFrozenShelfCapPref(Context context, int value) {
        saveIntPreference(context, KEY_FROZEN_SHELF_CAP, value);
    }

    public static Integer getOverflowShelfCapPref(Context context, int value) {
        return getIntPreference(context, KEY_OVERFLOW_SHELF_CAP, value);
    }

    public static void saveOverflowShelfCapPref(Context context, int value) {
        saveIntPreference(context, KEY_OVERFLOW_SHELF_CAP, value);
    }


}
