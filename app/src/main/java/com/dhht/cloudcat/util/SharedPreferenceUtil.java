package com.dhht.cloudcat.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：Tyhj on 2018/12/4 00:16
 * 邮箱：tyhj5@qq.com
 * github：github.com/tyhjh
 * description：
 */

public class SharedPreferenceUtil {

    private static String NAME = "config";
    static Application sApplication;

    public static void init(Application application) {
        sApplication = application;
    }

    public static SharedPreferences getSharedPreference() {
        return sApplication.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public static void saveString(String key, String value) {
        SharedPreferences.Editor sharedData = getSharedPreference().edit();
        sharedData.putString(key, value);
        sharedData.commit();
    }

    public static String getStringValue(String key) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getString(key, null);
    }

    public static void saveInt(String key, int value) {
        SharedPreferences.Editor sharedData = getSharedPreference().edit();
        sharedData.putInt(key, value);
        sharedData.commit();
    }

    public static int getIntValue(String key) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getInt(key, 3);
    }


}
