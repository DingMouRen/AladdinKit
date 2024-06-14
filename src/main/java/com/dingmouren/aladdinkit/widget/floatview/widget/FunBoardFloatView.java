package com.dingmouren.aladdinkit.widget.floatview.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.model.FunctionModel;
import com.dingmouren.aladdinkit.ui.adapter.AdKitFunBoardAdapter;
import com.dingmouren.aladdinkit.utils.SystemUtils;
import com.dingmouren.aladdinkit.utils.ToastUtils;
import com.dingmouren.aladdinkit.widget.floatview.AbsFloatView;

public class FunBoardFloatView extends AbsFloatView {

    private ImageView imgBack;
    private TextView tvTitle;
    private RecyclerView recycler;
    private AdKitFunBoardAdapter adKitFunBoardAdapter;
    private Context mContext;


    @Override
    public void onCreate(Context context) {

    }

    @Override
    public View onCreateView(Context context, FrameLayout rootView) {
        mContext = context;
        View view = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.activity_adkit_menu,rootView,false);
        return view;
    }

    @Override
    public void onViewCreated(FrameLayout rootView) {
        imgBack = rootView.findViewById(R.id.imgBack);
        tvTitle = rootView.findViewById(R.id.tvTitle);
        recycler = rootView.findViewById(R.id.recycler);

        tvTitle.setText(AdKit.sApplication.getText(R.string.lib_name));

        initListener();

        initRecycler();
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdKit.removeFloatView(FunBoardFloatView.class);
            }
        });
    }

    @Override
    public void initLayoutParams(FrameLayout.LayoutParams params) {
        params.width = FrameLayout.LayoutParams.MATCH_PARENT;
        params.height = FrameLayout.LayoutParams.MATCH_PARENT;
        params.topMargin = SystemUtils.getStatusBarHeight(mContext);
    }

    private void initRecycler() {
        adKitFunBoardAdapter = new AdKitFunBoardAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(mContext));
        recycler.setAdapter(adKitFunBoardAdapter);
        adKitFunBoardAdapter.setFunctionClickListener(new AdKitFunBoardAdapter.FunctionClickListener() {
            @Override
            public void onClick(FunctionModel functionModel) {
                ToastUtils.show(functionModel.toString());
            }
        });
    }




}
