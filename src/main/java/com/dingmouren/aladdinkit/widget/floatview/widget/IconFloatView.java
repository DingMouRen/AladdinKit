package com.dingmouren.aladdinkit.widget.floatview.widget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.ui.AdKitFunBoardActivity;
import com.dingmouren.aladdinkit.utils.SystemUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class IconFloatView extends AbsFloatView {

    @Override
    public void onCreate(Context context) {

    }

    @Override
    public View onCreateView(Context context, FrameLayout rootView) {
        View view = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.layout_adkit_float_icon,rootView,false);
        return view;
    }

    @Override
    public void onViewCreated(FrameLayout rootView) {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdKitFunBoardActivity.newInstance(context);
            }
        });
    }

    @Override
    public void initLayoutParams(FrameLayout.LayoutParams params) {
        params.topMargin = SystemUtils.getStatusBarHeight(AdKit.sApplication);
        params.leftMargin = SystemUtils.getStatusBarHeight(AdKit.sApplication);
    }
}
