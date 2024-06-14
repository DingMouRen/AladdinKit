package com.dingmouren.aladdinkit.ui.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.constant.AdKitFunctionBoardConstant;
import com.dingmouren.aladdinkit.model.FunctionClassifyModel;
import com.dingmouren.aladdinkit.model.FunctionModel;
import com.dingmouren.aladdinkit.widget.ImageTextView;

public class AdKitFunBoardAdapter extends RecyclerView.Adapter<AdKitFunBoardAdapter.FunBoardVH> {

    private FunctionClickListener functionClickListener;

    @NonNull
    @Override
    public FunBoardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.item_adkit_fun_board,parent,false);
        return new FunBoardVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull FunBoardVH holder, int position) {
        FunctionClassifyModel classifyModel = AdKitFunctionBoardConstant.functionData.get(position);
        holder.tvTitle.setText(classifyModel.menuClassify);
        holder.recyclerView.setLayoutManager( new GridLayoutManager(AdKit.sApplication,4));
        holder.recyclerView.setNestedScrollingEnabled(true);
        holder.recyclerView.setHasFixedSize(true);
//        holder.recyclerView.addItemDecoration(new SpaceItemDecoration(30));
        holder.recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.item_adkit_fun,parent,false);
                return new FunVH(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                FunctionModel functionModel = classifyModel.functionModelList.get(position);
                FunVH funVH = (FunVH) holder;
                funVH.imageTextView.setDesc(functionModel.desc);
                funVH.imageTextView.setImageResource(functionModel.img);
                funVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != functionClickListener)functionClickListener.onClick(functionModel);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return classifyModel.functionModelList == null?0:classifyModel.functionModelList.size();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int getItemCount() {
        return AdKitFunctionBoardConstant.functionData.size();
    }

    public static class FunBoardVH extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public RecyclerView recyclerView;
        public FunBoardVH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            recyclerView = itemView.findViewById(R.id.recycler);
        }
    }

    public static class FunVH extends RecyclerView.ViewHolder{
        public ImageTextView imageTextView;
        public FunVH(@NonNull View itemView) {
            super(itemView);
            imageTextView = itemView.findViewById(R.id.imgText);
        }
    }

    public interface FunctionClickListener{
        void onClick(FunctionModel functionModel);
    }

    public void setFunctionClickListener(FunctionClickListener listener){
        functionClickListener = listener;
    }
}
