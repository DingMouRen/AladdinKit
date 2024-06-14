package com.dingmouren.aladdinkit.widget.floatview.widget;

import static android.content.Context.WINDOW_SERVICE;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.core.AdKitReal;
import com.dingmouren.aladdinkit.data.DataHolder;
import com.dingmouren.aladdinkit.utils.ActivityUtils;
import com.dingmouren.aladdinkit.utils.SystemUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;
import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;

public class TopActivityFloatView extends AbsFloatView {

    private View mView;
    private WindowManager windowManager;


    private int screenWidth;
    private int screenHeight;

    private ImageView imgClose;
    private TextView tv;

    @Override
    public void onCreate(Context context) {
        windowManager = (WindowManager) AdKit.sApplication.getSystemService(WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();


    }

    @Override
    public View onCreateView(Context context, FrameLayout rootView) {
        mView = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.layout_adkit_float_top_activity,rootView,false);
        imgClose = mView.findViewById(R.id.img_close);
        tv = mView.findViewById(R.id.tv);
        return mView;
    }


    @Override
    public void onViewCreated(FrameLayout rootView) {

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdKit.removeFloatView(TopActivityFloatView.class);
            }
        });
        onDataChange();
    }

    @Override
    public void initLayoutParams(FrameLayout.LayoutParams params) {
        params.topMargin = SystemUtils.getStatusBarHeight(AdKit.sApplication);
        params.leftMargin = 0;
    }



    @Override
    public void onDataChange() {
        Activity activity = ActivityUtils.getTopActivity();
        String activityPath = activity.getComponentName().getClassName();
        tv.setText(activityPath);
    }


}
