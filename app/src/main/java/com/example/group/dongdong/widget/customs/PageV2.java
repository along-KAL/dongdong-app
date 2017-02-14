package com.example.group.dongdong.widget.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.group.dongdong.R;
import com.example.group.dongdong.utils.DensityUtils;

/**
 * Created by Administrator on 2017/1/4.
 */

public class PageV2 extends View {
    private Context context;
    private Paint timePaint,linePaint,columnPaint;
    private String[] times={"00:00","12:00","23:59"};
    private int ordinaColor,columnColor;
    private int mWidth,mHeight;
    private int pace,everyTime;//行走的步伐，各个时间断
    private int maxPace;//单日走路多的步伐数
    private int columnHeight;//柱状图的高度

    public void setData(int pace,int currentTime){
        this.pace=pace;
        everyTime=currentTime;
        if(pace>maxPace){
            maxPace=pace;
        }
        invalidate();
    }

    public PageV2(Context context) {
        this(context,null);
    }

    public PageV2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initAttrs(attrs);
        initPaint();
    }

    //初始化画笔
    private void initPaint() {
        timePaint=new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setColor(ordinaColor);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        timePaint.setColor(ordinaColor);

        columnPaint=new Paint();
        columnPaint.setAntiAlias(true);
        columnPaint.setColor(columnColor);
    }

    //初始化属性
    private void initAttrs(AttributeSet attrs) {
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.MyPageAttr);
        columnColor=array.getColor(R.styleable.MyPageAttr_columnColor, Color.parseColor("#2f8af1"));
        ordinaColor=array.getColor(R.styleable.MyPageAttr_ordinaColor,Color.parseColor("#c2bebf"));
        array.recycle();
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

        if(widthMode== MeasureSpec.EXACTLY){
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth=getWidth();
        mHeight=getHeight();
        columnHeight=-pace/maxPace*mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制文本
        drawText(canvas);
        //绘制X轴线
        drawLine(canvas);
        //绘制柱状图
        drawColumn(canvas);

    }

    private void drawColumn(Canvas canvas) {
        canvas.save();
        double textHeight = getTextHeight(timePaint);
        columnPaint.setStrokeWidth(DensityUtils.dp2px(context,8));
        int timeWidth = mWidth/ 24*everyTime;
        canvas.drawLine(timeWidth,(float) (mHeight-textHeight*2),timeWidth,columnHeight,columnPaint);
        canvas.restore();
    }

    private void drawLine(Canvas canvas) {
        canvas.save();
        linePaint.setStrokeWidth(DensityUtils.dp2px(context,1));
        linePaint.setStyle(Paint.Style.STROKE);
        int textHeight = (int) getTextHeight(timePaint);
        canvas.drawLine(0,(float) (mHeight-textHeight*2),mWidth,(float) (mHeight-textHeight*2),linePaint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        Rect rect=new Rect();
        timePaint.setStyle(Paint.Style.STROKE);
        timePaint.setTextSize(20);
        int textWidth= (int) getTextHeight(timePaint);

        for (int i = 0; i < times.length; i++) {
            timePaint.getTextBounds(times[i],0,times[i].length(),rect);
            canvas.drawText(times[i],(float)((mWidth-textWidth*2)*i/2),mHeight,timePaint);
        }
        canvas.restore();
    }

    private double getTextHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return Math.ceil(fm.descent-fm.ascent);
    }
}
