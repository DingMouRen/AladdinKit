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
import com.dingmouren.aladdinkit.core.AdKitReal;

/**
 * H5调试模式开关
 */
public class AdKitH5DebugToggleActivity extends BaseActivity {

    private ImageView imgBack,imgToggle;
    private TextView tvTitle;
    private RelativeLayout rlLayout;

    public static void newInstance(Context context){
        Intent intent = new Intent(context,AdKitH5DebugToggleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_adkit_h5_debug_toggle;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        imgBack = findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.tvTitle);
        imgToggle = findViewById(R.id.imgToggle);
        rlLayout = findViewById(R.id.rlLayout);

        tvTitle.setText(getText(R.string.ak_h5_debug_title));

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
                AdKitReal.sIsEnableH5Debug = !AdKitReal.sIsEnableH5Debug;
                updateToggleUI();

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateToggleUI(){
        if (AdKitReal.sIsEnableH5Debug){
            imgToggle.setImageDrawable(getDrawable(R.drawable.ak_toggle_on));
        }else {
            imgToggle.setImageDrawable(getDrawable(R.drawable.ak_toggle_off));
        }
    }


}
