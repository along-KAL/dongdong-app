package com.example.group.dongdong.widget.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.group.dongdong.utils.DensityUtils;
import com.example.group.teamproject2.R;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PageV4 extends View {
    private Context context;
    //几个画笔分别是文本画笔,总大卡线，当前大卡线,游标画笔，游标线画笔
    private Paint textPaint,carPaint,currentCarPaint,indexPaint,indexLinePaint;
    //总热量，当前热量
    private int totalCar,currentCar;
    //总热量占比,当前热量进度,热量进度条宽
    private int totalProgress=500,currentProgress=300,progressWidth;
    //普通颜色，当前颜色值，背景颜色
    private int ordinaColor,colomnColor,baseColor;
    //文本高度
    private int textHeight;
    //布局的宽高
    private int mWidth,mHeight;
    //文本内容
    private String[] showCar={"升级目标:"+totalCar+"大卡","上周:"+currentCar+"大卡"};
    //加载图片站位
    private Bitmap mBitmap=null;

    //提供外部传进设置消耗的热量
    public void setCar(int totalCar,int currentCar){
        this.totalCar=totalCar;
        this.currentCar=currentCar;
    }

    public PageV4(Context context) {
        this(context,null);
    }

    public PageV4(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageV4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        //设置控件属性
        initAttr(attrs);
        //初始化画笔
        initPaint();
    }

    private void initPaint() {
        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(ordinaColor);

        carPaint=new Paint();
        carPaint.setAntiAlias(true);
        carPaint.setColor(baseColor);

        currentCarPaint=new Paint();
        currentCarPaint.setAntiAlias(true);
        currentCarPaint.setColor(colomnColor);

        indexPaint=new Paint();
        mBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.me_index);
        indexPaint.setAntiAlias(true);
        indexPaint.setColor(ordinaColor);

        indexLinePaint=new Paint();
        indexLinePaint.setAntiAlias(true);
        indexLinePaint.setColor(ordinaColor);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyPageAttr);
        ordinaColor=array.getColor(R.styleable.MyPageAttr_ordinaColor,Color.parseColor("#c2bebf"));
        colomnColor=array.getColor(R.styleable.MyPageAttr_columnColor,Color.parseColor("#2f8af1"));
        baseColor=array.getColor(R.styleable.MyPageAttr_baseColor,Color.parseColor("#bed0e4"));
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
            height=heightSize;
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //布局的宽高
        mWidth=getWidth();
        mHeight=getHeight();
        //总进度是固定的按父布局的宽
        totalProgress=getWidth();
        //热量进度条的宽度
        progressWidth= DensityUtils.dp2px(context,6);
        //当前进度值
        currentProgress=80;//currentCar/totalCar*totalProgress;
        //文本宽度
        textHeight= getTextHeight(textPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制文本
        drawText(canvas);
        //绘制总进度
        drawTotal(canvas);
        //绘制当前进度
        drawCurrent(canvas);
        //绘制游标
        drawIndex(canvas);
        //绘制游标线
        drawIndexLine(canvas);
    }

    private void drawIndexLine(Canvas canvas) {
        canvas.save();
        int bitmapH = mBitmap.getHeight();
        int bitH = mHeight - textHeight * 3 - bitmapH;//图片所在的位置高度

        //绘制游标图下面的线
        Paint upPaint=new Paint();
        upPaint.setAntiAlias(true);
        upPaint.setStrokeWidth(1);
        upPaint.setStyle(Paint.Style.STROKE);
        upPaint.setColor(ordinaColor);
        upPaint.setMaskFilter(new BlurMaskFilter(DensityUtils.dp2px(context,1), BlurMaskFilter.Blur.SOLID));

        canvas.drawLine(mBitmap.getWidth()/2+currentProgress-mBitmap.getWidth()/2,bitH+20,mBitmap.getWidth()/2+currentProgress-mBitmap.getWidth()/2,bitH+20+(bitH-textHeight)/2,upPaint);

        //绘制游标下线
        Paint downPaint=new Paint();
        downPaint.setAntiAlias(true);
        downPaint.setStrokeWidth(1);
        downPaint.setColor(ordinaColor);
        downPaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(mWidth*3/2/2,
                mHeight-textHeight*2,mWidth*3/2/2,bitH+20+(bitH-textHeight)/2,downPaint);

        indexLinePaint.setStrokeWidth(1);
        indexLinePaint.setMaskFilter(new BlurMaskFilter(DensityUtils.dp2px(context,1), BlurMaskFilter.Blur.SOLID));
        indexLinePaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(currentProgress+mBitmap.getWidth()/2-mBitmap.getWidth()/2,
                bitH+20+(bitH-textHeight)/2,
                mWidth*3/2/2,bitH+20+(bitH-textHeight)/2,indexLinePaint);

        canvas.restore();
    }

    private void drawIndex(Canvas canvas) {
        canvas.save();
        indexPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        indexPaint.setMaskFilter(
                new BlurMaskFilter(
                        DensityUtils.dp2px(context,3),BlurMaskFilter.Blur.SOLID));//关闭硬件加速器
        int bitmapH = mBitmap.getHeight();
        int bitH = mHeight - textHeight * 3 - bitmapH *3 / 2;//图片所在的位置高度
        canvas.drawBitmap(mBitmap,currentProgress-mBitmap.getWidth()/2, bitH,indexPaint);
        canvas.restore();
    }

    private void drawCurrent(Canvas canvas) {
        canvas.save();
        int bitmapH = mBitmap.getHeight();
        int bitH = mHeight - textHeight * 3 - bitmapH;//图片所在的位置高度
        currentCarPaint.setStrokeWidth(20);
        currentCarPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(0,bitH,currentProgress,bitH,currentCarPaint);
        canvas.restore();
    }

    private void drawTotal(Canvas canvas) {
        canvas.save();
        int bitmapH = mBitmap.getHeight();
        int bitH = mHeight - textHeight * 3 - bitmapH;//图片所在的位置高度
        carPaint.setStrokeWidth(20);
        carPaint.setStyle(Paint.Style.FILL);
        canvas.drawLine(0,bitH,mWidth,bitH,carPaint);
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        Rect rect=new Rect();
        textPaint.setTextSize(30);
        textPaint.getTextBounds(showCar[0],0,showCar.length,rect);
        //绘制总量文本
        canvas.drawText(showCar[0],0,mHeight-2*textHeight,textPaint);


        textPaint=new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(ordinaColor);
        Rect rect2=new Rect();
        textPaint.setTextSize(30);
        textPaint.getTextBounds(showCar[0],0,showCar.length,rect2);
        canvas.drawText(showCar[1],mWidth*2/3,mHeight-2*textHeight,textPaint);
        canvas.restore();
    }

    private int getTextHeight(Paint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent-fm.ascent);
    }
}
