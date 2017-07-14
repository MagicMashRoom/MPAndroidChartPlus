package com.hqyxjy.ldf.mpandroidchartplus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hqyxjy.ldf.mpandroidchartplus.R;
import com.hqyxjy.ldf.mpandroidchartplus.chart.StudyReportLineChart;
import com.hqyxjy.ldf.mpandroidchartplus.model.StudyReportSummary;

import java.util.ArrayList;

/**
 * Created by ldf on 17/7/13.
 */

public class LineChartActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);
        StudyReportLineChart lineChart = (StudyReportLineChart) findViewById(R.id.line_chart);
        final ArrayList<StudyReportSummary> studyReportSummarizeList = new ArrayList<>();
        initData(studyReportSummarizeList);
        lineChart.setData(studyReportSummarizeList);
        lineChart.notifyDataChanged();
    }

    private void initData(ArrayList<StudyReportSummary> studyReportSummarizeList) {
        StudyReportSummary summary = new StudyReportSummary();
        summary.setDate("1.7-1.14");
        summary.setAccuracy("98");
        summary.setQuestionCount("100");
        summary.setDuration("240");
        summary.setRank("19.68");
        studyReportSummarizeList.add(summary);
        StudyReportSummary summary1 = new StudyReportSummary();
        summary.setDate("1.15-1.22");
        summary.setAccuracy("12");
        summary.setQuestionCount("100");
        summary.setDuration("240");
        summary.setRank("45.68");
        studyReportSummarizeList.add(summary1);
        StudyReportSummary summary2 = new StudyReportSummary();
        summary.setDate("1.23-1.30");
        summary.setAccuracy("44");
        summary.setQuestionCount("100");
        summary.setDuration("240");
        summary.setRank("72.12");
        studyReportSummarizeList.add(summary2);
        StudyReportSummary summary3 = new StudyReportSummary();
        summary.setDate("1.31-2.6");
        summary.setAccuracy("84");
        summary.setQuestionCount("100");
        summary.setDuration("240");
        summary.setRank("97");
        studyReportSummarizeList.add(summary3);
        StudyReportSummary summary4 = new StudyReportSummary();
        summary.setDate("2.7-1.14");
        summary.setAccuracy("98");
        summary.setQuestionCount("0");
        summary.setDuration("240");
        summary.setRank("19.68");
        studyReportSummarizeList.add(summary4);
        StudyReportSummary summary8 = new StudyReportSummary();
        summary.setDate("2.15-2.22");
        summary.setAccuracy("18");
        summary.setQuestionCount("0");
        summary.setDuration("120");
        summary.setRank("56.68");
        studyReportSummarizeList.add(summary8);
        StudyReportSummary summary5 = new StudyReportSummary();
        summary.setDate("2.23-3.2");
        summary.setAccuracy("79");
        summary.setQuestionCount("20");
        summary.setDuration("240");
        summary.setRank("64.8");
        studyReportSummarizeList.add(summary5);
        StudyReportSummary summary6 = new StudyReportSummary();
        summary.setDate("3.3-3.10");
        summary.setAccuracy("98");
        summary.setQuestionCount("10");
        summary.setDuration("240");
        summary.setRank("56.68");
        studyReportSummarizeList.add(summary6);
        StudyReportSummary summary7 = new StudyReportSummary();
        summary.setDate("3.11-3.18");
        summary.setAccuracy("9");
        summary.setQuestionCount("120");
        summary.setDuration("400");
        summary.setRank("23");
        studyReportSummarizeList.add(summary7);
    }
}
