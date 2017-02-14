package com.example.group.dongdong.widget.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.group.dongdong.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/15.
 */

public class PaceTableV2 extends View {
    private Context mContext;
    //日期画笔，数字画笔，线框画笔，折线画笔，虚线画笔，区域画笔，点画笔
    private Paint weekPaint,numbPaint,linePaint,brokenPaint,brokenVirtualPaint,areaPaint,pointPaint;
    //颜色
    private int ordinaColor,columnColor,baseColor;
    private String[] weeks={"周一","周二","周三","周四","周五","周六","周日"};
    private String[] numbs={"55","61","68","75","82"};
    //布局的宽高
    private int mWidth,mHeight;
    //控件的宽高
    private int widgetWidth,widgetHeight;
    //控件平均高度
    private int averageHeight;
    //文本宽高
    private int textWidth,textHeight;
    //折线集合
    private List<Integer> brokens=new ArrayList<>();
    //折线点集合
    private List<Point> mPoints;
    //绘制的路径
    private Path path;

    public void setBroken(List<Integer> broken){
        brokens.addAll(broken);
    }

    public PaceTableV2(Context context) {
        this(context,null);
    }

    public PaceTableV2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PaceTableV2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        initAttrs(attrs);
        inits();
    }

    private void inits() {
        weekPaint=new Paint();
        weekPaint.setAntiAlias(true);
        weekPaint.setColor(ordinaColor);

        numbPaint=new Paint();
        numbPaint.setColor(ordinaColor);
        numbPaint.setAntiAlias(true);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(ordinaColor);

        brokenPaint=new Paint();
        brokenPaint.setAntiAlias(true);
        brokenPaint.setColor(columnColor);

        brokenVirtualPaint=new Paint();
        brokenVirtualPaint.setColor(columnColor);
        brokenVirtualPaint.setAntiAlias(true);

        areaPaint=new Paint();
        areaPaint.setAntiAlias(true);
        areaPaint.setColor(baseColor);

        pointPaint=new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(columnColor);


        path = new Path();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.MyPageAttr);
        columnColor=array.getColor(R.styleable.MyPageAttr_columnColor, Color.parseColor("#2f8af1"));
        ordinaColor=array.getColor(R.styleable.MyPageAttr_ordinaColor,Color.parseColor("#c2bebf"));
        baseColor=array.getColor(R.styleable.MyPageAttr_baseColor,Color.parseColor("#6885e5fa"));
        array.recycle();
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
        textHeight=getTextHeight(weekPaint);
        textWidth = textHeight*3;
        widgetHeight=mHeight-30*textHeight;
        widgetWidth=mWidth-4*textWidth;
        averageHeight=widgetHeight/27;
        mPoints=new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Point point=new Point();
            point.x=textWidth*5/2+(i*textWidth*3-textWidth/2);
            point.y=brokens.get(i)*averageHeight;
            mPoints.add(point);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制线框
        drawLine(canvas);
        //绘制横坐标周
        drawWeek(canvas);
        //绘制纵坐标数字
        drawNumb(canvas);
        //绘制折线
        drawBroken(canvas);
        //绘制区域覆盖
        drawArea(canvas);
    }

    private void drawArea(Canvas canvas) {
        canvas.save();
        path.reset();
        List<Point> aPoints=new ArrayList<>();



        Point point2=new Point();
        point2.x=mWidth-textWidth*2;
        point2.y=mHeight-widgetHeight;
        aPoints.add(point2);

        Point point3=new Point();
        point3.x=mWidth-textWidth*2;
        point3.y=mHeight-15*textHeight;
        aPoints.add(point3);

        Point point4=new Point();
        point4.x=textWidth*2;
        point4.y=mHeight-15*textHeight;
        aPoints.add(point4);


        path.moveTo(textWidth*2,mHeight-widgetHeight);
        for (int i = 0; i < 3; i++) {
            path.lineTo(aPoints.get(i).x,aPoints.get(i).y);
        }
        path.close();
        areaPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path,areaPaint);
        canvas.restore();
    }

    private void drawBroken(Canvas canvas) {
        canvas.save();
        path.reset();
        path.moveTo(textWidth*2,brokens.get(0)*averageHeight);
        brokenPaint.setStrokeWidth(10);
        brokenPaint.setStyle(Paint.Style.STROKE);
        for (int i = 1; i < 7; i++) {
            path.lineTo(mPoints.get(i).x,mPoints.get(i).y);
        }
        canvas.drawPath(path,brokenPaint);

        pointPaint=new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(columnColor);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(10);
        pointPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(textWidth*5/2+((brokens.size()-1)*textWidth*3-textWidth/2)+15/2,
                brokens.get(brokens.size()-1)*averageHeight,15,pointPaint);

        path.reset();
        path.moveTo(textWidth*5/2+((brokens.size()-1)*textWidth*3-textWidth/2)+15,
                brokens.get(brokens.size()-1)*averageHeight);
        path.lineTo(mWidth-textWidth*2,brokens.get(brokens.size()-1)*averageHeight);
        pointPaint=new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(columnColor);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(10);
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{10,3,10,3}, 1);
        pointPaint.setPathEffect(dashPathEffect);
        canvas.drawPath(path,pointPaint);
        canvas.restore();
    }

    private void drawNumb(Canvas canvas) {
        canvas.save();
        numbPaint.setTextSize(25);
        Rect rect=new Rect();
        for (int i = 0; i < 5; i++) {
            numbPaint.getTextBounds(numbs[i],0,numbs[i].length(),rect);
            canvas.drawText(numbs[i],textWidth,mHeight-(15*textHeight+i*7*averageHeight),numbPaint);
        }
        canvas.restore();
    }

    private void drawWeek(Canvas canvas) {
        canvas.save();
        weekPaint.setTextSize(30);
        Rect rect=new Rect();
        for (int i = 0; i < 7; i++) {
            weekPaint.getTextBounds(weeks[i],0,weeks[i].length(),rect);
            canvas.drawText(weeks[i],textWidth*5/2+i*textWidth*3,mHeight-12*textHeight,weekPaint);
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
        canvas.restore();
    }

    //测量文本高度
    public int getTextHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent-fm.ascent);
    }
}
