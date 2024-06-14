package com.dingmouren.aladdinkit.utils;

import okhttp3.OkHttpClient;

public class SingleHttpClient {

    private static OkHttpClient sOkHttpClient = new OkHttpClient();

    public static OkHttpClient getHttpClient(){
        return sOkHttpClient;
    }
}
