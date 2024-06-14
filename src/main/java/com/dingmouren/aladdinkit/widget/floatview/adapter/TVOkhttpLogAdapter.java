package com.dingmouren.aladdinkit.widget.floatview.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dingmouren.aladdinkit.R;
import com.dingmouren.aladdinkit.data.DataHolder;

import java.util.List;

public class TVOkhttpLogAdapter  extends RecyclerView.Adapter<TVOkhttpLogAdapter.TVOkhttpLogVH>{

   public TVOkhttpLogAdapter() {
   }

   @NonNull
   @Override
   public TVOkhttpLogVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tv_okhttp_log,parent,false);
      return new TVOkhttpLogVH(view);
   }

   @Override
   public void onBindViewHolder(@NonNull TVOkhttpLogVH holder, int position) {
      List<String> data = DataHolder.getOkHttpLogList();
      if (data.size() == 0)return;
      String content = data.get(position);
      if (!TextUtils.isEmpty(content)) holder.tv.setText(content);
   }

   @Override
   public int getItemCount() {
      return DataHolder.getOkHttpLogList().size();
   }

   static class TVOkhttpLogVH extends RecyclerView.ViewHolder{
      TextView tv;
      public TVOkhttpLogVH(@NonNull View itemView) {
         super(itemView);
         tv = itemView.findViewById(R.id.tv);
      }
   }
}
