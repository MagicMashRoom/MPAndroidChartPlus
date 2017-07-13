package com.hqyxjy.ldf.mpandroidchartplus.other;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.hqyxjy.ldf.mpandroidchartplus.R;

/**
 * Created by jian on 16/7/1.
 */
public class DashLineProgressBar extends android.support.v7.widget.AppCompatImageView {
    private static final int DEFAULT_RING_RADIUS = 100;
    private static final int DEFAULT_RING_WIDTH = 10;
    private static final int DEFAULT_SEGMENT = 30;
    private static final float DEFAULT_RING_INTERVAL_RATE = 0.0f;
    private static final float DEFAULT_PROGRESS_RATE = 0.8f;

    private static final float START_ANGLE = -90;

    /**
     * 中间进度圈的线中心到圈中心的距离
     */
    private int ringRadius = DEFAULT_RING_RADIUS;
    private int ringWidth = DEFAULT_RING_WIDTH;
    private int ringSegmentCount = DEFAULT_SEGMENT;
    private float ringIntervalRate = DEFAULT_RING_INTERVAL_RATE;
    private float ringProgress = DEFAULT_PROGRESS_RATE;
    private float lastRingProgress = 0f;
    private int ringBackgroundColor = Color.RED;
    private int outRingWidth = 0;
    private int inRingWidth = 0;

    private int ringFillColor = Color.GREEN;
    private int outlineColor = Color.GRAY;
    private int inlineColor = Color.GRAY;

    private ValueAnimator mAnimator;

    private Paint filledProgressPaint = new Paint();
    private Paint restProgressPaint = new Paint();
    private Paint outlinePaint = new Paint();
    private Paint inlinePaint = new Paint();

    public DashLineProgressBar(Context context) {
        this(context, null);
    }

