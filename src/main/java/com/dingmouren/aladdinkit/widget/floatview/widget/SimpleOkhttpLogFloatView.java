package com.dingmouren.aladdinkit.widget.floatview.widget;


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
import com.dingmouren.aladdinkit.data.DataHolder;
import com.dingmouren.aladdinkit.utils.SystemUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;
import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;

import static android.content.Context.WINDOW_SERVICE;

public class SimpleOkhttpLogFloatView extends AbsFloatView {
    
    
    private View mView;
    private TextView tvClear;
    private TextView tvContent;
    private TextView tvMin;
    private ScrollView scrollView;
    private LinearLayout llTopBar;
    private ImageView imgFloatToggle;
    private RelativeLayout llContainer;
    private FrameLayout.LayoutParams layoutParams;
    private WindowManager windowManager;
    private int screenWidth;
    private int screenHeight;



    public void onCreate(Context context) {
        windowManager = (WindowManager)AdKit.sApplication.getSystemService(WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
    }

    public View onCreateView(Context context, FrameLayout rootView) {
        mView = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.layout_adkit_float_okhttp_log_tv, rootView, false);
        tvClear = (TextView)mView.findViewById(R.id.tv_clear);
        tvContent = (TextView)mView.findViewById(R.id.tv_content);
        scrollView = (ScrollView)mView.findViewById(R.id.scrollview);
        tvMin = (TextView)mView.findViewById(R.id.tv_min);
        llTopBar = (LinearLayout)mView.findViewById(R.id.ll_top);
        imgFloatToggle = (ImageView)mView.findViewById(R.id.img_float_toggle);
        llContainer = (RelativeLayout)mView.findViewById(R.id.ll_container);
        layoutParams = (FrameLayout.LayoutParams)llContainer.getLayoutParams();
        return mView;
    }

    public void onViewCreated(FrameLayout rootView) {
        tvClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clear();
            }
        });
        tvMin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showIconLayout();
            }
        });
        imgFloatToggle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showLogLayout();
            }
        });
        
        onDataChange();
    }

    public void initLayoutParams(FrameLayout.LayoutParams params) {
        params.topMargin = SystemUtils.getStatusBarHeight(AdKit.sApplication);
        params.leftMargin = 0;
    }

    

    private void clear() {
        DataHolder.getOkHttpLogStringBuilder().delete(0, DataHolder.getOkHttpLogStringBuilder().length());
        FloatViewManager.notifyDataChange(SimpleOkhttpLogFloatView.class);
    }



    private void showIconLayout() {
        imgFloatToggle.setVisibility(View.VISIBLE);
        llTopBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.GONE);
    }

    private void showLogLayout() {
        imgFloatToggle.setVisibility(View.GONE);
        llTopBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.VISIBLE);
        onDataChange();
    }

    public void onDataChange() {
        tvContent.post(new Runnable() {
            public void run() {
                tvContent.setText(DataHolder.getOkHttpLogStringBuilder().toString());
            }
        });
        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }



}
