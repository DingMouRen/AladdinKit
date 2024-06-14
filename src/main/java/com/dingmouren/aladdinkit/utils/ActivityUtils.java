package com.dingmouren.aladdinkit.utils;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class ActivityUtils {
    public static List<Activity> sList = new ArrayList<>();

    private ActivityUtils(){}

    public static void addActivity(Activity activity){
        if (null != activity)sList.add(activity);
    }

    public static void removeActivity(Activity activity){
        if (null != activity && sList.contains(activity))sList.remove(activity);
    }

    public static Activity getTopActivity(){
        Activity activity = null;
        if (sList.size() > 0){
            activity = sList.get(sList.size() - 1);
        }
        return activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing() &&  !activity.isDestroyed();
    }
}
