package com.example.group.dongdong.widget.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.group.dongdong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/6.
 */

public class WidgetView extends View {
    private Context mContext;
    private int ordinaColor,columnColor,underntColor;
    //数字画笔，时间画笔，坐标画笔，波折线画笔，覆盖颜色画笔
    private Paint numPaint,timePaint,linePaint,colorLinePaint,colorPaint;
    private String[] number={"52","57","62","67","72"};
    //布局宽高
    private int mWidth,mHeight;
    //间距
    private int space;
    //文字宽
    private int textHeight;
    //波折线长度
    private int colorLineWidth;
    //体质指数
    private List<Float> mBmi=new ArrayList<>();
    //月份，日期
    private List<String> mDay=new ArrayList<>();
    //刻度线总高度
    private int mNumber;
    //折线的点集合
    private List<Point> mPoints=new ArrayList<>();
    private Point point;
    //路径
    private Path path;

    public void setBmi(List<Float> bmi,List<String> day){
        mBmi.addAll(bmi);
        Log.i("TAG", "----------->mBmi:" +mBmi.size());
        mDay.addAll(day);
        Log.i("TAG", "----------->mDay:" +mDay.size());
        invalidate();
    }

    public WidgetView(Context context) {
        this(context,null);
        init();//初始化画笔
    }

    public WidgetView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        init();//初始化画笔
    }

    public WidgetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initAttrs(attrs);
        init();//初始化画笔
    }

    private void init() {
        path=new Path();

        numPaint=new Paint();
        numPaint.setAntiAlias(true);
        numPaint.setColor(ordinaColor);

        timePaint=new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setColor(ordinaColor);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ordinaColor);

        colorLinePaint=new Paint();
        colorLinePaint.setAntiAlias(true);
        colorLinePaint.setColor(columnColor);

        colorPaint=new Paint();
        colorPaint.setAntiAlias(true);
        colorPaint.setColor(underntColor);

        point=new Point();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.MyPageAttr);
        ordinaColor=array.getColor(R.styleable.MyPageAttr_ordinaColor, Color.parseColor("#c2bebf"));
        columnColor=array.getColor(R.styleable.MyPageAttr_columnColor,Color.parseColor("#2f8af1"));
        underntColor=array.getColor(R.styleable.MyPageAttr_baseColor,Color.parseColor("#6885e5fa"));
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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mHeight=getHeight();
        textHeight= (int) getTextHeight(numPaint);
        space=(mHeight-6*textHeight)/4;
        colorLineWidth=mWidth*3/4-textHeight*4;
        mNumber = (space * 4)/22;

        //获取折线点集合
        for (int i = 0; i < mDay.size(); i++) {
            point=new Point();
            point.x=colorLineWidth/mDay.size()*i+textHeight*2;
            Log.i("TAG", "----------->这里添加pointX:" +point.x);
            point.y= (int) (mNumber*(mBmi.get(i))-51);//*3
            Log.i("TAG", "----------->这里添加:" +point.y);
            mPoints.add(point);
        }

        point=new Point();
        point.x=textHeight*2+colorLineWidth;
        point.y=mHeight-textHeight*4;
        mPoints.add(point);

        point=new Point();
        point.x=textHeight*2;
        point.y=mHeight-textHeight*4;
        mPoints.add(point);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制刻度纵坐标
        drawNumb(canvas);
        //绘制日期
        drawTime(canvas);
        //绘制线条刻度
        drawLine(canvas);
        //绘制波折线
        drawColorLine(canvas);
        //绘制颜色区域
        drawColor(canvas);
    }

    //绘制填充区域
    private void drawColor(Canvas canvas) {
        canvas.save();
        path.reset();
        path.moveTo(mPoints.get(0).x,mPoints.get(0).y);

        for (int i = 0; i < mPoints.size(); i++) {
            path.lineTo(mPoints.get(i).x,mPoints.get(i).y);

        }
        path.close();

        colorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        colorPaint.setStrokeWidth(1);

        canvas.drawPath(path,colorPaint);
        canvas.restore();
    }

    //绘制折线图
    private void drawColorLine(Canvas canvas) {
        canvas.save();
        colorLinePaint.setStyle(Paint.Style.STROKE);
        colorLinePaint.setStrokeWidth(6);
        path.reset();
        path.moveTo(mPoints.get(0).x,mPoints.get(0).y);
        Log.i("TAG", "----------->起点位置:" +mPoints.get(0).x+","+mPoints.get(0).y);
        Log.i("TAG", "----------->集合中添加了点的个数:" +mPoints.size());
        for (int i = 0; i < mDay.size(); i++) {
            path.lineTo(mPoints.get(i).x,mPoints.get(i).y);
            Log.i("TAG", "----------->mpoint.x:" +mPoints.get(i).x);
            Log.i("TAG", "----------->mpoint.y:" +mPoints.get(i).y);
        }

        canvas.drawPath(path,colorLinePaint);
        canvas.restore();
    }

    private void drawLine(Canvas canvas) {
        canvas.save();
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(textHeight*2,mHeight-4*textHeight,
                mWidth-textHeight,mHeight-4*textHeight,linePaint);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ordinaColor);
        linePaint.setStrokeWidth(1);
        linePaint.setStyle(Paint.Style.STROKE);
        for (int i = 1; i < 5; i++) {
            canvas.drawLine(textHeight*2,mHeight-2*textHeight-space*i,
                    mWidth-textHeight,mHeight-2*textHeight-space*i,linePaint);
        }
        canvas.restore();
    }

    private void drawTime(Canvas canvas) {
        canvas.save();
        timePaint.setStyle(Paint.Style.STROKE);
        timePaint.setTextSize(20);
        for(int i = 0; i < 2; i++) {
            canvas.drawText(mDay.get(i*(mDay.size()-1)),textHeight*3+colorLineWidth*i,mHeight-textHeight*2,timePaint);
        }
        canvas.restore();
    }

    private void drawNumb(Canvas canvas) {
        canvas.save();
        numPaint.setStyle(Paint.Style.STROKE);
        numPaint.setTextSize(20);
        for (int i = 0; i < number.length; i++) {
            canvas.drawText(number[i],textHeight,mHeight-2*textHeight-space*i,numPaint);
        }
        canvas.restore();
    }

    private double getTextHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent-fm.ascent;
    }
}
