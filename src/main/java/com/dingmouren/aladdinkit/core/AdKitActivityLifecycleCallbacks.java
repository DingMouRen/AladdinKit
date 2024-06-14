package com.dingmouren.aladdinkit.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dingmouren.aladdinkit.utils.ActivityUtils;
import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;
import com.dingmouren.aladdinkit.widget.floatview.widget.TopActivityFloatView;

public class AdKitActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
        ActivityUtils.addActivity(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        FloatViewManager.onActivityResume(activity);
        FloatViewManager.notifyDataChange(TopActivityFloatView.class);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        FloatViewManager.onActivityDestroy(activity);
        ActivityUtils.removeActivity(activity);
        FloatViewManager.notifyDataChange(TopActivityFloatView.class);
    }
}
