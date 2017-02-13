package com.example.group.dongdong.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kongalong on 2017/1/3.
 */

public class SportsTextView extends ViewGroup {

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;

    private int mWidth;


    public SportsTextView(Context context) {
        super(context);
        init(context);
    }



    public SportsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }



    public SportsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }



    private void init(Context context) {

    //    this.setOrientation(VERTICAL);

        mTextView1 = new TextView(context);
        mTextView2 = new TextView(context);
        mTextView3 = new TextView(context);

        mTextView3.setAlpha(0);

        mTextView1.setText("大卡");
        mTextView2.setText("0h 9m");
        mTextView3.setText("今日大卡");

        mTextView1.setGravity(Gravity.CENTER);
        mTextView2.setGravity(Gravity.CENTER);
        mTextView3.setGravity(Gravity.CENTER);


        mTextView2.setTextColor(Color.parseColor("#FF4EA6FF"));
        mTextView2.setTextSize(26);
        TextPaint tp = mTextView2.getPaint();
        tp.setFakeBoldText(true);


        this.addView(mTextView1);
        this.addView(mTextView2);
        this.addView(mTextView3);



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int count = getChildCount();
        for (int i = 0; i < count; i++){
            // 测量child
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }

        mWidth = getWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        for (int i = 0, size = getChildCount(); i < size; i++) {

            View child = getChildAt(i );
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();

            if(i == 2){
                child.layout((mWidth/2-cWidth/2), 0, (mWidth/2-cWidth/2) + cWidth, 0 + cHeight);
            }else{
                // 放置子View，宽高都是100
                child.layout((mWidth/2-cWidth/2), t, (mWidth/2-cWidth/2) + cWidth, t + cHeight);
                t += cHeight;
            }


        }

    }

    public void setTextView1(String text){
        mTextView1.setText(text);
    }

    public void setTextView2(String text){
        mTextView2.setText(text);
    }

    public void setTextColor(int color){
        mTextView2.setTextColor(color);
    }

    public void setTextView1Alpha(float alpha){

        mTextView1.setAlpha(alpha);
    }
    public void setTextView3Alpha(float alpha){

        mTextView3.setAlpha(alpha);
    }

    public void setTextView3(String text){
        mTextView3.setText(text);
    }

}
