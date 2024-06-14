package com.dingmouren.aladdinkit.ui.adapter;

import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.core.AdKit;
import com.dingmouren.aladdinkit.model.HostAddressModel;

import java.util.List;

public class AdKitHostChangeAdapter extends RecyclerView.Adapter<AdKitHostChangeAdapter.HostVH> {

    private List<HostAddressModel> mList;
    private OnItemClickListener onItemClickListener;

    public void setData(List<HostAddressModel> list){
        if (null != list){
            mList = list;
            notifyDataSetChanged();
        }
    }

    public List<HostAddressModel> getList() {
        return mList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public HostVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(AdKit.sApplication).inflate(R.layout.item_adkit_host,parent,false);
        return new HostVH(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull HostVH holder, int position) {
        if (null == mList || mList.size() <=0) return;
        HostAddressModel model = mList.get(position);
        if (null == model) return;
        if (model.selected){
            holder.img.setImageDrawable(AdKit.sApplication.getDrawable(R.drawable.ak_selected));
        }else {
            holder.img.setImageDrawable(AdKit.sApplication.getDrawable(R.drawable.ak_unselected));
        }
        holder.tvTitle.setText(TextUtils.isEmpty(model.hostAddress)?"":model.hostAddress);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < mList.size(); i++) {
                    HostAddressModel temp = mList.get(i);
                    temp.selected = false;
                }
                model.selected = true;
                notifyDataSetChanged();

                if (null != onItemClickListener) onItemClickListener.onClick(mList,model.hostAddress);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == mList?0:mList.size();
    }

    static class HostVH extends RecyclerView.ViewHolder{
        public ImageView img;
        public TextView tvTitle;
        public HostVH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    public interface OnItemClickListener{
        void onClick(List<HostAddressModel> list,String hostAddress);
    }
}
