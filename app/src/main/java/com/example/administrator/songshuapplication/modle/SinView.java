package com.example.administrator.songshuapplication.modle;

/**
 * Created by zhangmei on 2017/3/15.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.administrator.songshuapplication.R;


public class SinView extends View {

    private int height;
    private int width;

    private Path path;
    private Paint paint;

    private float waveHeight;

    private int lineColor;//线的颜色
    private int backColor;//背景色
    private float amplitude;//振幅
    private float frequency;//频率

    private float startAngle = (float) (Math.PI/4);

    private int i =0;
    private float y1;
    private float y=0;

    public SinView(Context context) {
        this(context,null);
    }

    public SinView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    public SinView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    private void initView(Context context,AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.SinView);
        amplitude = ta.getDimension(R.styleable.SinView_amplitude,100);
        frequency = ta.getFloat(R.styleable.SinView_frequency,200);
        lineColor = ta.getColor(R.styleable.SinView_lineColor,Color.WHITE);
        backColor = ta.getColor(R.styleable.SinView_backColor,Color.TRANSPARENT);

        paint = new Paint();
        paint.setColor(lineColor);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        path = new Path();

        ta.recycle();
    }
    //是在布局发生变化时的回调函数，间接回去调用onMeasure, onLayout函数重新布局
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);
        if(height == 0){
            height = getMeasuredHeight();
            waveHeight = Math.min(height, amplitude)-20;
            Log.e("KFJKFJ", "高度"+waveHeight+"getMeasuredHeight()"+getMeasuredHeight()+"amplitude"+amplitude);
        }
        if(width == 0){
            width = getMeasuredWidth();
        }
    }
    public void sinViewShow(float y){
        y1 = y;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawColor(backColor);
        for(i =0;i<width;i+=5){
            if (y1!=0) {
                y = (float) (waveHeight / 2 + waveHeight / 2 * Math.sin(i * (2 * Math.PI / frequency) + startAngle)) + 10;
            }else {
                y=50;
            }

            if(i==0){
                //设置path的起点
                path.moveTo(0,y);
            }else{
                //连线path.quadTo(float x1, float y1, float x2, float y2)形成平滑的曲线，该曲线又称为"贝塞尔曲线"
                path.lineTo(i,y);
            }
        }
//        invalidate();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                i = 0;
                // 重置绘制路线，即隐藏之前绘制的轨迹
                path.reset();
                startAngle+=(Math.PI/4);
                Log.i("123",startAngle + "");
                postInvalidate();
            }
        }, 200);
        canvas.drawPath(path, paint);
    }
}