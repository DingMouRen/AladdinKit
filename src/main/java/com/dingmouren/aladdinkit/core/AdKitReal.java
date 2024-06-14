package com.dingmouren.aladdinkit.core;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.dingmouren.aladdinkit.model.HostAddressModel;
import com.dingmouren.aladdinkit.utils.LogUtils;
import com.dingmouren.aladdinkit.utils.SingleHttpClient;
import com.dingmouren.aladdinkit.utils.SpUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;
import com.dingmouren.aladdinkit.widget.floatview.FloatViewManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.dingmouren.aladdinkit.core.AdKitSPKeys.KEY_HOST_ADDRESSES;

import androidx.annotation.RequiresApi;

public class AdKitReal {

    public static Application sApplication;

    public static boolean sInited;//是否初始化

    public static AdKitCallBack sAdKitCallBack;//AdKit的回调

    public static boolean sIsEnableH5Debug = false;//H5调试模式是否开启

    public static boolean sIsShowOkhttpLogFloatView = false;//okhttp悬浮窗是否显示

    /**是否是调试模式*/
    public static boolean sIsDebug = true;

    public static void init(Application application){
        sApplication = application;
        sApplication.registerActivityLifecycleCallbacks(new AdKitActivityLifecycleCallbacks());
        sInited = true;
    }

    /**
     * 添加悬浮窗
     * @param clazz
     */
    public static void addFloatView(Class<? extends AbsFloatView> clazz){
        FloatViewManager.attach(clazz);
    }

    /**
     * 移除悬浮窗
     * @param clazz
     */
    public static void removeFloatView(Class<? extends AbsFloatView> clazz){
        FloatViewManager.detach(clazz);
    }


    public static void setDebug(boolean isDebug) {
        sIsDebug = isDebug;
    }

    /**
     * 设置host地址环境
     * @param hostAddresses
     */
    public static void setHostAddress(String[] hostAddresses) {
        if (null == hostAddresses || hostAddresses.length <=0){
            LogUtils.e("host环境不能为空");
        }else {

            String json = SpUtils.getInstance().getString(AdKitSPKeys.KEY_HOST_ADDRESSES);
            if (TextUtils.isEmpty(json)){
                List<HostAddressModel> list = new ArrayList<>();
                for (int i = 0; i < hostAddresses.length; i++) {
                    String hostAddress = hostAddresses[i];
                    HostAddressModel temp = new HostAddressModel();
                    temp.hostAddress = hostAddress;
                    if (i == 0){
                        temp.selected = true;
                    }else {
                        temp.selected = false;
                    }
                    list.add(temp);
                }

                String jsonHostAddressed = new Gson().toJson(list);

                LogUtils.d("初始化设置host地址环境："+jsonHostAddressed);

                SpUtils.getInstance().put(KEY_HOST_ADDRESSES,jsonHostAddressed);
            }

        }
    }

    /**
     * 获取当前host地址
     * @return
     */
    public static String getCurrentHostAddress() {
        String hostCurrent = "";
        String json = SpUtils.getInstance().getString(AdKitSPKeys.KEY_HOST_ADDRESSES);
        List<HostAddressModel> list = new Gson().fromJson(json, new TypeToken<List<HostAddressModel>>(){}.getType());
        if (null != list){
            for(HostAddressModel model:list){
                if (model.selected)hostCurrent = model.hostAddress;
            }
        }
        return hostCurrent;
    }

    /**
     * 设置AdKit的回调
     * @param adKitCallBack
     */
    public static void setCallBack(AdKitCallBack adKitCallBack) {
        sAdKitCallBack = adKitCallBack;
    }

    /**
     * H5调试模式开关, 侵入性方案
     * @param view
     * @param request
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Deprecated
    public static WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if (sIsEnableH5Debug) {
            try {
                if (request.getUrl().toString().startsWith(AdKit.getCurrentHostAddress())) {
                    Response response = SingleHttpClient.getHttpClient().newCall(new Request.Builder().url(request.getUrl().toString()).build()).execute();
                    if (null != response.body()) {
                        ResponseBody responseBody = response.body();
                        MediaType mediaType = responseBody.contentType();
                        if (null != mediaType && mediaType.toString().startsWith("text/html")) {
                            String html = response.body().string().trim();
                            if (html.endsWith("</html>")) {
                                String interceptHeader = "<script src=\"https://unpkg.com/vconsole@latest/dist/vconsole.min.js\"></script>\n<script> var vConsole = new window.VConsole();</script>";
                                Document document = Jsoup.parse(html);
                                document.outputSettings().prettyPrint(true);
                                Elements element = document.getElementsByTag("head");
                                if (element.size() > 0) {
                                    element.get(0).prepend(interceptHeader);
                                }
                                byte[] pageContentsNew = document.toString().getBytes();
                                InputStream inputStream = new ByteArrayInputStream(pageContentsNew);
                                return new WebResourceResponse("text/html", "UTF-8", inputStream);
                            }

                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
