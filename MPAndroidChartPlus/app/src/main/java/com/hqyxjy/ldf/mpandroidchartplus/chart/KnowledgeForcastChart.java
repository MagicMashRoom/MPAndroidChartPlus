package com.hqyxjy.ldf.mpandroidchartplus.chart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.hqyxjy.ldf.mpandroidchartplus.R;
import com.hqyxjy.ldf.mpandroidchartplus.model.KnowledgeCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 知识点预测的条形图
 * Created by qipeng on 16/8/13.
 * Changed by ldf on 16/8/22
 * 直接在XML布局中声明该对象会产生一个没有数据的空chart
 * 拿到该对象实例后，需要setData和setListener
 * 设置完后notify刷新view的显示
 */

public class KnowledgeForcastChart extends LinearLayout{
    private Context context;
    private View view;
    private BarChart mChart;
    private LinearLayout markerView;
    private TextView allPointNum;
    private TextView highPointNum;
    private TextView categoryName;
    private OnBarSelectedListener listener;
    private List<KnowledgeCategory> knowledges = new ArrayList<>();
    private float position;
    private float lastPosition;
    private boolean isChartTranslated = false;
    private float groupSpace = 0.444f;
    private float barSpace = 0.07f;
    private float barWidth = 0.208f;

    public KnowledgeForcastChart(Context context) {
        super(context);
        initView(context);
    }

