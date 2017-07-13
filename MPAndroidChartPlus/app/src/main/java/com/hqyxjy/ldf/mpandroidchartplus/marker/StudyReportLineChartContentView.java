package com.hqyxjy.ldf.mpandroidchartplus.marker;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by ldf on 17/6/23.
 */

public class StudyReportLineChartContentView extends LinearLayout{
    /**
     * 记录上次滑动的坐标
     */
    private int mLastXPosition = 0;
    /**
     * 记录上次滑动的坐标
     */
    private int mLastYPosition = 0;

    public StudyReportLineChartContentView(Context context) {
        super(context);
    }

    public StudyReportLineChartContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StudyReportLineChartContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 获取点击的X值
        int x = (int) ev.getX();
        // 获取点击的Y值
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastXPosition;
                int deltaY = y - mLastYPosition;
                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                    return false;
                }
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        // 重置最后触摸位置的坐标
        mLastXPosition = x;
        mLastYPosition = y;
        return super.dispatchTouchEvent(ev);
    }
}
