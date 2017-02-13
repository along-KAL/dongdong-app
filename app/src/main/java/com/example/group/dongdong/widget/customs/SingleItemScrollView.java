package com.example.group.dongdong.widget.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/1/17.
 */

public class SingleItemScrollView extends ScrollView implements View.OnClickListener{
    private Context mContext;
    private int mScreenHeight;
    private boolean flag;
    private Adapter mAdapter;
    //每个条目的高度
    private int mItemHeight;
    private ViewGroup mContainer;
    //获取的条目总数
    private int mItemCont;

    private OnItemClickListener mListener;


    public SingleItemScrollView(Context context) {
        this(context,null);
    }

    public SingleItemScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenHeight = metrics.heightPixels;
        mScreenHeight-=getStatusHeight(context);

    }

//    public SingleItemScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        mContext=context;
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics metrics = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(metrics);
//        mScreenHeight = metrics.heightPixels;
//        mScreenHeight-=getStatusHeight(context);
//    }

    private int getStatusHeight(Context context) {
        int statusHeight=-1;

        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!flag){
            Log.i("TAG", "----------->flag:" +flag);
           mContainer= (ViewGroup) getChildAt(0);
            Log.i("TAG", "----------->mContainer1:" +mContainer);
            //根据adapter的方法，为容器添加item
            if(mAdapter!=null){
                mItemCont=mAdapter.getCount();
                mItemHeight=mScreenHeight/mItemCont;
//                mContainer.removeAllViews();
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    addChildView(i);
                }
            }
            addChildView(0);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void addChildView(int i) {
       /* View item = mAdapter.getView(this, i);
        android.view.ViewGroup.LayoutParams params = new ViewGroup.LayoutParams
                (android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                mItemHeight);
        item.setLayoutParams(params);
        //设置标签
        item.setTag(i);
        //设置事件
        item.setOnClickListener(this);
        mContainer.addView(item);*/
    }

    private void addChildView(int i,int index){
        View item = mAdapter.getView(this, i);
        android.view.ViewGroup.LayoutParams params = new ViewGroup.LayoutParams
                (android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                        mItemHeight);
        item.setLayoutParams(params);
        //设置标签
        item.setTag(i);
        //设置事件
        item.setOnClickListener(this);
        mContainer.addView(item,index);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        flag=true;

        int action = ev.getAction();
        float scaleY = getScaleY();
        switch (action){
            case MotionEvent.ACTION_MOVE:
                if(scaleY==0){
                    addChildToFirst();
                }

                if(Math.abs(scaleY-mItemHeight)<=mItemCont){
                    addChildToLast();
                }
                break;

            case MotionEvent.ACTION_UP:
                checkForReset();
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        int pos = (Integer) v.getTag();
        if(mListener!=null){
            mListener.onItemClick(pos,v);
        }
    }

    /**
     * 检查当前getScrollY,显示完成Item，或者收缩此Item
     */
    private void checkForReset()
    {
        int val = getScrollY() % mItemHeight;
        if (val >= mItemHeight / 2)
        {
            smoothScrollTo(0, mItemHeight);
        } else
        {
            smoothScrollTo(0, 0);
        }
    }


    /**
     * 在顶部添加一个View，并移除最后一个View
     */
    protected void addChildToFirst()
    {
        Log.e("TAG", "addChildToFirst");
        int pos = (Integer) mContainer.getChildAt(mItemCont - 1).getTag();
        Log.i("TAG", "----------->mContainer2:" +mContainer.getChildCount());
        addChildView(pos, 0);
        mContainer.removeViewAt(mContainer.getChildCount() - 1);
        this.scrollTo(0, mItemHeight);
    }

    /**
     * 在底部添加一个View，并移除第一个View
     */
    private void addChildToLast()
    {
        Log.e("TAG", "addChildToLast");
        int pos = (Integer) mContainer.getChildAt(1).getTag();
        addChildView(pos);
        mContainer.removeViewAt(0);
        this.scrollTo(0, 0);
    }

    /**
     * 适配器
     */
    public static abstract class Adapter{
        public abstract View getView(SingleItemScrollView parent,int position);
        public abstract int getCount();
    }

    public void setAdapter(Adapter adapter) {
        this.mAdapter = adapter;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 点击回调
     */
    public interface OnItemClickListener{
        void onItemClick(int position,View view);
    }

}
