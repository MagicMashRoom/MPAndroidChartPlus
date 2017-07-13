
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.Utils;

/**
 * Chart that draws lines, surfaces, circles, ...
 *
 * @author Philipp Jahoda
 */
public class LineChart extends BarLineChartBase<LineData> implements LineDataProvider {

    private boolean drawHighlightLineEnabled = true;

    public LineChart(Context context) {
        super(context);
    }

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new LineChartRenderer(this, mAnimator, mViewPortHandler);
    }

    @Override
    public LineData getLineData() {
        return mData;
    }

    @Override
    protected void onDetachedFromWindow() {
        // releases the bitmap in the renderer to avoid oom error
        if (mRenderer != null && mRenderer instanceof LineChartRenderer) {
            ((LineChartRenderer) mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }

    public void setLabelReqLength(int length){
        mXAxisRenderer.setLabelReqLength(length);
    }

    public void setDrawHighlightLineEnabled() {
        this.drawHighlightLineEnabled = true;
    }

    public void setDrawHighlightLineDisabled() {
        this.drawHighlightLineEnabled = false;
    }

    public boolean isDrawHightlightLine() {
        return drawHighlightLineEnabled;
    }

    public void setHighlightLabel(String label){
        mXAxisRenderer.setHighlightLabel(label);
    }

    public void setHighlightLabelColor(int color) {
        mXAxisRenderer.setHighlightLabelColor(color);
    }

    @Override
    public void calculateOffsets() {
        super.calculateOffsets();
        mViewPortHandler.restrainViewPort(0, Utils.convertDpToPixel(15f),
                0, Utils.convertDpToPixel(46.67f));
        prepareOffsetMatrix();
        prepareValuePxMatrix();
    }
}
