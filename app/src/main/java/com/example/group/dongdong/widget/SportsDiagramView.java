package com.example.group.dongdong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kongalong on 2017/1/4.
 */

public class SportsDiagramView extends View {

    private Paint mPaint;

    //存条形图数据
    private int[] mDatas = new int[19];

    //默认刻度
    private int mMidCount = 50;
    private int mMaxCount = mMidCount*2;

    //横线的长度
    private int mLineLength;
    //横线的边距
    private int mLineMarginLeft = 80;
    private int mLineMarginRight = 40;
    private int mLineMarginTop = 40;
    private int mLineMarginBottom = 40;

    //view的长宽
    private int mWidth;
    private int mHeight;

    //间隔
    private float mRowGap;
    private float mColunmGap;
    //刻度
    private float mScaleGap;




    public SportsDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public SportsDiagramView(Context context) {
        super(context);
        init();
    }

    public SportsDiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);

        mDatas[0] = 11;
        mDatas[1] = 411;
        mDatas[2] = 44;
        mDatas[3] = 22;


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mRowGap = (mHeight - mLineMarginTop - mLineMarginBottom) / 2;

        mLineLength = mWidth - mLineMarginLeft - mLineMarginRight;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //算出最大步数
        int max = mDatas[0];
        for (int i = 1; i < mDatas.length; i++) {
            if(max<mDatas[i]){
                max = mDatas[i];
            }
        }

        mMaxCount = (max/100+1)*100;
        mMidCount = mMaxCount/2;

        mColunmGap = mLineLength / 4;

        mScaleGap = mColunmGap / 9;


        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(2);
        canvas.drawLine(mLineMarginLeft,mLineMarginTop
                ,mWidth-mLineMarginRight,mLineMarginTop,mPaint);

        canvas.drawLine(mLineMarginLeft,mLineMarginTop+mRowGap
                ,mWidth-mLineMarginRight,mLineMarginTop+mRowGap,mPaint);

        mPaint.setStrokeWidth(4);
        canvas.drawLine(mLineMarginLeft,mLineMarginTop+mRowGap*2
                ,mWidth-mLineMarginRight,mLineMarginTop+mRowGap*2,mPaint);


        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStrokeWidth(2);
        mPaint.setTextSize(30);
        canvas.drawText(String.valueOf(mMaxCount),mLineMarginLeft-30,mLineMarginTop,mPaint);
        canvas.drawText(String.valueOf(mMidCount),mLineMarginLeft-30,mLineMarginTop+mRowGap,mPaint);


        canvas.drawText("06:00",mLineMarginLeft + mColunmGap,mLineMarginTop+mRowGap*2+30,mPaint);
        canvas.drawText("12:00",mLineMarginLeft + mColunmGap*2,mLineMarginTop+mRowGap*2+30,mPaint);
        canvas.drawText("18:00",mLineMarginLeft + mColunmGap*3,mLineMarginTop+mRowGap*2+30,mPaint);


        mPaint.setColor(Color.parseColor("#FF4EA6FF"));
        mPaint.setStrokeWidth(15);

        for (int i = 0; i < mDatas.length; i++) {

            float height = (float)mDatas[i] / mMaxCount * (mRowGap * 2);
                canvas.drawLine(mLineMarginLeft + mColunmGap + mScaleGap*i
                    ,mLineMarginTop+mRowGap*2
                    ,mLineMarginLeft + mColunmGap + mScaleGap*i
                    ,mLineMarginTop+mRowGap*2-height, mPaint);

        }

    }

}