    public KnowledgeForcastChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public KnowledgeForcastChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.knowledge_forcast_chart, this, true);
        mChart = (BarChart) view.findViewById(R.id.cast_chart);
        markerView = (LinearLayout) view.findViewById(R.id.marker_view);
    }

    public void setData(ArrayList<KnowledgeCategory> list) {
        knowledges = list;
    }

    public void setListener(OnBarSelectedListener listener) {
        this.listener = listener;
    }

    public void notifyDataChanged() {
        if(knowledges.size() > 0) {
            show();
        }
    }

    public void show() {
        initMarkerView();
        setChartStyle();
        setAxis();
        setLegend();
        setChartClickListener();
        configBarData();
        scaleChartSize();
        focusCenter();
        mChart.animateY(500);
    }

    public void focusCenter() {
        calcFirstCenterPosition();
        selectChartItem();
        mChart.centerViewTo(position, 0, YAxis.AxisDependency.LEFT);
    }

    private void calcFirstCenterPosition() {
        switch (knowledges.size()) {
            case 0:
                position = 0.5f;
                break;
            case 1:
                position = 0.5f;
                break;
            case 2:
                position = 0.5f;
                break;
            case 3:
                position = 1.5f;
                break;
            case 4:
                position = 1.5f;
                break;
            default:
                position = 2.5f;
                break;
        }
    }

    private void initMarkerView() {
        allPointNum = (TextView) markerView.findViewById(R.id.all_knowledge_num);
        highPointNum = (TextView) markerView.findViewById(R.id.high_knowledge_num);
        categoryName = (TextView) markerView.findViewById(R.id.category_name);
    }

    public void setAxis() {
        configLeftAndRightAxis();
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(11f);
        xAxis.setAxisLineWidth(0.66f);
        xAxis.setAxisLineColor(Color.parseColor("#e8e8e8"));
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setTextColor(Color.parseColor("#999999"));
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value >= knowledges.size()) {
                    return "";
                }
                if(value < 0) {
                    return "";
                }
                return knowledges.get((int)value).getCategoryName();
            }
        });
        xAxis.setAxisMinimum(0f);
    }

    private void configLeftAndRightAxis() {
        //设置数值从零开始 勿删
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setAxisMinimum(0f);
    }

    public void setLegend() {
        Legend l = mChart.getLegend();
        l.setEnabled(true);
        l.setFormSize(0);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
    }

    public void setChartStyle() {
        mChart.setNoDataText("暂无图表数据");
        mChart.setDrawValueAboveBar(false);
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setScaleEnabled(false);
        mChart.setPinchZoom(true);
        mChart.setDragOffsetX(180f);
        mChart.setLabelReqLength(4);
        mChart.setHightlightLabelColor(Color.parseColor("#111111"));
        mChart.setMinRightOffset(0f);
        mChart.setMinLeftOffset(0f);
        mChart.setDragDecelerationEnabled(false);
    }

    public void setChartClickListener() {

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                position = e.getX();
                mChart.centerViewToAnimated((int)position + 0.5f, 0, YAxis.AxisDependency.LEFT, 500);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart.setOnChartGestureListener(
                new OnChartGestureListener() {
                    @Override
                    public void onChartGestureStart(
                            MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                    }

                    @Override
                    public void onChartGestureEnd(
                            MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                        selectChartItem();
                        if(isChartTranslated) {
                            mChart.centerViewToAnimated(position, 0, YAxis.AxisDependency.LEFT, 500);
                        }
                        isChartTranslated = false;
                    }

                    @Override
                    public void onChartLongPressed(MotionEvent me) {
                    }

                    @Override
                    public void onChartDoubleTapped(MotionEvent me) {
                    }

                    @Override
                    public void onChartSingleTapped(MotionEvent me) {
                    }

                    @Override
                    public void onChartFling(MotionEvent me1, MotionEvent me2,
                                             float velocityX, float velocityY) {
                    }

                    @Override
                    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                    }

                    @Override
                    public void onChartTranslate(MotionEvent me, float dX, float dY) {
                        isChartTranslated = true;
                        computeCenterPosition();
                    }
                });
    }

    private void scaleChartSize() {
        if(knowledges.size() >= 1) {
            Matrix mMatrix = new Matrix();
            float scaleX = 0.2f * knowledges.size() - 0.1f;
            mMatrix.postScale(scaleX, 1f);
            mChart.getViewPortHandler().refresh(mMatrix, mChart, false);
        }
    }

    private void configBarData() {
        BarData data = loadBarData();
        mChart.setData(null);
        mChart.setData(data);
        // specify the width each bar should have
        mChart.getBarData().setBarWidth(barWidth);
        mChart.groupBars(0 , groupSpace, barSpace);
    }

    @NonNull
    private BarData loadBarData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        for (int i = 0; i < knowledges.size(); i++) {
            yVals1.add(new BarEntry(i , knowledges.get(i).getTotalKnowledgeCount()));
            yVals2.add(new BarEntry(i , knowledges.get(i).getHighFrequencyKnowledgeCount()));
        }
        BarDataSet set1 = new BarDataSet(yVals1 , "");
        set1.setHighLightColor(Color.parseColor("#ffb300"));
        set1.setColor(Color.parseColor("#ffe8b3"));
        set1.setDrawValues(false);
        BarDataSet set2 = new BarDataSet(yVals2 , "");
        set2.setHighLightColor(Color.parseColor("#ff8585"));
        set2.setColor(Color.parseColor("#ffdbdb"));
        set2.setDrawValues(false);
        BarData data = new BarData(set1, set2);
        data.setValueTextSize(10f);
        return data;
    }

    public void selectChartItem() {
        if(position >= 0) {
            Highlight highlight1 = new Highlight(position, 1.0f, 0);
            Highlight highlight2 = new Highlight(position, 1.0f, 1);
            Highlight[] highlights = new Highlight[2];
            highlights[0] = highlight1;
            highlights[1] = highlight2;
            mChart.highlightValues(highlights);
            if((int)position >= knowledges.size()) {
                position = knowledges.size() - 1;
            }
            if(listener != null && position != lastPosition) {
                listener.onValueSelected((int)position);
                lastPosition = position;
                mChart.setHighlightLabel(knowledges.get((int)position).getCategoryName());
                categoryName.setText(knowledges.get((int)position).getCategoryName());
                allPointNum.setText(knowledges.get((int)position).getTotalKnowledgeCount() + "");
                highPointNum.setText(knowledges.get((int)position).getHighFrequencyKnowledgeCount() + "");
            }
        }
    }

    private void computeCenterPosition() {
        MPPointF pointF = mChart.getCenterOfView();
        float[] pixels = new float[2];
        pixels[0] = pointF.getX();
        pixels[1] = pointF.getY();
        mChart.getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(pixels);
        position = pixels[0];
        if(position < 0) {
            position = 0;
        }
        if(position >= knowledges.size()) {
            position = knowledges.size() - 1;
        }
        position = (int) position + 0.5f;
    }

    public interface OnBarSelectedListener {
        void onValueSelected(int position);
    }
}