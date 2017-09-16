package com.example.administrator.songshuapplication.modle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by yangtong on 2017/7/17.
 */

public class MyGroupView extends View {
    private Paint mPaint;
//    private Paint mPaintPoint;
    private int mWidth;
    private int mHeight;
    private Path mPath;
    private static final int NEED_INVALIDATE=0X23;
    private int count=0;
    private int size=0;
    private boolean isAdd=true;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case NEED_INVALIDATE:
                    count+=5;
                    if (count>160){
                        count=0;
                    }
                    if (isAdd){
                        size++;
                        if (size>25){
                            isAdd=false;
                        }
                    }else {
                        size--;
                        if (size<-26){
                            isAdd=true;
                        }
                    }
                    invalidate();
                    handler.sendEmptyMessageDelayed(NEED_INVALIDATE,50);
                    break;
            }
        }
    };
    private int is=1;

    public MyGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setTextSize(50);
        mPath=new Path();
//        mPaintPoint=new Paint();
//        mPaintPoint.setColor(Color.RED);
//        mPaintPoint.setStrokeWidth(10);
//        mPaintPoint.setStyle(Paint.Style.STROKE);
        handler.sendEmptyMessageDelayed(NEED_INVALIDATE,100);
    }

    public MyGroupView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mPath.lineTo(getWidth(),mHeight / 2);
        mPath.reset();//重置，避免波浪线加宽
        mPath.moveTo(count, mHeight / 2);//方法将起始轮廓点移至x，y坐标点，默认情况为0,0点
  if (is==0) {
    for (int i = 0; i < 6; i++) {
        mPath.rQuadTo(50, size, 80, 0);
        mPath.rQuadTo(50, -size, 80, 0);
     }
 }
        if (is==1){

            mPath.lineTo(getWidth(),mHeight / 2);
        }
        canvas.drawPath(mPath, mPaint);
//        canvas.drawCircle(mWidth/2,mHeight/2,150,mPaintPoint);
    }

          public synchronized void  onisif(int is){
              this.is = is;
           invalidate();
          }

}





