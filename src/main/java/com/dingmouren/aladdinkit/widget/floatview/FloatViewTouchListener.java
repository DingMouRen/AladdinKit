package com.dingmouren.aladdinkit.widget.floatview;

import android.view.MotionEvent;
import android.view.View;

import com.dingmouren.aladdinkit.utils.SizeUtils;

/**
 * 浮动视图的触摸事件监听器。
 * 用于处理浮动视图的触摸事件，包括拖动、点击等交互。
 */
public class FloatViewTouchListener {
    // 最小移动距离，用于判断是点击还是拖动
    private static final int MIN_DISTANCE_MOVE = 4;
    // 最小点击时间间隔，用于判断是单击还是双击
    private static final int MIN_TAP_TIME = 1000;

    // 触摸事件监听器接口
    private OnTouchEventListener mEventListener;
    // 上一次触摸的X坐标
    private int mLastX;
    // 上一次触摸的Y坐标
    private int mLastY;
    // 触摸开始时的X坐标
    private int mStartX;
    // 触摸开始时的Y坐标
    private int mStartY;
    // 当前触摸状态
    private TouchState mState = TouchState.STATE_STOP;


    /**
     * 构造函数
     */
    public FloatViewTouchListener() {
    }

    /**
     * 构造函数，带监听器参数
     *
     * @param eventListener 触摸事件监听器
     */
    public FloatViewTouchListener(OnTouchEventListener eventListener) {
        mEventListener = eventListener;
    }

    /**
     * 设置触摸事件监听器
     *
     * @param eventListener 触摸事件监听器
     */
    public void setEventListener(OnTouchEventListener eventListener) {
        mEventListener = eventListener;
    }

    /**
     * 触摸状态枚举类
     */
    private enum TouchState {
        STATE_MOVE, // 移动状态
        STATE_STOP  // 停止状态
    }

    /**
     * 处理触摸事件
     *
     * @param v 触摸的视图
     * @param event 触摸事件
     * @return 是否处理了触摸事件
     */
    public boolean onTouchEvent(View v, MotionEvent event) {
        // 将dp单位转换为px单位
        int distance = SizeUtils.dp2px(1) * MIN_DISTANCE_MOVE;
        // 获取当前触摸的X、Y坐标
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        // 根据触摸事件的动作类型进行处理
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                // 记录触摸开始的坐标
                mStartX = x;
                mStartY = y;
                // 记录上一次触摸的坐标为当前坐标
                mLastY = y;
                mLastX = x;
                // 触发触摸开始事件
                if (mEventListener != null) {
                    mEventListener.onDown(x, y);
                }
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                // 判断是否为小幅移动，如果是则认为是停留在同一位置
                if (Math.abs(x - mStartX) < distance
                        && Math.abs(y - mStartY) < distance) {
                    if (mState == TouchState.STATE_STOP) {
                        break;
                    }
                } else {
                    // 如果不是小幅移动，则认为是开始拖动
                    if (mState != TouchState.STATE_MOVE) {
                        mState = TouchState.STATE_MOVE;
                    }
                }
                // 触发触摸移动事件
                if (mEventListener != null) {
                    mEventListener.onMove(mLastX, mLastY, x - mLastX, y - mLastY,false);
                }
                // 更新上一次触摸的坐标为当前坐标
                mLastY = y;
                mLastX = x;
                mState = TouchState.STATE_MOVE;
            }
            break;
            case MotionEvent.ACTION_UP: {
                // 触发触摸结束事件
                if (mEventListener != null) {
                    mEventListener.onUp(x, y);
                }
                // 如果不是在移动状态且满足点击时间间隔条件，则认为是点击事件
                if (mState != TouchState.STATE_MOVE
                        && event.getEventTime() - event.getDownTime() < MIN_TAP_TIME) {
                    v.performClick();
                }
                mState = TouchState.STATE_STOP;
            }
            break;
            default:
                break;
        }
        // 表示处理了触摸事件，防止冒泡到父视图
        return true;
    }


    /**
     * 触摸事件监听器接口
     */
    public interface OnTouchEventListener {
        /**
         * 触摸移动事件
         *
         * @param x 当前X坐标
         * @param y 当前Y坐标
         * @param dx 相对于上一次触摸的X方向位移
         * @param dy 相对于上一次触摸的Y方向位移
         * @param initPosition 是否为初始化位置
         */
        void onMove(int x, int y, int dx, int dy,boolean initPosition);

        /**
         * 触摸结束事件
         *
         * @param x 结束时的X坐标
         * @param y 结束时的Y坐标
         */
        void onUp(int x, int y);

        /**
         * 触摸开始事件
         *
         * @param x 开始时的X坐标
         * @param y 开始时的Y坐标
         */
        void onDown(int x, int y);
    }
}
