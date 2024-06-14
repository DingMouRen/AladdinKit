package com.dingmouren.aladdinkit.data;

import android.os.Handler;
import android.os.Looper;

import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;
import com.dingmouren.aladdinkit.widget.floatview.widget.FullScreenOkhttpLogFloatView;
import com.dingmouren.aladdinkit.widget.floatview.widget.OkhttpLogFloatView;
import com.dingmouren.aladdinkit.widget.floatview.widget.TVOkhttpLogFloatView;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {

    public static StringBuilder stringBuilderOkHttpLog = new StringBuilder();

    public static volatile List<String> listOkHttpLog = new ArrayList<>();
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    public static void appendOkHttpLog(String msg){
        sHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean hasOkhttpLogFloatView = FloatViewManager.hasFloatView(OkhttpLogFloatView.class);
                if (hasOkhttpLogFloatView){
                    stringBuilderOkHttpLog.append(msg+"\n");
                    FloatViewManager.notifyDataChange(OkhttpLogFloatView.class);
                }

                boolean hasTVOkhttpLogFloatView = FloatViewManager.hasFloatView(TVOkhttpLogFloatView.class);
                if (hasTVOkhttpLogFloatView ){
                    listOkHttpLog.add(msg);
                    FloatViewManager.notifyDataChange(TVOkhttpLogFloatView.class);
                }

                boolean hasFullOkhttpLogFloatView = FloatViewManager.hasFloatView(FullScreenOkhttpLogFloatView.class);
                if (hasFullOkhttpLogFloatView){
                    listOkHttpLog.add(msg);
                    FloatViewManager.notifyDataChange(FullScreenOkhttpLogFloatView.class);
                }
            }
        },100);


    }

    public static List<String> getOkHttpLogList(){
        return listOkHttpLog;
    }
    public static StringBuilder getOkHttpLogStringBuilder(){
        return stringBuilderOkHttpLog;
    }

}
