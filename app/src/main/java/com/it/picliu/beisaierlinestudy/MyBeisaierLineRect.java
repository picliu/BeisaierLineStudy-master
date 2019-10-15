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
public class MyBeisaierLineRect extends View {

    private Point leftTopPoint;
    private Point rightTopPoint;
    private Point leftBottomPoint;
    private Point rightBottomPoint;
    private PointF controPoint;
    private Paint mPaint;
    private int x;
    private int y;
    private int centerX;
    private int centerY;

    public MyBeisaierLineRect(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBeisaierLineRect(Context context) {
        super(context);
    }

    private void initConfig() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        leftTopPoint = new Point(0, 0);
        rightTopPoint = new Point(0, 0);
        leftBottomPoint = new Point(0, 0);
        rightBottomPoint = new Point(0, 0);

        controPoint = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initConfig();
        centerX = w / 2;
        centerY = h / 2 ;

        leftTopPoint.x = centerX - 100;
        leftTopPoint.y = centerY;

        leftBottomPoint.x = centerX - 100;
        leftBottomPoint.y = centerY + 100;

        resertPoint(rightBottomPoint, centerX - 100, rightTopPoint, centerY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int) event.getX();
        y = (int) event.getY();

        rightBottomPoint.x = x;
        rightBottomPoint.y = y + 50;

        rightTopPoint.x = x;
        rightTopPoint.y = y - 50;


        controPoint.x = rightTopPoint.x - Math.abs(rightTopPoint.x - leftTopPoint.x) / 2;
        if (rightTopPoint.y < leftTopPoint.y) {
            controPoint.y = rightTopPoint.y + Math.abs(rightTopPoint.y - leftBottomPoint.y) / 2;
        } else {
            controPoint.y = rightBottomPoint.y - Math.abs(leftTopPoint.y - rightBottomPoint.y) / 2;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                resertPoint(rightBottomPoint, centerX - 100, rightTopPoint, centerY);
                break;
        }
        postInvalidate();
        return true;
    }

    private void resertPoint(Point rightBottomPoint, int i, Point rightTopPoint, int centerY) {
        rightBottomPoint.x = i;
        rightBottomPoint.y = centerY + 100;

        rightTopPoint.x = i;
        rightTopPoint.y = centerY;

        controPoint.x = i;
        controPoint.y = centerY + 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(controPoint.x, controPoint.y, mPaint);

        Path path = new Path();
        path.moveTo(leftTopPoint.x, leftTopPoint.y);
        path.quadTo(controPoint.x, controPoint.y, rightTopPoint.x, rightTopPoint.y);
        path.lineTo(rightBottomPoint.x, rightBottomPoint.y);
        path.quadTo(controPoint.x, controPoint.y, leftBottomPoint.x, leftBottomPoint.y);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
