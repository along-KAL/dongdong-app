package com.example.group.dongdong.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.ScaleAnimation;

import com.example.group.teamproject2.R;

/**
 * Created by kongalong on 2017/1/3.
 */

public class SportsBeginTrainView extends View {

    private Paint mPaint;

    //
    private int mWidth;
    private int mHeight;

    private float radius;

    private Bitmap mBitmap1 = null;
    private Bitmap mBitmap2 = null;

    private String mText;


    public SportsBeginTrainView(Context context) {
        super(context);



        init(context);
    }



    public SportsBeginTrainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SportsBeginTrainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void init(Context context) {
        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.FILL);


        radius = 350.0f;

        mBitmap1 = ((BitmapDrawable)getResources()
                .getDrawable(R.mipmap.icon_fire_white)).getBitmap();

        mBitmap2 = ((BitmapDrawable)getResources()
                .getDrawable(R.mipmap.icon_start_btn_round_orange)).getBitmap();


        mText = "--";
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        radius = Math.min(mWidth/2,mHeight/2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mWidth/2,mHeight/2,radius,mPaint);


        canvas.drawBitmap(mBitmap1, mWidth/2- mBitmap1.getWidth()/2,mHeight/2-mBitmap1.getHeight()/2-40, null);
        canvas.drawBitmap(mBitmap2, mWidth/2- mBitmap2.getWidth()/2+60,mHeight/2-20, null);

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(70);
        mPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText("开始",mWidth/2,mHeight/2+160,mPaint);

        mPaint.setTextSize(40);

        canvas.drawText(mText,mWidth/2,mHeight/2+220,mPaint);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {



        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:
                Animation scaleAnimation1 = new ScaleAnimation(1f, 0.9f, 1f, 0.9f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation1.setInterpolator(new AnticipateInterpolator());
                scaleAnimation1.setDuration(200);
                scaleAnimation1.setFillAfter(true);
                this.startAnimation(scaleAnimation1);
               // return true;
              /*  scaleAnimation.setRepeatCount(1);//设置重复次数
                scaleAnimation.setRepeatMode(Animation.REVERSE);*/

            case MotionEvent.ACTION_MOVE:


            case MotionEvent.ACTION_UP:
                Animation scaleAnimation2 = new ScaleAnimation(0.9f, 1f, 0.9f, 1f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation2.setInterpolator(new AnticipateInterpolator());
                scaleAnimation2.setDuration(200);
                scaleAnimation2.setFillAfter(true);
                this.startAnimation(scaleAnimation2);

                //mOnClickListener.onClick(this);
        }
        return super.onTouchEvent(event);

    }


    public void setBitmap1(Bitmap bitmap){
        this.mBitmap1 = bitmap;
        invalidate();
    }
    public void setBitmap2(Bitmap bitmap){
        this.mBitmap2 = bitmap;
        invalidate();
    }

    public void setText2(String text){
        mText = text;
    }



}
