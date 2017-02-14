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
import com.example.group.dongdong.utils.NearlyDateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Administrator on 2017/1/18.
 */

public class ShowCustom1 extends View {
    private Context mContext;
    //线框画笔,
    private Paint linePaint,monthPaint,numbPaint,columnPaint;
    //文本宽高
    private int textWidth,textHeight;
    //布局宽高
    private int mWidth,mHeight;
    //颜色
    private int ordinaColor,columnColor;
    //控件的宽高
    private int widgetWidth,widgetHeight;
    //控件平均高度
    private int averageHeight;
    //最近天数
    private List<Map<String,String>> nearlyDays=new ArrayList<>();
    //走路的值
    private String[] paces={"0","2500","5000","7500","10000"};
    //柱状图数值集合
    private List<Integer> columnHeight=new ArrayList<>();


    public void setColumnHeight(List<Integer> columnHeight) {
        this.columnHeight.addAll(columnHeight);
        invalidate();
    }
    public ShowCustom1(Context context) {
        this(context,null);
    }

    public ShowCustom1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShowCustom1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        //日期
        currentDay();
        inits();
        initAttrs(attrs);
    }

    private void currentDay() {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String years = String.valueOf(c.get(Calendar.YEAR));
        String months = String.valueOf(c.get(Calendar.MONTH));
        String days = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        for (int i = 0; i < 5; i++) {
            List<Map<String, String>> date = NearlyDateUtils.getDate(years + "-" + months + "-" + days, i * 7);
            nearlyDays.addAll(date);
        }
        invalidate();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.MyPageAttr);
        columnColor=array.getColor(R.styleable.MyPageAttr_columnColor, Color.parseColor("#2f8af1"));
        ordinaColor=array.getColor(R.styleable.MyPageAttr_ordinaColor,Color.parseColor("#c2bebf"));
        array.recycle();
    }

    private void inits() {
        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ordinaColor);

        monthPaint=new Paint();
        monthPaint.setAntiAlias(true);
        monthPaint.setColor(ordinaColor);

        numbPaint=new Paint();
        numbPaint.setAntiAlias(true);
        numbPaint.setColor(ordinaColor);

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
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
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

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth=getWidth();
        mHeight=getHeight();
        textHeight=getTextHeight(monthPaint);
        textWidth = textHeight*3;
        widgetHeight=mHeight-30*textHeight;
        widgetWidth=mWidth-4*textWidth;
        averageHeight=widgetHeight/27;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制线框
        drawLine(canvas);
        //绘制日期
        drawDays(canvas);
        //绘制数值
        drawNumb(canvas);
        //绘制柱状图
        drawColumn(canvas);
    }

    private void drawColumn(Canvas canvas) {
        canvas.save();
        columnPaint.setStrokeWidth(textWidth/2);
        for (int i = 0; i < 7; i++) {
            canvas.drawLine(2*textWidth+i* textWidth,mHeight-textHeight*13,
                    2*textWidth+i* textWidth,
                    columnHeight.get(i)*(5000/(mHeight-textHeight*16)),columnPaint);
        }
        canvas.restore();
    }

    private void drawNumb(Canvas canvas) {
        canvas.save();
        numbPaint.setTextSize(20);
        Rect rect=new Rect();
        for (int i = 0; i < 5; i++) {
            numbPaint.getTextBounds(paces[i],0,paces[i].length(),rect);
            canvas.drawText(paces[i],textWidth,mHeight-(15*textHeight+(i+1)*7*averageHeight),numbPaint);
        }
        canvas.restore();
    }

    private void drawDays(Canvas canvas) {
        canvas.save();
        monthPaint.setTextSize(20);
        Rect rect=new Rect();
        for (int i = 0; i < nearlyDays.size(); i++) {
            Map<String, String> map = nearlyDays.get(i);
            String month = map.get("month");
            String day = map.get("day");
            String result=month+"月"+day+"日";
            monthPaint.getTextBounds(result,0,result.length(),rect);
            canvas.drawText(result,textWidth*2+i*textWidth,textHeight,monthPaint);
        }
        canvas.restore();
    }

    private void drawLine(Canvas canvas) {
        canvas.save();
        linePaint.setStrokeWidth(6);
        linePaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(textWidth*2,mHeight-15*textHeight,mWidth-textWidth*2,
                mHeight-15*textHeight,linePaint);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ordinaColor);
        linePaint.setStrokeWidth(1);
        linePaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 4; i++) {
            canvas.drawLine(textWidth*2,mHeight-(15*textHeight+(i+1)*7*averageHeight),
                    mWidth-textWidth*2,mHeight-(15*textHeight+(i+1)*7*averageHeight),linePaint);
        }

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ordinaColor);
        linePaint.setStrokeWidth(1);
        linePaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < 2; i++) {
            canvas.drawLine(textWidth*2+i*( mWidth-textWidth*2),mHeight-15*textHeight,
                    textWidth*2+i*( mWidth-textWidth*2),
                    mHeight-(15*textHeight+28*averageHeight),linePaint);
        }

        canvas.restore();
    }


    //测量文本高度
    public int getTextHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent-fm.ascent);
    }

}
