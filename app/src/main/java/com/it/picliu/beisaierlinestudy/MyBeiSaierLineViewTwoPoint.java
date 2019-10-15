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
public class MyBeiSaierLineViewTwoPoint extends View {

    private Point startPoint;
    private Point endPoint;
    private PointF contorPoint1;
    private PointF contorPoint2;
    private int centerX;
    private int centerY;
    private Paint mPaint;

    public MyBeiSaierLineViewTwoPoint(Context context) {
        super(context);
    }

    public MyBeiSaierLineViewTwoPoint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initConfig() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);

        startPoint = new Point(0, 0);
        endPoint = new Point(0, 0);

        contorPoint1 = new PointF(0, 0);
        contorPoint2 = new PointF(0, 0);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initConfig();
        centerX = w / 2;
        centerY = h / 2 ;

        startPoint.x = centerX - 250;
        startPoint.y = centerY;

        endPoint.x = centerX + 250;
        endPoint.y = centerY;

        contorPoint1.x = centerX - 125;
        contorPoint1.y = centerY - 200;

        contorPoint2.x = centerX + 125;
        contorPoint2.y = centerY + 200;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (x > centerX) {
            contorPoint2.x = x;
            contorPoint2.y = y;
        } else {
            contorPoint1.x = x;
            contorPoint1.y = y;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPoint(startPoint.x, startPoint.y, mPaint);
        canvas.drawPoint(endPoint.x, endPoint.y, mPaint);

        canvas.drawPoint(contorPoint1.x, contorPoint1.y, mPaint);
        canvas.drawPoint(contorPoint2.x, contorPoint2.y, mPaint);

        mPaint.setColor(Color.RED);

        Path path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(contorPoint1.x, contorPoint1.y, contorPoint2.x, contorPoint2.y, endPoint.x, endPoint.y);

        canvas.drawPath(path, mPaint);
    }
}
