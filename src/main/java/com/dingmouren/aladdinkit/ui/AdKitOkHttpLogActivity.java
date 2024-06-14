package com.dingmouren.aladdinkit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.base.BaseActivity;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.core.AdKitReal;
import com.dingmouren.aladdinkit.widget.floatview.widget.OkhttpLogFloatView;

/**
 * OkHttpLog开关页面
 */
public class AdKitOkHttpLogActivity extends BaseActivity {

    private ImageView imgBack,imgToggle;
    private TextView tvTitle;
    private RelativeLayout rlLayout;

    public static void newInstance(Context context){
        Intent intent = new Intent(context, AdKitOkHttpLogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_adkit_okhttp_log;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.tvTitle);
        imgToggle = findViewById(R.id.imgToggle);
        rlLayout = findViewById(R.id.rlLayout);

        tvTitle.setText(getText(R.string.ak_okhttp_log_title));

        updateToggleUI();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdKitReal.sIsShowOkhttpLogFloatView = !AdKitReal.sIsShowOkhttpLogFloatView;
                updateToggleUI();
                showOkHttpLogFloatView();
            }
        });

    }

    private void showOkHttpLogFloatView() {
        if (AdKitReal.sIsShowOkhttpLogFloatView){
            AdKit.addFloatView(OkhttpLogFloatView.class);
        }else {
            AdKit.removeFloatView(OkhttpLogFloatView.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateToggleUI(){
        if (AdKitReal.sIsShowOkhttpLogFloatView){
            AdKit.addFloatView(OkhttpLogFloatView.class);
            imgToggle.setImageDrawable(getDrawable(R.drawable.ak_toggle_on));
        }else {
            AdKit.removeFloatView(OkhttpLogFloatView.class);
            imgToggle.setImageDrawable(getDrawable(R.drawable.ak_toggle_off));
        }
    }


}
