package com.dingmouren.aladdinkit.widget.floatview.widget;

import static android.content.Context.WINDOW_SERVICE;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.core.AdKitReal;
import com.dingmouren.aladdinkit.data.DataHolder;
import com.dingmouren.aladdinkit.ui.AdKitFunBoardActivity;
import com.dingmouren.aladdinkit.utils.SystemUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;
import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;

public class OkhttpLogFloatView extends AbsFloatView {


    private static final int SIZE_TYPE_CLOSE = 0;//关闭
    private static final int SIZE_TYPE_RESTORE = 1;//还原
    private static final int SIZE_TYPE_MAX = 2;//最大化


    private View mView;
    private TextView tvClear,tvContent,tvClose,tvMax,tvRestore;
    private ScrollView scrollView;
    private LinearLayout llTopBar;
    private RelativeLayout llContainer;

    private FrameLayout.LayoutParams llContainerLayoutParams ;

    private int mSizeType = SIZE_TYPE_RESTORE;//尺寸类型：最小化、还原、最大
    private WindowManager windowManager;


    private int screenWidth;
    private int screenHeight;

    @Override
    public void onCreate(Context context) {
        windowManager = (WindowManager) AdKit.sApplication.getSystemService(WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();


    }

    @Override
    public View onCreateView(Context context, FrameLayout rootView) {
        mView = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.layout_adkit_float_okhttp_log,rootView,false);
        tvClear = mView.findViewById(R.id.tv_clear);
        tvContent = mView.findViewById(R.id.tv_content);
        scrollView = mView.findViewById(R.id.scrollview);
        tvClose = mView.findViewById(R.id.tv_close);
        tvRestore = mView.findViewById(R.id.tv_restore);
        tvMax = mView.findViewById(R.id.tv_max);
        llTopBar = mView.findViewById(R.id.ll_top);
        llContainer = mView.findViewById(R.id.ll_container);

        llContainerLayoutParams = (FrameLayout.LayoutParams) llContainer.getLayoutParams();

        mSizeType = SIZE_TYPE_RESTORE;
        switchSize(mSizeType);

        return mView;
    }


    @Override
    public void onViewCreated(FrameLayout rootView) {


        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdKit.removeFloatView(OkhttpLogFloatView.class);
                AdKitReal.sIsShowOkhttpLogFloatView = false;
            }
        });
        tvRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSizeType = SIZE_TYPE_RESTORE;
                switchSize(mSizeType);
            }
        });
        tvMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSizeType = SIZE_TYPE_MAX;
                switchSize(mSizeType);
            }
        });


        onDataChange();

    }

    @Override
    public void initLayoutParams(FrameLayout.LayoutParams params) {
        params.topMargin = SystemUtils.getStatusBarHeight(AdKit.sApplication);
        params.leftMargin = 0;
    }

    private void switchSize(int sizeType){
        switch (sizeType){
            case SIZE_TYPE_RESTORE:
                llContainerLayoutParams.width = screenWidth;
                llContainerLayoutParams.height = screenHeight/3;
                llContainer.setLayoutParams(llContainerLayoutParams);
                break;
            case SIZE_TYPE_MAX:
                llContainerLayoutParams.width = screenWidth;
                llContainerLayoutParams.height = screenHeight;
                llContainer.setLayoutParams(llContainerLayoutParams);
                onMove(0,SystemUtils.getStatusBarHeight(AdKit.sApplication),0,0,true);
                break;
        }
    }


    /**
     * 清空日志
     */
    private void clear(){
        DataHolder.getOkHttpLogStringBuilder().delete(0,DataHolder.getOkHttpLogStringBuilder().length());
        FloatViewManager.notifyDataChange(OkhttpLogFloatView.class);
    }

    @Override
    public void onDataChange() {
        tvContent.post(new Runnable() {
            @Override
            public void run() {
                tvContent.setText(DataHolder.getOkHttpLogStringBuilder().toString());
            }
        });

        scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
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
