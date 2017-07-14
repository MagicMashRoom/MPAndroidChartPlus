package com.hqyxjy.ldf.mpandroidchartplus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hqyxjy.ldf.mpandroidchartplus.R;
import com.hqyxjy.ldf.mpandroidchartplus.chart.StudyReportPieChart;
import com.hqyxjy.ldf.mpandroidchartplus.model.KnowledgeMasteryReport;

/**
 * Created by ldf on 17/7/13.
 */

public class PieChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        StudyReportPieChart pieChart = (StudyReportPieChart) findViewById(R.id.pie_chart);
        KnowledgeMasteryReport report = new KnowledgeMasteryReport();
        report.setKnowKnowledgeCount("96");
        report.setFamiliarKnowledgeCount("84");
        report.setMasterKnowledgeCount("72");
        report.setWeakKnowledgeCount("60");
        pieChart.notifyDataChanged(report);
    }
}
