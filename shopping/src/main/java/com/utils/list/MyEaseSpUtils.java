package com.utils.list;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2018/9/14 0014.
 */

public class MyEaseSpUtils {

    private static SharedPreferences preferences;
    private static SharedPreferences.Editor edit;

    public static void init(Context ctx) {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        }
    }


    public static void CommitString(String key, String value) {
        edit = preferences.edit();
        edit.putString(key, value).commit();

    }

    public static String getString(String key, String value) {

        return preferences.getString(key, value);
    }
}
