package com.example.group.dongdong.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by kongalong on 2017/1/9.
 */

public class SportsTrainPlanChoseView extends HorizontalScrollView {

    private int mWeekCount;

    private Context mContext;
    private View mView1;
    private View mView2;


    private int mChildWidth;
    private int mWidth;
    private int mDelta;
    private int mScrollX;


    public SportsTrainPlanChoseView(Context context) {
        super(context);
        init(context);
    }



    public SportsTrainPlanChoseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SportsTrainPlanChoseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }




    private void init(Context context) {

        mContext = context;

        mWeekCount = 8;


        LinearLayout llH = new LinearLayout(mContext);
        llH.setOrientation(LinearLayout.HORIZONTAL);


        mView1 = new View(mContext);
        llH.addView(mView1,new LinearLayout.LayoutParams(344,LayoutParams.MATCH_PARENT));

        for (int i = 0; i < mWeekCount; i++) {
            for (int i1 = 0; i1 < 3; i1++) {

                LinearLayout llV = new LinearLayout(mContext);
                llV.setOrientation(LinearLayout.VERTICAL);
                llV.setBackgroundColor(Color.parseColor("#eae9e9"));

                TextView weekText = new TextView(mContext);
                weekText.setText("第一周");
                Button choseBtn = new Button(mContext);
                choseBtn.setText("  ");
                TextView dayText = new TextView(mContext);
                dayText.setText("第一天");

                llV.addView(weekText);
                llV.addView(choseBtn);
                llV.addView(dayText);

                llH.addView(llV);
            }
        }
        mView2 = new View(mContext);
        llH.addView(mView2,new LinearLayout.LayoutParams(344,LayoutParams.MATCH_PARENT));
        this.addView(llH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mChildWidth = ((LinearLayout) getChildAt(0)).getChildAt(1).getMeasuredWidth();
        mWidth = getMeasuredWidth();
        LinearLayout.LayoutParams Params =  (LinearLayout.LayoutParams)mView1.getLayoutParams();
        Params.width = mWidth/2-mChildWidth/2;
        mView1.setLayoutParams(Params);

        mView2.setLayoutParams(Params);
    }
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY
            , int scrollX, int scrollY, int scrollRangeX
            , int scrollRangeY, int maxOverScrollX
            , int maxOverScrollY, boolean isTouchEvent) {

        mScrollX = scrollX;
       mDelta = deltaX;
        if(deltaX<=2&&deltaX>=-2){
            int i = (int) ((scrollX) % (mChildWidth));
            if(mTempView!=null){
                mTempView.setBackgroundColor(Color.parseColor("#eae9e9"));
            }

            if(i>mChildWidth/2){
                this.smoothScrollTo(scrollX+mChildWidth-i,33);

                int temp = (scrollX+mChildWidth-i)/mChildWidth;
                mTempView = ((LinearLayout)getChildAt(0)).getChildAt(temp+1);
                mTempView.setBackgroundColor(Color.parseColor("#dfdcdc"));
                mOnChoseListener.onChose(temp);
            }else{
                this.smoothScrollTo(scrollX-i,33);
                int temp = ((scrollX-i)/mChildWidth);

                mTempView = ((LinearLayout)getChildAt(0)).getChildAt(temp+1);
                mTempView.setBackgroundColor(Color.parseColor("#dfdcdc"));

                mOnChoseListener.onChose(temp);


            }

        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


    private View mTempView;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

       if(ev.getAction()==MotionEvent.ACTION_UP&&mDelta<=2&&mDelta>=-2){
           int i = (int) ((mScrollX) % (mChildWidth));

           if(mTempView!=null){
               mTempView.setBackgroundColor(Color.parseColor("#eae9e9"));
           }
           if(i>mChildWidth/2){
               this.smoothScrollTo(mScrollX+mChildWidth-i,33);
               int temp = ((mScrollX+mChildWidth-i)/mChildWidth);
               mTempView = ((LinearLayout)getChildAt(0)).getChildAt(temp+1);
               mTempView.setBackgroundColor(Color.parseColor("#dfdcdc"));
               mOnChoseListener.onChose(temp);


           }else{
               this.smoothScrollTo(mScrollX-i,33);
               int temp = ((mScrollX-i)/mChildWidth);

               mTempView = ((LinearLayout)getChildAt(0)).getChildAt(temp+1);
               mTempView.setBackgroundColor(Color.parseColor("#dfdcdc"));
               mOnChoseListener.onChose(temp);

           }
       }


        return super.onTouchEvent(ev);


    }

    private OnChoseListener mOnChoseListener;

    public void setOnChoseListener(OnChoseListener onChoseListener){

        this.mOnChoseListener = onChoseListener;

    }

    public interface OnChoseListener{

        void onChose(int position);

    }
}
