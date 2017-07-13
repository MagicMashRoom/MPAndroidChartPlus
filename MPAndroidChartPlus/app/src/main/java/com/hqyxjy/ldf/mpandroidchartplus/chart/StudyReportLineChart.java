package com.hqyxjy.ldf.mpandroidchartplus.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.hqyxjy.ldf.mpandroidchartplus.R;
import com.hqyxjy.ldf.mpandroidchartplus.marker.StudyReportLineChartSpotSelectedView;
import com.hqyxjy.ldf.mpandroidchartplus.other.DS;
import com.hqyxjy.ldf.mpandroidchartplus.other.DashLineProgressBar;

import java.util.ArrayList;


/**
 * Created by ldf on 17/6/13.
 */

public class StudyReportLineChart extends LinearLayout{

    private Context context;
    private ArrayList<StudyReportSummary> reportSummaries = new ArrayList<>();
    private OnSpotSelectedListener listener;
    private View view;
    private LineChart chart;
    private TextView rankTv;
    private TextView answerCountTv;
    private TextView answerDurationTv;
    private DashLineProgressBar progressBar;
    private TextView answerCorrectRateTv;
    private TextView answerCorrectRateEmptyTv;
    private LinearLayout answerCorrectRateContainer;

    private float position;
    private float lastPosition;
    private boolean isChartTranslated = false;

    public StudyReportLineChart(Context context) {
        super(context);
        initView(context);
    }

