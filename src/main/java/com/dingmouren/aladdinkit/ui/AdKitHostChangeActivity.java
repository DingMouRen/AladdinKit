package com.dingmouren.aladdinkit.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.base.BaseActivity;
import com.dingmouren.aladdinkit.core.AdKitReal;
import com.dingmouren.aladdinkit.core.AdKitSPKeys;
import com.dingmouren.aladdinkit.model.HostAddressModel;
import com.dingmouren.aladdinkit.ui.adapter.AdKitHostChangeAdapter;
import com.dingmouren.aladdinkit.utils.LogUtils;
import com.dingmouren.aladdinkit.utils.SpUtils;
import com.dingmouren.aladdinkit.utils.ToastUtils;
import com.dingmouren.aladdinkit.widget.AdKitDialogEdit;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import static com.dingmouren.aladdinkit.core.AdKitSPKeys.KEY_HOST_ADDRESSES;

/**
 * host地址切换
 */
public class AdKitHostChangeActivity extends BaseActivity {

    private RecyclerView recycler;
    private ImageView imgBack;
    private TextView tvTitle;
    private LinearLayout llAddHost;
    private AdKitDialogEdit dialogEdit;

    private AdKitHostChangeAdapter adapter;

    public static void newInstance(Context context){
        Intent intent = new Intent(context,AdKitHostChangeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_adkit_host_change;
    }

    @Override
    protected void initView() {
        recycler = findViewById(R.id.recycler);
        imgBack = findViewById(R.id.imgBack);
        tvTitle = findViewById(R.id.tvTitle);
        llAddHost = findViewById(R.id.llAddHost);

        dialogEdit = new AdKitDialogEdit(this,getResources().getString(R.string.ak_host_dialog_add_host_title));

        tvTitle.setText(getResources().getString(R.string.ak_host_title));

        initRecycler();

        initListener();
    }

    private void initListener() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llAddHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit.show();
            }
        });

        dialogEdit.setOnDialogClickListener(new AdKitDialogEdit.OnDialogClickListener() {
            @Override
            public void onClick(boolean confirm,String newHostAddress) {
                if (TextUtils.isEmpty(newHostAddress)){
                    ToastUtils.show("host地址不能未空");
                    return;
                }
                if (!newHostAddress.startsWith("http")){
                    ToastUtils.show("请输入正确的格式，比如http开头");
                    return;
                }
                if (confirm && !TextUtils.isEmpty(newHostAddress)){
                    HostAddressModel model = new HostAddressModel();
                    model.selected = true;
                    model.hostAddress = newHostAddress;
                    List<HostAddressModel> tempList = adapter.getList();
                    for (int i = 0; i < tempList.size(); i++) {
                        HostAddressModel temp = tempList.get(i);
                        temp.selected = false;
                    }
                    tempList.add(model);
                    adapter.notifyDataSetChanged();
                    //缓存新的测试环境
                    String jsonHostAddressed = new Gson().toJson(adapter.getList());

                    LogUtils.d("设置host地址环境："+jsonHostAddressed);

                    SpUtils.getInstance().put(KEY_HOST_ADDRESSES,jsonHostAddressed);

                    LogUtils.d("Host环境切换至:" + newHostAddress);

                    if (null != AdKitReal.sAdKitCallBack)AdKitReal.sAdKitCallBack.onHostAddressChanged(newHostAddress);
                }
            }
        });
    }


    @Override
    protected void initData() {
        adapter.setData(getHostAddressesList());
    }

    private void initRecycler() {
        adapter = new AdKitHostChangeAdapter();
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdKitHostChangeAdapter.OnItemClickListener() {
            @Override
            public void onClick(List<HostAddressModel> list,String hostAddress) {

                String jsonHostAddressed = new Gson().toJson(list);

                LogUtils.d("设置host地址环境："+jsonHostAddressed);

                SpUtils.getInstance().put(KEY_HOST_ADDRESSES,jsonHostAddressed);

               LogUtils.d("Host环境切换至:" + hostAddress);

                if (null != AdKitReal.sAdKitCallBack)AdKitReal.sAdKitCallBack.onHostAddressChanged(hostAddress);
            }
        });

    }

    private List<HostAddressModel> getHostAddressesList(){
        String json = SpUtils.getInstance().getString(AdKitSPKeys.KEY_HOST_ADDRESSES);
        List<HostAddressModel> list = new Gson().fromJson(json, new TypeToken<List<HostAddressModel>>(){}.getType());
        return list;
    }

}
