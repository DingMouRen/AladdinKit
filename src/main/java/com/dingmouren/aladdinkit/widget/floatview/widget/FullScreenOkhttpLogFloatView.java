package com.dingmouren.aladdinkit.widget.floatview.widget;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.data.DataHolder;
import com.dingmouren.aladdinkit.utils.SizeUtils;
import com.dingmouren.aladdinkit.utils.SystemUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;
import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;
import com.dingmouren.aladdinkit.widget.floatview.adapter.TVOkhttpLogAdapter;

import static android.content.Context.WINDOW_SERVICE;

public class FullScreenOkhttpLogFloatView extends AbsFloatView {
    
    
    private View mView;
    private TextView tvClear;
    private TextView tvMin;
    private RecyclerView recyclerView;
    private LinearLayout llTopBar;
    private ImageView imgFloatToggle;
    private RelativeLayout llContainer;
    private FrameLayout.LayoutParams layoutParams;
    private WindowManager windowManager;
    private int screenWidth;
    private int screenHeight;
    private int statusBarHeight;
    private boolean isShowLog = false;

    private TVOkhttpLogAdapter adapter;


    public void onCreate(Context context) {
        windowManager = (WindowManager)AdKit.sApplication.getSystemService(WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();
        statusBarHeight = SystemUtils.getStatusBarHeight(AdKit.sApplication);
    }

    public View onCreateView(Context context, FrameLayout rootView) {
        mView = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.layout_adkit_float_okhttp_log_full, rootView, false);
        tvClear = (TextView)mView.findViewById(R.id.tv_clear);
        recyclerView = (RecyclerView)mView.findViewById(R.id.recycler);
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
        imgFloatToggle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mTouchListener.onTouchEvent(view,motionEvent);
                return true;
            }
        });

        adapter = new TVOkhttpLogAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(AdKit.sApplication));
        recyclerView.setAdapter(adapter);
    }

    public void initLayoutParams(FrameLayout.LayoutParams params) {
        params.topMargin = SystemUtils.getStatusBarHeight(AdKit.sApplication);
        params.leftMargin = 0;
        params.width =  SizeUtils.dp2px(40);
        params.height = SizeUtils.dp2px(40);
    }

    

    private void clear() {
        DataHolder.getOkHttpLogList().clear();
        FloatViewManager.notifyDataChange(FullScreenOkhttpLogFloatView.class);
    }

    @Override
    public void onMove(int x, int y, int dx, int dy, boolean initPosition) {
        // 当前悬浮窗左上角的x, y坐标加上本次移动的dx, dy
        int newX = mLayoutParams.leftMargin + dx;
        int newY = mLayoutParams.topMargin + dy;

        // 限制新坐标在屏幕范围内
        newX = Math.max(0, Math.min(newX, screenWidth - mChildView.getWidth()));

        int yMin = statusBarHeight;
        int yMax = screenHeight - SizeUtils.dp2px(40);
        if (newY < statusBarHeight){
            newY = yMin;
        } else if (newY > yMax ){
            newY = yMax;
        }
        // 根据是否是初始位置直接设置或更新布局参数
        mLayoutParams.leftMargin = newX;
        mLayoutParams.topMargin = newY;
        FloatViewManager.moveFloatViewPosition(this.getClass(),mLayoutParams);
    }

    // 假设有一个获取边界内边距的方法，用于微调限制范围，这里仅为示意
    private int getPadding() {
        // 实际应用中应根据设计返回适当的内边距值
        return 0;
    }

    private void showIconLayout() {
        isShowLog = false;
        imgFloatToggle.setVisibility(View.VISIBLE);
        llTopBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        mLayoutParams.width = SizeUtils.dp2px(40);
        mLayoutParams.height = SizeUtils.dp2px(40);
        mRootView.invalidate();
    }


    private void showLogLayout() {
        isShowLog = true;
        imgFloatToggle.setVisibility(View.GONE);
        llTopBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);

        mLayoutParams.width = screenWidth;
        mLayoutParams.height = screenHeight - statusBarHeight;
        mRootView.invalidate();



    }




    @SuppressLint("NotifyDataSetChanged")
    public void onDataChange() {
        if (isShowLog){
            if (null != adapter){
                int logListSize = DataHolder.getOkHttpLogList().size();
                if (logListSize == 0){
                    adapter.notifyDataSetChanged();
                    return;
                }
                adapter.notifyDataSetChanged();
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.smoothScrollToPosition(DataHolder.getOkHttpLogList().size() - 1);
                    }
                });
            }
        }

    }



}
