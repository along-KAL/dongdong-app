package com.example.group.dongdong.widget.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.group.dongdong.utils.DensityUtils;
import com.example.group.dongdong.R;

/**
 * Created by Administrator on 2017/1/1.
 */

public class PageV1 extends View {
    private Context mContext;
    private Paint timePaint,linePaint,columnPaint;
    private int ordinaColor,columnColor;//通用画笔颜色，柱状图画笔颜色
    private int currentTime;
    private int columnHeight;//柱状图的宽度
    private int mWidth,mHeight;//布局的宽高
    private String[] time={"00:00","12:00","23:59"};
    private Rect mRect;
    private int maxPace=0;

    public void setData(int pace,String time){
        currentTime=Integer.parseInt(time);

        if(pace>maxPace){
            maxPace=pace;
        }
        invalidate();
    }

    public PageV1(Context activity) {
        this(activity,null);
    }

    public PageV1(Context activity, AttributeSet attrs) {
        this(activity, attrs,0);
    }

    public PageV1(Context activity, AttributeSet attrs, int defStyleAttr) {
        super(activity, attrs, defStyleAttr);
        mContext=activity;
        TypedArray array = activity.getTheme().obtainStyledAttributes(attrs, R.styleable.MyPageAttr, defStyleAttr, 0);
        ordinaColor=array.getColor(R.styleable.MyPageAttr_ordinaColor, Color.parseColor("#c2bebf"));
        columnColor=array.getColor(R.styleable.MyPageAttr_columnColor,Color.parseColor("#2f8af1"));
        array.recycle();
        init();
    }

    //初始化画笔
    private void init() {
        timePaint=new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setColor(ordinaColor);
        mRect=new Rect();
        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ordinaColor);
        columnPaint=new Paint();
        columnPaint.setAntiAlias(true);
        columnPaint.setColor(columnColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if(widthMode==MeasureSpec.EXACTLY){
            width=widthSize;
        }else {
            width=widthSize*1/2;
        }

        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else {
            height=heightSize*1/2;
        }

        setMeasuredDimension(width,height);
    }

    //计算柱状图的宽高
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth=getWidth();
        mHeight=getHeight();
        columnHeight=maxPace/200*mHeight;
    }


    //重写onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //一个文本的高度
        int textWidth= (int) getTextHeight(timePaint);


        //绘制时间坐标文本
        timePaint.setTextSize(20);
        timePaint.setTextAlign(Paint.Align.CENTER);
        timePaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < time.length; i++) {
            timePaint.getTextBounds(time[i],0,time[i].length(),mRect);
            canvas.drawText(time[i], (float) ((mWidth-textWidth*2)*i/2+textWidth),mHeight,timePaint);
        }

        //绘制X坐标轴线
        linePaint.setStrokeWidth(DensityUtils.dp2px(mContext,1));
        linePaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(0, (float) (mHeight-textWidth*2),mWidth,(float) (mHeight-textWidth*2),linePaint);

        //绘制矩形条
        columnPaint.setStrokeWidth(DensityUtils.dp2px(mContext,8));
        double textHeight = getTextHeight(timePaint);
        int timeWidth = mWidth/ 24*currentTime;
        canvas.drawLine(timeWidth,(float) (mHeight-textHeight*2),timeWidth,columnHeight,columnPaint);
    }

    private double getTextHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return Math.ceil(fm.descent-fm.ascent);
    }
}
