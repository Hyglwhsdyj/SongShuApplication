package com.example.administrator.songshuapplication.modle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.songshuapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/16.
 */

public class ChartView extends View {
    private List mPointList = new ArrayList();
    private int mPointY = 0;
    private Paint mPoint = new Paint();   //画笔

    public ChartView(Context context, AttributeSet attrs) {
        // TODO Auto-generated constructor stub
        this(context, attrs, 0);
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        //初始化画笔
        mPoint.setColor(getContext().getResources().getColor(
                R.color.white));
        mPoint.setStrokeWidth(4);
        mPoint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        //mPointY = (int) (Math.random() * 100);
        if (mPointList.size() >= 2) {
            for (int k = 0; k < -1 + mPointList.size(); k++) {
                paramCanvas.drawLine(((Point) mPointList.get(k)).x,
                        ((Point) mPointList.get(k)).y,
                        ((Point) mPointList.get(k + 1)).x,
                        ((Point) mPointList.get(k + 1)).y, mPoint);
            }
        }

        Point localPoint1 = new Point(getWidth(), mPointY);
        int i = mPointList.size();
        int j = 0;
        if (i > 101) {                    //最多绘制100个点，多余的出栈
            mPointList.remove(0);
            while (j < 100) {
                Point localPoint3 = (Point) mPointList.get(j);
                localPoint3.x = (-7 + localPoint3.x);
                j++;
            }
            mPointList.add(localPoint1);
            return;
        }

        while (j < mPointList.size()) {    //每新产生使前面的每一个点左移7
            Point localPoint2 = (Point) mPointList.get(j);
            localPoint2.x = (-7 + localPoint2.x);
            j++;
        }
        mPointList.add(localPoint1);
    }

    public final void ClearList() {
        mPointList.clear();
    }

    public final synchronized void AddPointToList(int paramInt) {
        mPointY = paramInt;
        invalidate();//重绘
    }

    public void stop(){
        mPointList.clear();
        invalidate();
    }

}
