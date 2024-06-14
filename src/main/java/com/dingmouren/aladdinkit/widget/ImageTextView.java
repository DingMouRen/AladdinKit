package com.dingmouren.aladdinkit.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dingmouren.aladdinkit.R;

public class ImageTextView extends RelativeLayout {
    private ImageView img;
    private TextView tvDesc;
    private RelativeLayout layoutRoot;
    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }
    //初始化UI，可根据业务需求设置默认值。
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_adkit_image_text, this,
                true);
        img = findViewById(R.id.img);
        tvDesc = findViewById(R.id.tvDesc);
        layoutRoot = findViewById(R.id.layoutRoot);
    }
    //设置标题文字的方法
    public void setDesc(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvDesc.setText(title);
        }
    }

    public void setImageResource(Drawable drawable){
        img.setImageDrawable(drawable);
    }

    public void setOnClickListener( OnClickListener listener){
        layoutRoot.setOnClickListener(listener);
    }


}
