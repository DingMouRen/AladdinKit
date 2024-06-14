package com.dingmouren.aladdinkit.core;

import android.app.Application;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.dingmouren.aladdinkit.data.DataHolder;
import com.dingmouren.aladdinkit.utils.ActivityUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;
import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;
import com.dingmouren.aladdinkit.widget.floatview.widget.IconFloatView;

import java.util.List;

public class AdKit {

    public static Application sApplication;

    /**
     * AdKit显示主Icon
     */
    public static void show(){
        AdKitReal.addFloatView(IconFloatView.class);
    }

    /**
     * AdKit隐藏主Icon
     */
    public static void hide(){
        AdKitReal.removeFloatView(IconFloatView.class);
    }

    /**
     * 添加悬浮窗
     * @param clazz
     */
    public static void addFloatView(Class<? extends AbsFloatView> clazz){
        AdKitReal.addFloatView(clazz);
    }

    /**
     * 移除悬浮窗
     * @param clazz
     */
    public static void removeFloatView(Class<? extends AbsFloatView> clazz){
        AdKitReal.removeFloatView(clazz);
    }

    /**
     * 获取当前host地址
     * @return
     */
    public static String getCurrentHostAddress(){
        return AdKitReal.getCurrentHostAddress();
    }

    /**
     * H5调试模式开关, 侵入性方案
     * @param view
     * @param request
     * @return
     */
    public static WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request){
        return AdKitReal.shouldInterceptRequest(view,request);
    }

    /**
     * Okhttp的请求日志信息
     * @param msg
     */
    public static void appendOkHttpLog(String msg){
        DataHolder.appendOkHttpLog(msg);
    }





    public static class Builder{

        public Builder(Application application) {
            sApplication = application;
        }

        public void build(){
            AdKitReal.init(sApplication);
        }

        public Builder setDebug(boolean isDebug){
            AdKitReal.setDebug(isDebug);
            return this;
        }

        public Builder setHostAddress(String[] hostAddresses){
            AdKitReal.setHostAddress(hostAddresses);
            return this;
        }

        public Builder callBack(AdKitCallBack adKitCallBack){
            AdKitReal.setCallBack(adKitCallBack);
            return this;
        }
    }





}
