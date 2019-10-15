package com.it.picliu.beisaierlinestudy;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * *  @name:picliu
 * *  @date: 2019-10-15
 */
public class MyWaveView extends View {

    private int waveHeight;
    private int waveLength;
    private int waveColor;
    private Paint mPaint;
    private float originY;
    private int duration;
    private int viewWidthSize;
    private int viewHeightSize;
    private Path path;
    private ValueAnimator valueAnimator;
    private int moveX;
    private int moveY;

    //    默认水位上涨
    private boolean isUp = true;
    private float targY;
    private int waveSpeed;

    public MyWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrsConfig(context, attrs);

    }

    private void initAttrsConfig(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyWaveView);
        waveColor = typedArray.getColor(R.styleable.MyWaveView_waveColor, ContextCompat.getColor(context, R.color.colorAccent));
        waveHeight = (int) typedArray.getDimension(R.styleable.MyWaveView_waveHeight, 200);
        waveLength = (int) typedArray.getDimension(R.styleable.MyWaveView_waveLength, 400);
        originY = typedArray.getDimension(R.styleable.MyWaveView_originY, 500);
        duration = (int) typedArray.getFloat(R.styleable.MyWaveView_duration, 2000);
        waveSpeed = (int) typedArray.getFloat(R.styleable.MyWaveView_speed, 4);

        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setColor(waveColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        viewHeightSize = MeasureSpec.getSize(heightMeasureSpec);

//            涨水效果的默认起始位置
        if (originY == 0) {
            originY = viewHeightSize;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path = new Path();
        setPathData();
        canvas.drawPath(path, mPaint);
    }

    private void setPathData() {
        path.reset();
        /**
         * 实现思路：
         * 一个波长，包括波峰和波谷，第一反应是用三阶贝塞尔曲线，用两个弯折点来控制，然后波纹动画，动态控制两个弯折点的坐标实现波纹动画
         * 这种写法理论上是可行的，但是计算量稍微大点，借鉴别人的思路
         *
         * 一个波长的波峰和波谷，用两个二阶贝塞尔曲线拼接
         *
         * 实现动画的时候，理解为两段波浪   在屏幕外的一段，和屏幕内的一段或者多段，他们实现平移动画
         * 然后反复执行
         */

//        计算一个半个波长的长度
        float hafeWaveLength = waveLength / 2;

        /**
         * 第一个波浪的起始位置，应该在屏幕外的一个波长长度，即  ： -waveLength
         * 一个波长结束绘制下一个波浪，循环计算一屏幕内能装下多少个波浪 即 i += waveLength
         *
         * 注意： 屏幕结束位置，可能放不下一整个波浪长度，会造成缺的，因此多画一个完整的波浪 补齐 即 i < viewWidthSize+waveLength
         *
         */

//        path.moveTo(-waveLength, originY);
        /**
         * 这里动画的执行即为 起始坐标的移动
         */
        targY = originY + moveY;

        path.moveTo(-waveLength + moveX, originY + moveY);


        float v = (viewHeightSize - (targY + waveHeight)) / viewHeightSize;

        if (v >= 1) {
            Toast.makeText(getContext(), "加载完成", Toast.LENGTH_SHORT).show();
            valueAnimator.cancel();
        }

        for (int i = -waveLength; i < viewWidthSize + waveLength; i += waveLength) {
//         如果使用path.quadTo(); 要绘制多个波浪，不科学   因此 根据相对坐标去绘制挨着的的波浪， 相当于一个波浪的结束是下一个波浪的开始
            path.rQuadTo(hafeWaveLength / 2, waveHeight, hafeWaveLength, 0);
            path.rQuadTo(hafeWaveLength / 2, -waveHeight, hafeWaveLength, 0);
        }
        path.lineTo(viewWidthSize, viewHeightSize);
        path.lineTo(0, viewHeightSize);
        path.close();
    }

    public void startAnimator() {
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
/**
 * 这里平移动画 可以理解为，从起始位置开始，移动到一个波浪的末尾，
 * 即为屏幕外多余的一个波浪移动结束后即为结束
 * 总共移动了一个波长的距离
 * animation.getAnimatedValue()即为动画执行时，起始位置当前的位置
 */
                float fraction = (float) animation.getAnimatedValue();
                moveX = (int) (waveLength * fraction);

                if (isUp) {
                    moveY -= waveSpeed;
                } else {
                    moveY += waveSpeed;
                }
                postInvalidate();
            }
        });
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        匀速加速器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
    }


    public void cancelAnimotor() {
        if (valueAnimator.isRunning()) {
            valueAnimator.cancel();
        } else {
            valueAnimator.start();
        }
    }

    public void changeirectionD() {
        if (isUp) {
            isUp = false;
        } else {
            isUp = true;
        }
    }
}
