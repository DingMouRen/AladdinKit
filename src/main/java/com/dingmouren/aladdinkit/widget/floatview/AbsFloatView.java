package com.dingmouren.aladdinkit.widget.floatview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public abstract class AbsFloatView implements IFloatView, FloatViewTouchListener.OnTouchEventListener {

    public FloatFrameLayout mRootView;

    public View mChildView;

    public FrameLayout.LayoutParams mLayoutParams;

    public FloatViewTouchListener mTouchListener = new FloatViewTouchListener(this);

    public Context context;


    @SuppressLint("ClickableViewAccessibility")
    public void performCreate(Context context){

        this.context = context;

        //View创建时调用，用于初始化变量，不能进行View的操作
        onCreate(context);

        mRootView = new FloatFrameLayout(context);

        //用于创建控件
        mChildView = onCreateView(context,mRootView);

        mRootView.addView(mChildView);

        //根布局手势处理
        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mTouchListener.onTouchEvent(view,motionEvent);
                return true;
            }
        });

        //将xml中的控件添加到rootView以后调用，在当前方法中可以进行view的一些操作
        onViewCreated(mRootView);

        mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        mLayoutParams.width = FloatViewLayoutParams.WRAP_CONTENT;

        mLayoutParams.height = FloatViewLayoutParams.WRAP_CONTENT;

        mLayoutParams.gravity = Gravity.START|Gravity.TOP;

        initLayoutParams(mLayoutParams);
    }

    public void onDataChange(){}

    @Override
    public void onMove(int x, int y, int dx, int dy,boolean initPosition) {

        if (initPosition){
            mLayoutParams.leftMargin = x;
            mLayoutParams.topMargin = y;
        }else {
            mLayoutParams.leftMargin += dx;
            mLayoutParams.topMargin += dy;
        }

        FloatViewManager.moveFloatViewPosition(this.getClass(),mLayoutParams);
    }



    @Override
    public void onUp(int x, int y) {

    }

    @Override
    public void onDown(int x, int y) {

    }
}
