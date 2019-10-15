package com.it.picliu.beisaierlinestudy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * *  @name:picliu
 * *  @date: 2019-10-10
 */
public class MyBeisaierLineView extends View {

    private Paint mPaint;
    private Point startPoint;
    private Point endPoint;
    private int centerX, centerY;
    private PointF controlPointF;

    public MyBeisaierLineView(Context context) {
        super(context);
    }

    public MyBeisaierLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initConfig() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(60);
        startPoint = new Point(0, 0);
        endPoint = new Point(0, 0);
        controlPointF = new PointF(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(startPoint.x, startPoint.y, mPaint);
        canvas.drawPoint(endPoint.x, endPoint.y, mPaint);
//        canvas.drawPoint(controlPointF.x, controlPointF.y, mPaint);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(8);

        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.quadTo(controlPointF.x, controlPointF.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, mPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initConfig();

        centerX = w / 2;
        centerY = h / 2;

        // 初始化数据点和控制点的位置
        startPoint.x = centerX - 200;
        startPoint.y = centerY;
        endPoint.x = centerX + 200;
        endPoint.y = centerY;
        controlPointF.x = centerX;
        controlPointF.y = centerY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controlPointF.x = event.getX();
        controlPointF.y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                controlPointF.x = centerX;
                controlPointF.y = centerY;
                break;
        }
        postInvalidate();
        return true;
    }
}
