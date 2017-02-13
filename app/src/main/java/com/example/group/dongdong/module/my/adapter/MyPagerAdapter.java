package com.example.group.dongdong.module.my.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */
public class MyPagerAdapter extends PagerAdapter{
    private Context mContext;
    private List<View> mData;

    public MyPagerAdapter(Context context, List<View> vData) {
        this.mContext=context;
        this.mData=vData;
    }

    @Override
    public int getCount() {
        return mData!=null?mData.size():0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View ret = mData.get(position);
        container.addView(ret);
        return ret;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(mData.get(position));
    }

}
