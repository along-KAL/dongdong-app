package com.example.group.dongdong.widget;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.group.dongdong.utils.CountDownTimer;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kongalong on 2017/1/6.
 */

public class SportsTrainPlanCircleView extends View{





    private Paint mPaint;

    //view的宽高
    private int mWidth;
    private int mHeight;
    //半径
    private float mRadius;

    //当前的刻度值
    private int mCurrentCount;

    //刻度间隔
    private float mAngleGap = 2.7f;

    private float mStartAngle;
    private float mSweepAngle;

    private String mSportTypeText;
    private String mSportTypeCountText;

    private long mTotalTime;
    private long mLeftTime;

    private float mSchedule;
    private ObjectAnimator mAnim;


    public SportsTrainPlanCircleView(Context context) {
        super(context);



        init(context);
    }



    public SportsTrainPlanCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SportsTrainPlanCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void init(Context context) {
        mPaint = new Paint();

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);


        mRadius = 350.0f;

        mSportTypeText = "热身";

        mSportTypeCountText = "1/18";

        mLeftTime = mTotalTime;

        //mTotalTime = 50000;

        mSchedule = 1;



    }

    public void setSportTypeText(String sportTypeCountText, String sportTypeText, long totalTime){
        this.mSportTypeText = sportTypeText;
        this.mTotalTime = totalTime;
        this.mLeftTime = totalTime;
        this.mSportTypeCountText = sportTypeCountText;
        invalidate();
    }

  /*  public void setSportTypeCountText(String sportTypeCountText){

        invalidate();
    }*/

    public void setLeftTime(long leftTime){
        this.mLeftTime = leftTime;
        invalidate();
    }

    public void setSchedule(float schedule) {
        mSchedule = schedule;
        invalidate();
    }

    public float getSchedule() {
        return mSchedule;
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

        mSweepAngle = mSchedule*360;
        mStartAngle = (360 - mSweepAngle)-90;
        //绘制圆环
        canvas.save();

        mPaint.setColor(Color.parseColor("#FFDCDADA"));
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.translate(mWidth/2,mHeight/2);
        RectF oval=new RectF();
        oval.left=-mRadius+20;
        oval.top=-mRadius+20;
        oval.right=mRadius-20;
        oval.bottom=mRadius-20;
        canvas.drawArc(oval, 0, 360, false, mPaint);

        mPaint.setColor(Color.parseColor("#FFF16A6A"));
        mPaint.setStrokeWidth(40);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(oval, mStartAngle, mSweepAngle, false, mPaint);

        canvas.restore();

        mPaint.setStyle(Paint.Style.FILL);

        //画刻度
        canvas.save();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(1);
        canvas.translate(mWidth/2,mHeight/2);
        //canvas.rotate(-mAngle/2);

        for (int i = 0; i <= 360/mAngleGap; i++) {

            canvas.drawLine(0,-mRadius,0,-mRadius+40,mPaint);
            canvas.rotate(mAngleGap);

        }

        canvas.restore();


        //画字
        canvas.save();
        canvas.translate(mWidth/2,mHeight/2);

        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(50);
        canvas.drawText(mSportTypeText, 0,-150,mPaint);

        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(170);

        //格式化时间
        SimpleDateFormat format1=new SimpleDateFormat("mm:ss");
        Date d1=new Date(mLeftTime);

        canvas.drawText(format1.format(d1), 0,30,mPaint);

        mPaint.setColor(Color.GRAY);
        mPaint.setTextSize(40);
        canvas.drawText(mSportTypeCountText, 0,140,mPaint);

        canvas.restore();

    }

    MyAnimatorUpdateListener updateListener = new MyAnimatorUpdateListener();

    public void startAnimate(){

        //如果已经暂停，是继续播放
        if(updateListener.isPause)updateListener.play();
            //否则就是从头开始播放
        else {
            mAnim = ObjectAnimator//
                    .ofFloat(this, "schedule", 1.0F,  0.0F)//
                    .setDuration(mTotalTime);
            mAnim.setInterpolator(new LinearInterpolator());
            mAnim.addUpdateListener(updateListener);

            mAnim.start();
        }
    }

    public void pauseAnimate(){

        updateListener.pause();

    }


    class MyAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        /**
         * 暂停状态
         */
        private boolean isPause = false;
        /**
         * 是否已经暂停，如果一已经暂停，那么就不需要再次设置停止的一些事件和监听器了
         */
        private boolean isPaused = false;
        /**
         * 当前的动画的播放位置
         */
        private float fraction = 0.0f;
        /**
         * 当前动画的播放运行时间
         */
        private long mCurrentPlayTime = 0l;

        /**
         * 是否是暂停状态
         * @return
         */
        public boolean isPause(){
            return isPause;
        }

        /**
         * 停止方法，只是设置标志位，剩余的工作会根据状态位置在onAnimationUpdate进行操作
         */
        public void pause(){
            isPause = true;
        }
        public void play(){
            isPause = false;
            isPaused = false;
        }
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            /**
             * 如果是暂停则将状态保持下来，并每个刷新动画的时间了；来设置当前时间，让动画
             * 在时间上处于暂停状态，同时要设置一个静止的时间加速器，来保证动画不会抖动
             */
            if(isPause){
                if(!isPaused){
                    mCurrentPlayTime = animation.getCurrentPlayTime();
                    fraction = animation.getAnimatedFraction();
                    animation.setInterpolator(new TimeInterpolator() {
                        @Override
                        public float getInterpolation(float input) {
                            return fraction;
                        }
                    });
                    isPaused =  true;


                }

                //每隔动画播放的时间，我们都会将播放时间往回调整，以便重新播放的时候接着使用这个时间,同时也为了让整个动画不结束
                new CountDownTimer(ValueAnimator.getFrameDelay(), ValueAnimator.getFrameDelay()){

                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        mAnim.setCurrentPlayTime(mCurrentPlayTime);
                        this.cancel();
                    }
                }.start();
            }else{
                //将时间拦截器恢复成线性的，如果您有自己的，也可以在这里进行恢复
                animation.setInterpolator(null);
            }
        }

    }

}
