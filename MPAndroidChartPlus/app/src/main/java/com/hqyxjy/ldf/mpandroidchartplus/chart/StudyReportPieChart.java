package com.hqyxjy.ldf.mpandroidchartplus.chart;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.hqyxjy.ldf.mpandroidchartplus.R;
import com.hqyxjy.ldf.mpandroidchartplus.model.KnowledgeMasteryReport;
import com.hqyxjy.ldf.mpandroidchartplus.other.DS;

import java.util.ArrayList;

/**
 * Created by ldf on 17/6/13.
 */

public class StudyReportPieChart extends LinearLayout{
    private Context context;
    private KnowledgeMasteryReport report;
    private View view;
    private PieChart chart;
    private PieDataSet dataSet;

    public StudyReportPieChart(Context context) {
        super(context);
        initView(context);
    }

    public StudyReportPieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public StudyReportPieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        report = new KnowledgeMasteryReport();
        view = LayoutInflater.from(context).inflate(R.layout.study_report_piechart, this, true);
        chart = (PieChart) view.findViewById(R.id.chart);
        configChart();
    }

    private void configChart() {
        chart.setMaxAngle(360f);
        chart.animateY(800);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);
        chart.setHoleRadius(42.7f);  //Hole半径
        chart.setOuterDecorateRadius(8.3f); //Hole半透明圈
        chart.setTransparentCircleRadius(47.7f);
        chart.setTransparentCircleAlpha(204);//白色透明度80%
        chart.setDrawCenterText(true);
        chart.setCenterTextSize(10f);
        chart.setCenterTextColor(Color.parseColor("#666666"));
        chart.setHighlightPerTapEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setUsePercentValues(true);
        chart.setDrawEntryLabels(false);
        chart.setExtraTopOffset(15f);
        chart.setExtraBottomOffset(15f);
    }

    private void configData() {
        float weakKnowledgeCount = DS.toFloat(report.getWeakKnowledgeCount());
        float knowKnowledgeCount = DS.toFloat(report.getKnowKnowledgeCount());
        float familiarKnowledgeCount = DS.toFloat(report.getFamiliarKnowledgeCount());
        float masterKnowledgeCount = DS.toFloat(report.getMasterKnowledgeCount());
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(knowKnowledgeCount, "了解"));
        entries.add(new PieEntry(weakKnowledgeCount, "薄弱"));
        entries.add(new PieEntry(masterKnowledgeCount, "精通"));
        entries.add(new PieEntry(familiarKnowledgeCount, "熟悉"));

        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#ffb300"));//了解
        colors.add(Color.parseColor("#ff8585"));//薄弱
        colors.add(Color.parseColor("#40baff"));//精通
        colors.add(Color.parseColor("#98e67b"));//掌握

        if(entries.size() != 0) {
            dataSet = new PieDataSet(entries, "");
            dataSet.setSliceSpace(0.67f);
            dataSet.setColors(new ArrayList<Integer>());
            dataSet.setColors(colors);
            dataSet.setValueTextColors(new ArrayList<Integer>());
            dataSet.setValueTextColors(colors);
            dataSet.setValueLinePart1Length(0.48f);
            dataSet.setValueLinePart2Length(1.2f);
            dataSet.setValueLineColor(Color.WHITE);
            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            Formatter formatter = new Formatter();
            dataSet.setValueFormatter(formatter);
            dataSet.setValueTextSize(12f);
            show();
        }
    }

    private void show() {
        PieData data = new PieData(dataSet);
        chart.setData(data);
        chart.invalidate();
    }

    public void notifyDataChanged(KnowledgeMasteryReport report) {
        this.report = report;
        configData();
    }

    class Formatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry,
                                        int dataSetIndex, ViewPortHandler viewPortHandler) {
            PieEntry pieEntry = (PieEntry) entry;
            return pieEntry.getLabel();
        }
    }
}
