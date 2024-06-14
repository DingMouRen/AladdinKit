package com.dingmouren.aladdinkit.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preOnCreate();
        if (provideLayoutId() != 0) setContentView(provideLayoutId());
        initView();
        initData();
    }

    private void preOnCreate() { }

    protected abstract int provideLayoutId();

    protected abstract void initView();

    protected  void initData(){};



    protected void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


}
