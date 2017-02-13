package com.example.group.dongdong.widget.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.group.teamproject2.R;

import java.util.ArrayList;
import java.util.List;

import static android.graphics.Paint.Style.STROKE;

/**
 * Created by Administrator on 2017/1/14.
 */

public class PaceTableV1 extends View {
    private  Context mContext;
    private Paint textPaint,numbPaint,linePaint,columnbPaint,suspendPaint;
    private String[] weeks={"周一","周二","周三","周四","周五","周六","周日"};
    private String maxNumb="5k";
    private int orderColor,columnColor;
    private int mWidth,mHeight;//布局的宽高
    private int columnWidth;//柱状图的宽
    private List<Integer> columnHeight=new ArrayList<>();
    private int textHeight;//文本的宽度，文本的高度
    private int textWidth;//文本的宽度
    private List<String> showNumbers=new ArrayList<>();
    //标记触摸的位置点
    private int selectWeek=7;


    public void setColumnHeight(List<Integer> columnHeight) {
        this.columnHeight.addAll(columnHeight);
        invalidate();
    }

    public PaceTableV1(Context context) {
        this(context,null);
    }

    public PaceTableV1(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PaceTableV1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       this.mContext=context;
        initAttrs(attrs);
        inits();
    }

    //初始化画笔
    private void inits() {
        textPaint=new Paint();
        textPaint.setColor(orderColor);
        textPaint.setAntiAlias(true);

        numbPaint=new Paint();
        numbPaint.setAntiAlias(true);
        numbPaint.setColor(orderColor);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(5);
        linePaint.setColor(orderColor);

        columnbPaint=new Paint();
        columnbPaint.setAntiAlias(true);
        columnbPaint.setStrokeWidth(20);
        columnbPaint.setColor(columnColor);

        suspendPaint= new Paint();
        suspendPaint.setAntiAlias(true);
        suspendPaint.setColor(columnColor);

    }

    //初始化属性值
    private void initAttrs(AttributeSet attrs) {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.MyPageAttr);
        orderColor=array.getColor(R.styleable.MyPageAttr_ordinaColor, Color.parseColor("#c2bebf"));
        columnColor=array.getColor(R.styleable.MyPageAttr_columnColor,Color.parseColor("#2f8af1"));
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
            width=widthSize;
        }

        if(heightMode==MeasureSpec.EXACTLY){
            height=heightSize;
        }else{
            height=heightSize*1/2;
        }
        setMeasuredDimension(width,height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=getWidth();
        mHeight=getHeight();
        textHeight= getTextHeight(textPaint);
        columnWidth=textHeight*2/3;
        textWidth=mWidth/10;
        for (int i = 0; i < columnHeight.size(); i++) {
            showNumbers.add(i+"00");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //this.getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onActionTouch(event);
                //this.getParent().requestDisallowInterceptTouchEvent(false);
                break;

            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
               // this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return false;
    }

    private void onActionTouch(MotionEvent event) {
        boolean isValidTouch = validTouch(event.getX(), event.getY());
        if (isValidTouch){
            invalidate();
        }
    }

    //触摸范围
    private boolean validTouch(float x,float y){
        for (int i = 0; i < 7; i++) {
            if(x>2*textWidth+i* textWidth&&
                    x<2*textWidth+i* textWidth+textWidth/2){
                selectWeek=i+1;
                return true;
            }
        }
        return false;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //绘制文本
        drawText(canvas);
        //绘制线框
        drawLine(canvas);
        //绘制柱状图
        drawColumn(canvas);
        //绘制悬浮文本
        drawSuspend(canvas);
    }

    private void drawSuspend(Canvas canvas) {
        canvas.save();
        suspendPaint.setTextSize(30);
        Rect rect=new Rect();
        for (int i = 0; i < 7; i++) {
            suspendPaint.getTextBounds(showNumbers.get(i),0,
                    showNumbers.get(i).length(),rect);
            if(i==selectWeek-1){
                canvas.drawText(showNumbers.get(i),textWidth*2-textWidth*1/4+i* textWidth,
                        columnHeight.get(i)*(5000/(mHeight-textHeight*16))-textHeight,suspendPaint);
            }
        }

        canvas.restore();
    }

    private void drawColumn(Canvas canvas) {
        canvas.save();
        columnbPaint.setStrokeWidth(textWidth/2);
        for (int i = 0; i < 7; i++) {
            canvas.drawLine(2*textWidth+i* textWidth,mHeight-textHeight*13,
                    2*textWidth+i* textWidth,
                    columnHeight.get(i)*(5000/(mHeight-textHeight*16)),columnbPaint);
        }
        canvas.restore();
    }

    private void drawLine(Canvas canvas) {
        canvas.save();
        linePaint.setStrokeWidth(3);
        linePaint.setStyle(STROKE);
        canvas.drawLine(textWidth+textWidth/2,textHeight,
                mWidth-textWidth*3/2,textHeight,linePaint);

        linePaint=new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(6);
        linePaint.setColor(orderColor);
        linePaint.setStyle(STROKE);
        canvas.drawLine(textWidth+textWidth/2,mHeight-textHeight*11,mWidth-textWidth*3/2,
                mHeight-textHeight*11,linePaint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        textPaint.setTextSize(35);
        textPaint.setStyle(Paint.Style.STROKE);
        Rect rect=new Rect();
        for (int i = 0; i < weeks.length; i++) {
            textPaint.getTextBounds(weeks[i],0,weeks[i].length(),rect);
            canvas.drawText(weeks[i],
                    textWidth+textWidth/2+i*textWidth,
                    mHeight-textHeight*8,textPaint);
        }

        textPaint=new Paint();
        textPaint.setColor(orderColor);
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);
        Rect rect2=new Rect();
        textPaint.getTextBounds(maxNumb,0,maxNumb.length(),rect2);
        canvas.drawText(maxNumb,textWidth, textHeight*3/2,textPaint);
        canvas.restore();
    }

    private int getTextHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent-fm.ascent);
    }
}
