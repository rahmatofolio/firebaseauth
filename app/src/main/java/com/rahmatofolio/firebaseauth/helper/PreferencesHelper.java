package com.rahmatofolio.firebaseauth.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.rahmatofolio.firebaseauth.R;

import org.json.JSONArray;

public class PreferencesHelper {
    private static PreferencesHelper instance;
    private SharedPreferences prefs;

    public static PreferencesHelper getInstance() {
        return instance;
    }

    private PreferencesHelper(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    private PreferencesHelper(Context context, String sharePreferencesName) {
        prefs = context.getApplicationContext().getSharedPreferences(sharePreferencesName, Context.MODE_PRIVATE);
    }

    public static PreferencesHelper initHelper(Context context) {
        if (instance == null)
            instance = new PreferencesHelper(context);
        return instance;
    }

    public static PreferencesHelper initHelper(Context context, String sharePreferencesName) {
        if (instance == null)
            instance = new PreferencesHelper(context, sharePreferencesName);
        return instance;
    }

    public void setValue(String KEY, boolean value) {
        prefs.edit().putBoolean(KEY, value).apply();
    }

    public void setValue(String KEY, String value) {
        prefs.edit().putString(KEY, value).apply();
    }

    public void setValue(String KEY, int value) {
        prefs.edit().putInt(KEY, value).apply();
    }

    public void setValue(String KEY, long value) {
        prefs.edit().putLong(KEY, value).apply();
    }

    public void setValue(String KEY, float value) {
        prefs.edit().putFloat(KEY, value).apply();
    }

    public void setValue(String KEY, double defValue) {
        setValue(KEY, String.valueOf(defValue));
    }

    public <T> T[] getArrayValue(String KEY) {
        T[] results = null;
        try {
            JSONArray jArray = new JSONArray(prefs.getString(KEY, ""));
            for (int i = 0; i < jArray.length(); i++) {
                results[i] = (T) jArray.get(i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;
    }


    public boolean getBooleanValue(String KEY, boolean defvalue) {
        return prefs.getBoolean(KEY, defvalue);
    }

    public String getStringValue(String KEY, String defvalue) {
        return prefs.getString(KEY, defvalue);
    }

    public int getIntValue(String KEY, int defValue) {
        return prefs.getInt(KEY, defValue);
    }

    public long getLongValue(String KEY, long defValue) {
        return prefs.getLong(KEY, defValue);
    }

    public float getFloatValue(String KEY, float defValue) {
        return prefs.getFloat(KEY, defValue);
    }

    public double getDoubleValue(String KEY, double defValue) {
        return Double.parseDouble(getStringValue(KEY, String.valueOf(defValue)));
    }

    public void removeKey(String KEY) {
        prefs.edit().remove(KEY).apply();
    }

    public boolean contain(String KEY) {
        return prefs.contains(KEY);
    }

    public void registerChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }
}
