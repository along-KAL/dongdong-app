package com.example.group.dongdong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kongalong on 2017/1/6.
 */

public class SportsTrainPlanScheduleView extends View {


    private Paint mPaint;

    private int mWidth;
    private int mHeight;

    private long mCompleteTime;
    private long mTotalTime;


    public SportsTrainPlanScheduleView(Context context) {
        super(context);
        init();
    }


    public SportsTrainPlanScheduleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SportsTrainPlanScheduleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mCompleteTime = 0;
        mTotalTime = 0;
    }

    public void setTotalTime(long totalTime) {
        mTotalTime = totalTime;
        invalidate();
    }

    public void setCompleteTime(long completeTime) {
        mCompleteTime = completeTime;

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画进度条
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.parseColor("#c9c4c2"));
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(20,mHeight,20,0,mPaint);
        canvas.drawLine(mWidth-20,mHeight,mWidth-20,0,mPaint);

        mPaint.setColor(Color.parseColor("#fc7c49"));
        canvas.drawLine(20,mHeight,20,mHeight-(float)mCompleteTime/mTotalTime*mHeight,mPaint);
        canvas.drawLine(mWidth-20,mHeight,mWidth-20,(float)mCompleteTime/mTotalTime*mHeight,mPaint);


        //画两边的字
        mPaint.setTextSize(50);
        mPaint.setColor(Color.parseColor("#76f27c"));
        mPaint.setTextAlign(Paint.Align.LEFT);

        //计算文字的高度
        Rect rect1 = new Rect();
        mPaint.getTextBounds("剩余", 0, "剩余".length(), rect1);
        int h1 = rect1.height();
        canvas.drawText("已完成",30,h1,mPaint);

        mPaint.setColor(Color.parseColor("#fc7c49"));
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("剩余",mWidth-30,h1,mPaint);


        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(70);

        Rect rect2 = new Rect();
        mPaint.getTextBounds("剩余", 0, "剩余".length(), rect2);
        int h2 = rect2.height();

        //格式化时间
        SimpleDateFormat format1=new SimpleDateFormat("mm:ss");
        Date d1=new Date(mCompleteTime);

        canvas.drawText(format1.format(d1),30,mHeight-10,mPaint);

        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(70);
        SimpleDateFormat format2=new SimpleDateFormat("mm:ss");
        Date d2=new Date(mTotalTime - mCompleteTime);
        canvas.drawText(format2.format(d2),mWidth-30,mHeight-10,mPaint);


    }
}
