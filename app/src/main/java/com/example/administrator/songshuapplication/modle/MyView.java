package com.example.administrator.songshuapplication.modle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.example.administrator.songshuapplication.R;


/**
 * Created by yangtong on 2017/6/3.
 */

public class MyView extends View {


    private Paint paint;
    private int circleWidth;
    private int roundBackgroundColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private float progress =100;
    private int[] colors = {0xff660099, 0xff3366ff, 0xff00ccff};
    private int radius;
    private RectF oval;
    private Paint mPaintText;
    private int maxColorNumber = 100;
    private float singlPoint =30;
    private float lineWidth = 0.3f;
    private int circleCenter;
    private SweepGradient sweepGradient;
    private boolean isLine;
    /***
     * 是在Java代码创建视图的时候被调用，如果是从xml填充的视图，就不会调用这个
     * @param context
     */

    public MyView(Context context) {
        super(context);
    }



    /***
     * 自定义控件必须重新这个构造函数
     * 这个是在xml创建但是没有指定style的时候被调用
     * @param context
     * @param attrs
     */
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        maxColorNumber = mTypedArray.getInt(R.styleable.MyView_circleNumber,12);
        circleWidth = mTypedArray.getDimensionPixelOffset(R.styleable.MyView_circleWidth, getDpValue(350));
        roundBackgroundColor = mTypedArray.getColor(R.styleable.MyView_roundColor, 0xffdddddd);
        textColor = mTypedArray.getColor(R.styleable.MyView_circleTextColor, 0xff999999);
        roundWidth = mTypedArray.getDimension(R.styleable.MyView_circleRoundWidth, 20);
        textSize = mTypedArray.getDimension(R.styleable.MyView_circleTextSize, getDpValue(8));
        colors[0] = mTypedArray.getColor(R.styleable.MyView_circleColor1, 0xff660099);
        colors[1] = mTypedArray.getColor(R.styleable.MyView_circleColor2, 0xff3366ff);
        colors[2] = mTypedArray.getColor(R.styleable.MyView_circleColor3, 0xff00ccff);
        initView();
        mTypedArray.recycle();

    }

    /***
     * 这个是在xml创建并且指定style的时候被调用
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }
    /**
     * 分割的数量
     *
     * @param maxColorNumber 数量
     */
    public void setMaxColorNumber(int maxColorNumber) {
        this.maxColorNumber = maxColorNumber;
        singlPoint = (float) 360 / (float) maxColorNumber;
        invalidate();
    }
    /**
     * 是否是线条
     *
     * @param line true 是 false否
     */
    public void setLine(boolean line) {
        isLine = line;
        invalidate();
    }
    public int getCircleWidth() {
        return circleWidth;
    }
    /**
     * 空白出颜色背景
     *
     * @param roundBackgroundColor
     */
    public void setRoundBackgroundColor(int roundBackgroundColor) {
        this.roundBackgroundColor = roundBackgroundColor;
        paint.setColor(roundBackgroundColor);
        invalidate();
    }
    /**
     * 刻度字体颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        mPaintText.setColor(textColor);
        invalidate();
    }
    /**
     * 刻度字体大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mPaintText.setTextSize(textSize);
        invalidate();
    }

    /**
     * 渐变颜色
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("colors length < 2");
        }
        this.colors = colors;
        sweepGradientInit();
        invalidate();
    }
    /**
     * 间隔角度大小
     *
     * @param lineWidth
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        invalidate();
    }
    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,w, getContext().getResources().getDisplayMetrics());
    }
    /**
     * 圆环宽度
     *
     * @param roundWidth 宽度
     */
    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
        if (roundWidth > circleCenter) {
            this.roundWidth = circleCenter;
        }
        radius = (int) (circleCenter - this.roundWidth / 2); // 圆环的半径
        oval.left = circleCenter - radius;
        oval.right = circleCenter + radius;
        oval.bottom = circleCenter + radius;
        oval.top = circleCenter - radius;
        paint.setStrokeWidth(this.roundWidth);
        invalidate();
    }
    /**
     * 圆环的直径
     *
     * @param circleWidth 直径
     */
    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
        circleCenter = circleWidth / 2;

        if (roundWidth > circleCenter) {
            roundWidth = circleCenter;
        }
        setRoundWidth(roundWidth);
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋转 不然是从0度开始渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(60, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }
    /**
     * 渐变初始化
     */
    public void sweepGradientInit() {
        //渐变颜色
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋转 不然是从0度开始渐变
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }
    public void initView() {

        circleCenter = circleWidth / 2;//半径
        singlPoint = (float) 360 / (float) maxColorNumber;
        radius = (int) (circleCenter - roundWidth / 2); // 圆环的半径
        sweepGradientInit();
        mPaintText = new Paint();
        mPaintText.setColor(textColor);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(20);
        mPaintText.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(roundBackgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);

        // 用于定义的圆弧的形状和大小的界限
        oval = new RectF(circleCenter - radius, circleCenter - radius, circleCenter + radius, circleCenter + radius);

    }


    /** 
          * 要画图形，最起码要有三个对象： 
          * 1.颜色对象 Color 
          * 2.画笔对象 Paint 
          * 3.画布对象 Canvas 
          */
    @Override
    protected void onDraw(Canvas canvas) {
//         Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
//        //设置字体大小  
//        paint.setTextSize(100);
//
//        //让画出的图形是空心的  
//       paint.setStyle(Paint.Style.STROKE);
//       //设置画出的线的 粗细程度  
//       paint.setStrokeWidth(5);
//        //画圆  
//        canvas.drawCircle(300,350,200,paint);
//        canvas.drawCircle(300,350,180,paint);
//
//        super.onDraw(canvas);
        //		/**
//		 * 画最外层的大圆环
//		 */
        //背景渐变颜色
        paint.setShader(sweepGradient);
        canvas.drawArc(oval, -90, (float) (progress * 3.6), false, paint);
        paint.setShader(null);
        //是否是线条模式
        if (!isLine) {
            float start = -90f;
            float p = ((float) maxColorNumber / (float) 100);
            p = (int) (progress * p);
            for (int i = 0; i < p; i++) {
                paint.setColor(roundBackgroundColor);
                canvas.drawArc(oval, start + singlPoint - lineWidth, lineWidth, false, paint); // 绘制间隔快
                start = (start + singlPoint);
            }
        }
        //绘制剩下的空白区域
        paint.setColor(roundBackgroundColor);
        /**
         * oval :指定圆弧的外轮廓矩形区域。
         * startAngle: 圆弧起始角度，单位为度。
         * sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度,从右中间开始为零度。
         * useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。关键是这个变量，下
         */
        canvas.drawArc(oval, -90, (float) (-(100 - progress) * 3.6), false, paint);
        //绘制文字刻度
        for (int i = 1; i <= 12; i++) {
            canvas.save();// 保存当前画布
            canvas.rotate(360 / 12 * i,circleCenter, circleCenter);//计算刻度显示位置
//          canvas.drawText(i + "", circleCenter, circleCenter - radius + roundWidth / 2 + getDpValue(4) + textSize, mPaintText);
//            canvas.drawText(i + "", circleCenter, circleCenter - radius + roundWidth / 2 + getDpValue(4) + textSize, mPaintText);
            canvas.restore();//
        }

    }

    OnProgressScore onProgressScore;

    public interface OnProgressScore {
        void setProgressScore(float score);

    }

    public synchronized void setProgress(final float p) {
        progress = p;
        postInvalidate();
    }

    /**
     * @param p
     */
    public synchronized void setProgress(final float p, OnProgressScore onProgressScore) {
        this.onProgressScore = onProgressScore;
        progress = p;
        postInvalidate();
    }





}
