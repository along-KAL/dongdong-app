package com.example.group.dongdong.module.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.group.teamproject2.R;

import static com.example.group.dongdong.commom.Contants.Constant.MSG_FROM_SERVER;

/**
 * Created by Administrator on 2017/1/13.
 */
public class MyRecordAdapter extends RecyclerView.Adapter{
    private Context mContext;
    public MyRecordAdapter(Context applicationContext) {
        mContext=applicationContext;
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return MSG_FROM_SERVER;
        }

        return MSG_FROM_SERVER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View ret=null;
        if(viewType==0){
            ret= LayoutInflater.from(mContext).inflate(R.layout.record_item,parent,false);
        }else {
            ret= LayoutInflater.from(mContext).inflate(R.layout.record_item,parent,false);
        }
        return new MyHolder(ret);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MyHolder){
            ((MyHolder) holder).img.setImageResource(R.mipmap.ic_launcher);
            ((MyHolder) holder).kilo.setText("1330m");
            ((MyHolder) holder).time.setText("2017年1月13日");
            ((MyHolder) holder).type.setText("时间");
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        private TextView type,time,kilo;
        private ImageView img;
        public MyHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.record_img);
            type= (TextView) itemView.findViewById(R.id.record_type);
            time= (TextView) itemView.findViewById(R.id.record_time);
            kilo= (TextView) itemView.findViewById(R.id.record_kilo);
        }
    }
}