    public StudyReportLineChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public StudyReportLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.study_report_linechart, this, true);
        chart = (LineChart) view.findViewById(R.id.chart);
        rankTv = (TextView) view.findViewById(R.id.rank_list_tv);
        answerCountTv = (TextView) view.findViewById(R.id.answer_question_amount_tv);
        answerDurationTv = (TextView) view.findViewById(R.id.answer_question_duration_tv);
        progressBar = (DashLineProgressBar) view.findViewById(R.id.answer_correct_rate_progressbar);
        answerCorrectRateTv = (TextView) view.findViewById(R.id.answer_correct_rate);
        answerCorrectRateEmptyTv = (TextView) view.findViewById(R.id.answer_correct_rate_empty);
        answerCorrectRateContainer = (LinearLayout) view.findViewById(R.id.answer_correct_rate_container);
        configChartStyle();
        configChartListener();
    }

    private void configChartListener() {
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if (e == null)
                    return;
                position = e.getX();
                chart.centerViewToAnimated((int)position , 0, YAxis.AxisDependency.LEFT, 500);
            }

            @Override
            public void onNothingSelected() {
            }
        });
        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                selectChartItem();
                if(isChartTranslated) {
                    chart.centerViewToAnimated(position, 0, YAxis.AxisDependency.LEFT, 500);
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
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

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

    private void configChartStyle() {
        StudyReportLineChartSpotSelectedView selectedView = new StudyReportLineChartSpotSelectedView(
                context, R.layout.view_study_report_linechart_markerview);
        selectedView.setChartView(chart);
        chart.setMarker(selectedView);

        chart.setDragOffsetX(180f);
        Description description = new Description();
        description.setText("");
        chart.setScaleEnabled(false);
        chart.setDescription(description);
        chart.getLegend().setEnabled(false);
        configAxis();
        chart.setMinLeftOffset(0);
        chart.setMinRightOffset(0f);
        chart.setExtraBottomOffset(25f);
        chart.setDragDecelerationEnabled(false);
        Formatter formatter = new Formatter();
        chart.getXAxis().setValueFormatter(formatter);
        chart.setLabelReqLength(12);
        chart.setMaxVisibleValueCount(120);
        chart.setDrawHighlightLineDisabled();
    }

    private void configAxis() {
        chart.getAxisLeft().setAxisMinimum(-1f);
        chart.getAxisLeft().setAxisMaximum(101f);
        chart.getAxisLeft().setAxisLineColor(Color.TRANSPARENT);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setTextColor(Color.TRANSPARENT);
        chart.getAxisRight().setAxisMinimum(-1f);
        chart.getAxisRight().setAxisMaximum(101f);
        chart.getAxisRight().setAxisLineColor(Color.TRANSPARENT);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setTextColor(Color.TRANSPARENT);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setGranularity(1f);
        chart.getXAxis().setAxisLineColor(Color.TRANSPARENT);
        chart.getXAxis().setYOffset(12.5f);
        chart.getXAxis().setTextColor(context.getResources().getColor(R.color.c5_1_80));
        chart.getXAxis().setTextSize(Utils.convertPixelsToDp(context.getResources().getDimension(R.dimen.t7)));
        chart.setHighlightLabelColor(Color.WHITE);
    }

    public void setData(ArrayList<StudyReportSummary> reportSummaries) {
        this.reportSummaries = reportSummaries;
        configData();
    }

    private void configData() {
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < reportSummaries.size(); i++) {
            float val = DS.toFloat(reportSummaries.get(i).getAccuracy());
            boolean meaningful = DS.toInt(reportSummaries.get(i).getQuestionCount()) == 0 ? false : true;
            Entry entry = new Entry(i , val);
            entry.setMeaningful(meaningful);
            values.add(entry);
        }

        LineDataSet lineDataSet;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet)chart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            lineDataSet = new LineDataSet(values, "DataSet 1");
            lineDataSet.setDrawIcons(false);
            lineDataSet.setColor(Color.WHITE);
            lineDataSet.setLineWidth(1.67f);
            lineDataSet.setDrawValues(false);
            lineDataSet.setDrawCircleHole(true);
            lineDataSet.setCircleColor(Color.WHITE);
            lineDataSet.setCircleRadius(4.67f);
            lineDataSet.setCircleColorHole(Color.parseColor("#ffd15d"));
            lineDataSet.setCircleHoleRadius(3f);
            lineDataSet.enableDashedLine(16f, 8f, 8f);
            lineDataSet.setDrawDiffLineTypeEnabled();

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(lineDataSet);
            LineData data = new LineData(dataSets);
            chart.setData(data);
        }
    }

    public void setListener(OnSpotSelectedListener listener) {
        this.listener = listener;
    }

    public void notifyDataChanged() {
        configChartMaxVisibleEntryCount();
        focusCenter();
        chart.animateY(500);
    }

    private void focusCenter() {
        calcFirstCenterPosition();
        firstSelectChartItem();
        chart.centerViewTo(position, 0, YAxis.AxisDependency.LEFT);
    }

    private void configChartMaxVisibleEntryCount() {
        chart.setVisibleXRangeMinimum(5);
        chart.setVisibleXRangeMaximum(5);
    }

    private void calcFirstCenterPosition() {
        position = reportSummaries.size() - 1;
    }

    private void computeCenterPosition() {
        MPPointF pointF = chart.getCenterOfView();
        float[] pixels = new float[2];
        pixels[0] = pointF.getX() + chart.getViewPortHandler().getChartWidth() / 10;
        pixels[1] = pointF.getY();
        chart.getTransformer(YAxis.AxisDependency.LEFT).pixelsToValue(pixels);
        position = (int)(pixels[0]);
        if(position < 0) {
            position = 0;
        }
        if(position >= reportSummaries.size()) {
            position = reportSummaries.size() - 1;
        }
    }

    public void firstSelectChartItem() {
        Log.e("ldf","firstSelectChartItem");
        if(position >= 0) {
            Highlight highlight = new Highlight(position, 1.0f, 0);
            chart.highlightValue(highlight);
            if((int)position >= reportSummaries.size()) {
                position = reportSummaries.size() - 1;
            }
            if(listener != null && position != lastPosition) {
                lastPosition = position;
            }
            updateMarkerView();
        }
    }

    public void selectChartItem() {
        Log.e("ldf","selectChartItem");
        if(position >= 0) {
            Highlight highlight = new Highlight(position, 1.0f, 0);
            chart.highlightValue(highlight);
            if((int)position >= reportSummaries.size()) {
                position = reportSummaries.size() - 1;
            }
            if(listener != null && position != lastPosition) {
                listener.onValueSelected(reportSummaries.get((int)position));
                lastPosition = position;
            }
            updateMarkerView();
        }
    }

    private void updateMarkerView() {
        chart.setHighlightLabel(reportSummaries.get((int)position).getDate());
        rankTv.setText("你击败了天津地区" + reportSummaries.get((int)position).getRank() + "%的同学");
        answerCountTv.setText(reportSummaries.get((int)position).getQuestionCount());
        answerDurationTv.setText(reportSummaries.get((int)position).getDuration());
        if(reportSummaries.get((int)position).getQuestionCount().equals("0")) {
            progressBar.setProgressWithAnimation(0, 300);
            answerCorrectRateContainer.setVisibility(GONE);
            answerCorrectRateEmptyTv.setVisibility(VISIBLE);
        } else {
            progressBar.setProgressWithAnimationStartNotZero(DS.toFloat(reportSummaries.get((int)position).getAccuracy()) / 100, 300);
            answerCorrectRateTv.setText(reportSummaries.get((int)position).getAccuracy() + "%");
            answerCorrectRateContainer.setVisibility(VISIBLE);
            answerCorrectRateEmptyTv.setVisibility(GONE);
        }
    }

    public interface OnSpotSelectedListener {
        void onValueSelected(StudyReportSummary summary);
    }

    class Formatter implements IAxisValueFormatter {
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int position = (int) value;
            if(position >= reportSummaries.size()) {
                return "";
            }
            if (position < 0) {
                return "";
            }
            if(reportSummaries.size() == 0) {
                return "";
            }
            return reportSummaries.get(position).getDate();
        }
    }
}
