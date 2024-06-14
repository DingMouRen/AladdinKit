package com.dingmouren.aladdinkit.utils;

import android.widget.Toast;

import com.dingmouren.aladdinkit.core.AdKit;

public class ToastUtils {

    public static void show(String msg){
        Toast.makeText(AdKit.sApplication,msg,Toast.LENGTH_SHORT).show();
    }
}
