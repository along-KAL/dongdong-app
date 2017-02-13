package com.example.group.dongdong.module.sports.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kongalong on 2017/1/3.
 */
public class SportsPagerAdapter extends PagerAdapter {


    private List<View> mViews;
    public SportsPagerAdapter(Context context, List<View> views) {

        this.mViews = views;
    }

    private int mChildCount = 0;

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object)   {
        if ( mChildCount > 0) {
            mChildCount --;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView((View) object);//删除页卡
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡

        //container.removeView(mViews.get(position));
        container.addView(mViews.get(position));//添加页卡
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return  mViews.size();//返回页卡的数量
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0==arg1;//官方提示这样写
    }

}
