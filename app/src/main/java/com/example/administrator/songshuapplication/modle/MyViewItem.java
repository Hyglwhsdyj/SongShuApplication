package com.example.administrator.songshuapplication.modle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yangtong on 2017/6/5.
 */

public class MyViewItem extends View {

    private Paint mPaint;
    private Typeface blodFont;
    private float[] broken;
    private int[] brokenTimes;

    public MyViewItem(Context context) {
        super(context);
    }
    public MyViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
         blodFont = Typeface.create("宋体", Typeface.BOLD);
        initView();
    }

    public MyViewItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //先初始化，背景设置偏黑色，初始化画笔。
    private void initView() {
//       setBackgroundColor(Color.parseColor("#222222"));
        mPaint = new Paint();
    }
    /**
      * 绘制标题
      */
    private void canvasTitle(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(25);
        mPaint.setTypeface(blodFont);
        canvas.drawText("心率",150,30,mPaint);
        canvas.drawText("平均--次/分钟",900 ,30,mPaint);
    }
//提供数据绘制图像
    public void XYb(float[] broken,int[] brokenTimes ){
        this.broken=broken;
        this.brokenTimes=brokenTimes;
    }
    //绘制XY轴
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //上下左右保持一定的距离
        //距离底部为this.getBottom()-100,距离左边为50
        Log.e("aaa",this.getBottom()+"");
        int marginBottom =this.getBottom()/2 ;
        int marginLeft = 100;
        int getRight=900;
        //初始化X轴
        mPaint.setColor(Color.parseColor("#c0c0c0"));
        canvas.drawLine(marginLeft, marginLeft-60, marginLeft, marginBottom,mPaint);
        //初始化Y轴
        mPaint.setStrokeWidth(3);
        canvas.drawLine(marginLeft, marginBottom, getRight+20 , marginBottom, mPaint);
        Log.e("ggg",this.getRight()+"f");
        //时间轴，这里我分成了6份，每份为1.5个小时，最后一份为4个小时，一份的宽度计算方式为：屏幕的宽度－距离右边的宽度－距离左边的宽度／6
        int xWidth = (getRight - 20 - marginLeft) / 6;
        int xLine = marginLeft;//起始位置
        //每一个时间点，都是逐个增加一份的宽度，这里我们new一个Integer数组来存储
      Integer[] xPoint = new Integer[7];
        for (int a = 0; a < 7; a++) {
            if (a==0){
                xPoint[a] = xLine;
            }else {
                xLine += xWidth;
                xPoint[a] = xLine;
            }

        }
        String[] times = {"23:00","00:30","2:00","3:30","5:00","6:30","8:00"};
        for (int i = 0; i < xPoint.length; i++) {
          mPaint.setColor(Color.parseColor("#c0c0c0"));
            mPaint.setTextSize(20);
            Typeface font=Typeface.create("宋体", Typeface.BOLD);
            mPaint.setTypeface(font);
            mPaint.setTextAlign(Paint.Align.RIGHT);
//            if (i == 0) {
//                canvas.drawText(times[i]+"", marginLeft,
//                marginBottom + 50, mPaint);
                canvas.drawText(times[i],xPoint[i],
                        marginBottom + 50, mPaint);
            Log.e("aaa",xPoint[i]+"---xPoint");
//                } else {
//                 canvas.drawText(times[i], xPoint[i],
//                         marginBottom + 50, mPaint);
//                }
        }

        int[] atmosphereNum = {50, 100, 150};
//        int yHeight = (marginBottom - marginLeft) / 3;
        int yHeight=(marginLeft+150)/3;
        for (int i = 0;i < 3; i++) {
             mPaint.setStrokeWidth(3);
            int startY = marginBottom - yHeight * (i + 1);
            Log.e("aaa",startY+"i"+i);
            canvas.drawLine(marginLeft, startY, getRight +20, startY, mPaint);
             //绘制心率值
             canvas.drawText(atmosphereNum[i] + "", marginLeft -20, startY, mPaint);
        }
        //Y间距119~202||202~285
        //X间距100~244~388~532~676~820~964

        broken = new float[]{285f, 201f, 210f, 215f, 210f, 220f, 225f, 230f,235f,240f,203f,240f,250f,
          260f,202f,202f,285f, 280f, 270f, 260f, 265f, 260f, 250f, 250f,240f,245f,230f,235f,220f};
        brokenTimes = new int[]{100, 105, 110, 115, 120, 125, 130, 135,140,145,150,155,160,
           165,270,275,280,285,390,395,400,405,410,515,620,725,830,935,940};
        if(broken.length>1&&brokenTimes.length>1) {
            for (int i = 0; i < broken.length - 1; i++) {
                canvas.drawLine(brokenTimes[i], broken[i], brokenTimes[i + 1], broken[i + 1], mPaint);
            }
        }
//        float brokenSizeY = (marginBottom - marginLeft) / 500;//得到纵轴刻度值
//        float brokenSizeX = (getRight - 20 - marginLeft) / 144;
//        float lastX = brokenTimes[0] * brokenSizeX + marginLeft,
//                lastY = marginBottom - broken[0] * brokenSizeY;
//        for (int a = 0; a < broken.length; a++) {
//             float height = marginBottom - broken[a] * brokenSizeY;
//             float width = brokenTimes[a] * brokenSizeX + marginLeft;
////            canvas.drawCircle(width, height, 10, mPaint);
//            canvas.drawLine(lastX, lastY, width, height, mPaint);
//            lastX = width;
//            lastY = height;
//        }
        canvasTitle(canvas);
    }
}
