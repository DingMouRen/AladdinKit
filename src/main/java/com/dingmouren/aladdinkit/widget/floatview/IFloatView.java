package com.dingmouren.aladdinkit.widget.floatview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 悬浮View接口
 */
public interface IFloatView {

    /**View创建时调用，用于初始化变量，不能进行View的操作*/
    void onCreate(Context context);

    /**传入View，用于创建控件*/
    View onCreateView(Context context, FrameLayout rootView);

    /**将xml中的控件添加到rootView以后调用，在当前方法中可以进行view的一些操作*/
    void onViewCreated(FrameLayout rootView);


    /**当前的FloatView添加到根布局里时调用*/
//    void onResume();

    /**当前activity onPause时调用*/
//    void onPause();

    void initLayoutParams(FrameLayout.LayoutParams params);

    /**app进入后台时调用*/
//    void onEnterBackground();


    /**app回到前台时调用*/
//    void onEnterForeground();

    /**悬浮窗主动销毁时调用 不能在当前生命周期回调函数中调用 detach自己 否则会出现死循环*/
//    void onDestroy();
}
