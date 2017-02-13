package com.example.group.dongdong.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kongalong on 2017/1/3.
 */

public class SportsStepCountView extends View {


    private Paint mPaint;

    //view的宽高
    private int mWidth;
    private int mHeight;
    //半径
    private float mRadius;

    //当前的刻度值
    private int mCurrentCount;
    //总的角度
    private int mAngle = 270;
    //刻度间隔
    private float mAngleGap = 2.7f;

    //需要从用户获取的数值数据
    private int mTodyStepCountText;
    private int mTargetCountText;
    private String mGradeText;

    private boolean mSign = true;


    public SportsStepCountView(Context context) {
        super(context);



        init(context);
    }



    public SportsStepCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SportsStepCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void init(Context context) {
        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);


        mRadius = 350.0f;


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mRadius = Math.min(mWidth/2,mHeight/2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate(-mAngle/2);

        for (int i = 0; i <= mAngle/2.7; i++) {

            if(mSign&&i>mCurrentCount){
                mSign = false;
                //画指针
                canvas.rotate(-mAngleGap);
                Path path = new Path();
                path.moveTo(0,-mRadius);
                path.lineTo(15,-mRadius-35);
                path.lineTo(-15,-mRadius-35);
                path.close();
                mPaint.setColor(Color.parseColor("#FF4EA6FF"));
                canvas.drawPath(path,mPaint);
                i--;
                //
                mPaint.setColor(Color.GRAY);
                mPaint.setStrokeWidth(2);
            }
            canvas.drawLine(0,-mRadius,0,-mRadius+40,mPaint);
            canvas.rotate(mAngleGap);

        }
        //重置
        mSign = true;
        canvas.restore();



        canvas.save();
        canvas.translate(mWidth/2,mHeight/2);

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(50);
        canvas.drawText("今日步数", 0,-150,mPaint);

        mPaint.setColor(Color.parseColor("#FF4EA6FF"));
        mPaint.setTextSize(170);
        canvas.drawText(String.valueOf(mTodyStepCountText), 0,30,mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(40);
        canvas.drawText("目标："+mTargetCountText, 0,140,mPaint);

        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(40);
        canvas.drawText("等级："+mGradeText, 0,200,mPaint);

        canvas.restore();

    }


    public void setTargetCountText(int targetCountText) {
        mTargetCountText = targetCountText;
        invalidate();
    }

    public void setTodyStepCountText(int todyStepCountText) {
        mTodyStepCountText = todyStepCountText;

        mCurrentCount = mTodyStepCountText / 100;


        invalidate();
    }

    public void setGradeText(String gradeText) {
        mGradeText = gradeText;
        invalidate();
    }

}
