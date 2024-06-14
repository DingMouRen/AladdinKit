package com.dingmouren.aladdinkit.aop;

import android.os.Trace;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.core.AdKitReal;
import com.dingmouren.aladdinkit.utils.SingleHttpClient;
import com.dingmouren.aladdinkit.utils.ToastUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Aspect
public class WebViewClientAspectj {

    @Around("execution(* android.webkit.WebViewClient+.shouldInterceptRequest(..)) && args(view,request)")
    public Object shouldInterceptRequest(WebView view, WebResourceRequest request, ProceedingJoinPoint joinPoint) {
        try {
            if (AdKitReal.sIsEnableH5Debug) {
                if (TextUtils.isEmpty(AdKit.getCurrentHostAddress())){
                    ToastUtils.show(AdKit.sApplication.getResources().getString(R.string.ak_h5_debug_toggle_notice));
                    return null;
                }
                if (request.getUrl().toString().startsWith(AdKit.getCurrentHostAddress())) {
                    Response response = SingleHttpClient.getHttpClient().newCall(new Request.Builder().url(request.getUrl().toString()).build()).execute();
                    if (null != response.body()) {
                        ResponseBody responseBody = response.body();
                        MediaType mediaType = responseBody.contentType();
                        if (mediaType.toString().startsWith("text/html")) {
                            String html = response.body().string();
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
