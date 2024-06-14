package com.dingmouren.aladdinkit.utils;

import android.util.Log;

public class LogUtils {

    public static final String TAG = "Adkit";

    public static void d(String msg){
        Log.d(TAG,msg);
    }

    public static void e(String msg){
        Log.e(TAG,msg);
    }
}