    public DashLineProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashLineProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashLineProgressBar, defStyleAttr, 0);
        ringRadius = typedArray.getDimensionPixelSize(R.styleable.DashLineProgressBar_ring_radius, DEFAULT_RING_RADIUS);
        ringWidth = typedArray.getDimensionPixelSize(R.styleable.DashLineProgressBar_ring_width, DEFAULT_RING_WIDTH);
        outRingWidth = typedArray.getDimensionPixelSize(R.styleable.DashLineProgressBar_out_ring_width, DEFAULT_RING_WIDTH);
        inRingWidth = typedArray.getDimensionPixelSize(R.styleable.DashLineProgressBar_in_ring_width, DEFAULT_RING_WIDTH);
        ringSegmentCount = typedArray.getInt(R.styleable.DashLineProgressBar_ring_segment_count, DEFAULT_SEGMENT);
        ringIntervalRate = typedArray.getFloat(R.styleable.DashLineProgressBar_ring_interval_rate, DEFAULT_RING_INTERVAL_RATE);
        ringProgress = typedArray.getFloat(R.styleable.DashLineProgressBar_ring_progress, DEFAULT_PROGRESS_RATE);
        ringBackgroundColor = typedArray.getInt(R.styleable.DashLineProgressBar_ring_background_color, Color.RED);
        outlineColor = typedArray.getInt(R.styleable.DashLineProgressBar_ring_outline_color, Color.GRAY);
        inlineColor = typedArray.getInt(R.styleable.DashLineProgressBar_ring_inline_color, Color.GRAY);
        ringFillColor = typedArray.getInt(R.styleable.DashLineProgressBar_ring_fill_color, Color.GREEN);
        typedArray.recycle();

        initPaint(filledProgressPaint, ringFillColor, true);
        initPaint(restProgressPaint, ringBackgroundColor, false);
        initOutlinePaint();
        initInLinePaint();
    }

    private void initOutlinePaint() {
        outlinePaint.setStyle(Paint.Style.STROKE);
        outlinePaint.setAntiAlias(true);
        outlinePaint.setStrokeWidth(outRingWidth);
        outlinePaint.setColor(outlineColor);
    }

    private void initInLinePaint() {
        inlinePaint.setStyle(Paint.Style.STROKE);
        inlinePaint.setAntiAlias(true);
        inlinePaint.setStrokeWidth(inRingWidth);
        inlinePaint.setColor(inlineColor);
    }

    private void initPaint(Paint paint, int color, boolean drawIntervalFirst) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(ringWidth);
        paint.setColor(color);
        paint.setPathEffect(getPathEffect(drawIntervalFirst));

        paint.setStrokeCap(Paint.Cap.ROUND);

    }

    private PathEffect getPathEffect(boolean drawIntervalFirst) {
        //根据周长和小段的数据,计算出每段的长度,以及每段的实线与空断的长度
        float circumference = (float) (2 * Math.PI * ringRadius);
        float segment = circumference / ringSegmentCount;
        float solidLineLength = segment * (1 - ringIntervalRate);
        float dashedLineLength = segment * ringIntervalRate;
        PathEffect effects;
        if (drawIntervalFirst)
            effects = new DashPathEffect(new float[]{solidLineLength, dashedLineLength}, 0);
        else
            effects = new DashPathEffect(new float[]{solidLineLength, dashedLineLength}, solidLineLength);
        return effects;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRestProgressLine(canvas);
        drawFilledProgressLine(canvas);
        drawOutline(canvas);
        drawInline(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sideLength = ringRadius * 2 + ringWidth + outRingWidth * 2;
        setMeasuredDimension(sideLength, sideLength);
    }

    private void drawOutline(Canvas canvas) {
        Path path = new Path();
        int offsetX = getMeasuredWidth() / 2 - ringRadius;
        int offsetY = getMeasuredHeight() / 2 - ringRadius;
        RectF outline = new RectF(offsetX - outRingWidth, offsetY - outRingWidth, ringRadius * 2 + offsetX + outRingWidth, ringRadius * 2 + offsetY + outRingWidth);
        path.addArc(outline, 0, 360);
        canvas.drawPath(path, outlinePaint);
    }

    private void drawInline(Canvas canvas) {
        Path path = new Path();
        int offsetX = getMeasuredWidth() / 2;
        int offsetY = getMeasuredHeight() / 2;
        canvas.translate(offsetX, offsetY);
        RectF outline = new RectF(-ringRadius + inRingWidth, -ringRadius + inRingWidth, ringRadius - inRingWidth, ringRadius - inRingWidth);
        path.addArc(outline, 0, 360);
        canvas.drawPath(path, inlinePaint);
    }

    private void drawFilledProgressLine(Canvas canvas) {
        if (ringProgress > 0) {
            float sweepAngle = ringProgress * 360;
            Path path = initPath(sweepAngle);
            canvas.drawPath(path, filledProgressPaint);
        }
    }

    private void drawRestProgressLine(Canvas canvas) {
        if (ringProgress < 1) {
            float sweepAngle = -360 * (1 - ringProgress);
            Path path = initPath(sweepAngle);
            canvas.drawPath(path, restProgressPaint);
        }
    }

    private Path initPath(float sweepAngle) {
        Path path = new Path();

        int offsetX = getMeasuredWidth() / 2 - ringRadius;
        int offsetY = getMeasuredHeight() / 2 - ringRadius;
        //圆的外切正方形,即是圆的边界
        RectF ringBounds = new RectF(0 + offsetX, 0 + offsetY, ringRadius * 2 + offsetX, ringRadius * 2 + offsetY);
        path.addArc(ringBounds, START_ANGLE, sweepAngle);

        return path;
    }

    // TODO [refactor] 感觉困惑,没有明确方案
    public void setProgressWithAnimation(float progress, long animationDuration) {
        if (isProgressOutBounds(progress)) {
            return;
        }

        if (animationDuration >= 0) {
            if (mAnimator != null) {
                mAnimator.cancel();
            }
            mAnimator = ValueAnimator.ofFloat(0, progress);
            mAnimator.setDuration(animationDuration);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setProgress((float) animation.getAnimatedValue());
                }
            });

            mAnimator.start();
        } else {
            setProgress(progress);
        }
    }

    public void setProgressWithAnimationStartNotZero(float progress, long animationDuration) {
        if (isProgressOutBounds(progress)) {
            return;
        }

        if (animationDuration >= 0) {
            if (mAnimator != null) {
                mAnimator.cancel();
            }
            mAnimator = ValueAnimator.ofFloat(lastRingProgress, progress);
            mAnimator.setDuration(animationDuration);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setProgress((float) animation.getAnimatedValue());
                }
            });

            mAnimator.start();
        } else {
            setProgress(progress);
        }
        lastRingProgress = progress;
    }

    private boolean isProgressOutBounds(float progress) {
        return progress < 0 || progress > 1;
    }

    public void setProgress(float progress) {
        ringProgress = progress;
        invalidate();
    }

    public float getProgress() {
        return ringProgress;
    }

    public void setRingFillColor(int ringFillColor) {
        this.ringFillColor = ringFillColor;
    }
}
