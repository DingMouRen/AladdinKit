package com.dingmouren.aladdinkit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.base.BaseActivity;
import com.dingmouren.aladdinkit.model.FunctionModel;
import com.dingmouren.aladdinkit.ui.adapter.AdKitFunBoardAdapter;

import static com.dingmouren.aladdinkit.constant.AdKitFunctionBoardConstant.TAG_BUS_1_CHANGE_HOST;
import static com.dingmouren.aladdinkit.constant.AdKitFunctionBoardConstant.TAG_BUS_2_H5_DEBUG;
import static com.dingmouren.aladdinkit.constant.AdKitFunctionBoardConstant.TAG_BUS_3_OKHTTP_LOG;

public class AdKitFunBoardActivity extends BaseActivity {

    private ImageView imgBack;
    private TextView tvTitle;
    private RecyclerView recycler;
    private AdKitFunBoardAdapter adKitFunBoardAdapter;

    public static void newInstance(Context context){
        Intent intent = new Intent(context,AdKitFunBoardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    @Override
    public int provideLayoutId() {
        return R.layout.activity_adkit_menu;
    }

    @Override
    public void initView() {
        imgBack = findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.tvTitle);
        recycler = findViewById(R.id.recycler);

        tvTitle.setText(getText(R.string.lib_name));

        iniListener();

        initRecycler();
    }

    private void initRecycler() {
        adKitFunBoardAdapter = new AdKitFunBoardAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adKitFunBoardAdapter);
        adKitFunBoardAdapter.setFunctionClickListener(new AdKitFunBoardAdapter.FunctionClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(FunctionModel functionModel) {
                clickDispatch(functionModel);
            }
        });
    }


    private void iniListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void initData() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void clickDispatch(FunctionModel functionModel) {
        String type = functionModel.desc;
        if (TextUtils.equals(type,TAG_BUS_1_CHANGE_HOST)){
            AdKitHostChangeActivity.newInstance(AdKitFunBoardActivity.this);
        }else if (TextUtils.equals(type,TAG_BUS_2_H5_DEBUG)){
            AdKitH5DebugToggleActivity.newInstance(AdKitFunBoardActivity.this);
        }else if (TextUtils.equals(type,TAG_BUS_3_OKHTTP_LOG)){
            AdKitOkHttpLogActivity.newInstance(AdKitFunBoardActivity.this);
        }
        finish();
    }

}
