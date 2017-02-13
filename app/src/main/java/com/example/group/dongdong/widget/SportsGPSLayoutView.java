package com.example.group.dongdong.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;

/**
 * Created by kongalong on 2017/1/11.
 */

public class SportsGPSLayoutView extends LinearLayout {


    private Paint mPaint;

    private int mY = 0;

    private int mHeight = 0;
    private int mWidth = 0;


    public void setY(int y) {
        mY = y;
        invalidate();
    }

    public SportsGPSLayoutView(Context context) {
        super(context);
        setWillNotDraw(false);
        init();
    }



    public SportsGPSLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        init();

    }

    public SportsGPSLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

    }

    private void init() {

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#c5d4d2d2"));
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();

        mY = mHeight/2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate(-45);

        canvas.drawRect(new RectF(-mWidth/2-mWidth,-mHeight/2-mWidth,mWidth/2+mWidth,(float)mY-50),mPaint);

        canvas.restore();

    }


    public void startInAnimate(){

        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f,1.0f);
        //   scaleAnimation.setInterpolator(new AnticipateInterpolator());
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(500);

        getChildAt(0).startAnimation(alphaAnimation);


        ObjectAnimator mAnim = ObjectAnimator//
                    .ofInt(this, "y", -mHeight/2,  mHeight/2)//
                    .setDuration(800);
            mAnim.setInterpolator(new BounceInterpolator());

            mAnim.start();


    }

    public void startOutAnimate(){


        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
         //   scaleAnimation.setInterpolator(new AnticipateInterpolator());
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(300);

        getChildAt(0).startAnimation(alphaAnimation);


        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


                ObjectAnimator mAnim = ObjectAnimator//
                        .ofInt(SportsGPSLayoutView.this, "y", mHeight/2,  -mHeight/2)//
                        .setDuration(800);
                mAnim.setInterpolator(new BounceInterpolator());

                mAnim.start();

                mAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mIsConsumeEvent = false;

                        mOnOutListener.onOut();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    private boolean mSign = false;



    private boolean mIsConsumeEvent = true;

    public void setConsumeEvent(boolean consumeEvent) {
        mIsConsumeEvent = consumeEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float touchX;


        switch(event.getAction()){

            case MotionEvent.ACTION_DOWN:

                touchX = event.getX();

                if(touchX>mWidth-100&&touchX<mWidth){
                    mSign = true;
                }

                return mIsConsumeEvent;

            case MotionEvent.ACTION_MOVE:
                return true;
            case MotionEvent.ACTION_UP:

                touchX = event.getX();

                if(mSign&&touchX>mWidth-100&&touchX<mWidth){
                    mSign = false;
                    startOutAnimate();
                }
                return true;

        }

        return super.onTouchEvent(event);

    }

    private OnOutListener mOnOutListener;

    public void setOnOutListener(OnOutListener onOutListener){

        mOnOutListener = onOutListener;

    }

    public interface OnOutListener{

        void onOut();

    }
}
