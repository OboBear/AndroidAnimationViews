package com.obo.androidanimationviews.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by obo on 16/4/15.
 * Email:obo1993@gmail.com
 */
public class DashBoardCircleSeekBar extends View implements ValueAnimator.AnimatorUpdateListener {

    private float mDegreenValue = 0;
    private float mCurrentDegreenValue = 0;
    private float mStrokeWidth = 60;
    private float mSmallCircleRedius = 200;

    private Shader mSweepGradient;
    private ValueAnimator mValueAnimator;
    private Matrix mMatrix = new Matrix();
    private RectF mRectBlackBg;
    private Paint mDrawPaint = null;
    private Paint mTrianglePaint = null;
    private int[] mRainbow = {Color.argb(0, 0, 0, 0), Color.argb(60, 255, 0, 0), Color.argb(200, 56, 208, 253), Color.argb(0, 255, 255, 255),Color.argb(0, 255, 255, 255)};
    private float[] mPositions = {0f, 0.3f, 0.5f, 0.5f,1f};
    private float mStartDrawDegreen = 0;

    private Path mTrianglePath;

    public DashBoardCircleSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mValueAnimator = ValueAnimator.ofFloat(0, 0);
        mValueAnimator.addUpdateListener(this);
        mValueAnimator.setDuration(500);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDatas();
        canvas.drawArc(mRectBlackBg, mStartDrawDegreen, mCurrentDegreenValue, false, getDrawPaint());
        canvas.drawPath(mTrianglePath, getTrianglePaint());
    }

    private void initDatas() {
        if (mRectBlackBg == null) {
            float outWith = (getWidth() - mSmallCircleRedius * 2 - mStrokeWidth * 2) / 2;
            float outHeight = (getHeight() - mSmallCircleRedius * 2 - mStrokeWidth * 2) / 2;
            mRectBlackBg = new RectF(outWith + mStrokeWidth / 2, outHeight + mStrokeWidth / 2, getWidth() - outWith - mStrokeWidth / 2, getHeight() - outHeight - mStrokeWidth / 2);
        }
        if (mTrianglePath == null) {
            mTrianglePath = new Path();
        }
        mTrianglePath.reset();
        PointF p1 = getPoint(mCurrentDegreenValue, mSmallCircleRedius + mStrokeWidth);
        mTrianglePath.moveTo(p1.x, p1.y);
        p1 = getPoint(mCurrentDegreenValue - 1, mSmallCircleRedius - 3);
        mTrianglePath.lineTo(p1.x, p1.y);
        p1 = getPoint(mCurrentDegreenValue + 1, mSmallCircleRedius - 3);
        mTrianglePath.lineTo(p1.x, p1.y);
    }

    private Paint getDrawPaint() {
        if (mDrawPaint == null) {
            mDrawPaint = new Paint();
            mSweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, mRainbow, mPositions);
            mDrawPaint.setShader(mSweepGradient);
            mDrawPaint.setAntiAlias(true);
            mDrawPaint.setStrokeWidth(mStrokeWidth);
            mDrawPaint.setStyle(Paint.Style.STROKE);
        }
        mMatrix.setRotate(mCurrentDegreenValue - 0.5f * 360, getWidth() / 2, getHeight() / 2);
        mSweepGradient.setLocalMatrix(mMatrix);
        return mDrawPaint;
    }

    private Paint getTrianglePaint() {
        if (mTrianglePaint == null) {
            mTrianglePaint = new Paint();
            mTrianglePaint.setColor(Color.WHITE);
            mTrianglePaint.setAntiAlias(true);
        }
        return mTrianglePaint;
    }



    static PointF p = new PointF();

    /**
     *  get the position of the point which we know its degreen & width
     * @param degreen the rotate degreen of point
     * @param width radium for the point
     * @return the position of the point
     */
    private PointF getPoint(double degreen, float width) {
        float x = (float) (getWidth() / 2 + (width * Math.cos(degreen / 180 * Math.PI)));
        float y = (float) (getHeight() / 2 + (width * Math.sin(degreen / 180 * Math.PI)));
        p.set(x, y);
        return p;
    }

    public void setDegreenValue(float newDegreen) {

        mValueAnimator.setFloatValues(mDegreenValue, newDegreen);
        mValueAnimator.start();
        mDegreenValue = newDegreen;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        mCurrentDegreenValue = (Float) animation.getAnimatedValue();
        invalidate();
    }

    // set the start degreen for circle, and the area will be erased if it is
    public void setStartDrawDegreen(int startDrawDegreen) {
        mStartDrawDegreen = startDrawDegreen;
    }

    // set the Line width for Circle
    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
    }


    public void setSmallCircleRedius(float smallCircleRedius) {
        mSmallCircleRedius = smallCircleRedius;
    }

}
