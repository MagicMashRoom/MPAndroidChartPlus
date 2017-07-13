package com.hqyxjy.ldf.mpandroidchartplus.chart;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ldf on 17/7/13.
 */

class StudyReportSummary implements Parcelable {
    public String date = "";//统计日期 dateRange
    public String questionCount = "";//答题数
    public String rank = "";//排名
    public String duration = "";//答题时长 单位为分钟
    public String accuracy = "";//答题正确率
    public String periodValue = "";

    /*--------------------------------------------------------------------------------------------*/

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }

    public String getPeriodValue() {
        return periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    /*--------------------------------------------------------------------------------------------*/

    public StudyReportSummary() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.questionCount);
        dest.writeString(this.rank);
        dest.writeString(this.duration);
        dest.writeString(this.accuracy);
        dest.writeString(this.periodValue);
    }

    protected StudyReportSummary(Parcel in) {
        int tmpPeriodType = in.readInt();
        this.date = in.readString();
        this.questionCount = in.readString();
        this.rank = in.readString();
        this.duration = in.readString();
        this.accuracy = in.readString();
        this.periodValue = in.readString();
    }

    public static final Creator<StudyReportSummary> CREATOR = new Creator<StudyReportSummary>() {
        @Override
        public StudyReportSummary createFromParcel(Parcel source) {
            return new StudyReportSummary(source);
        }

        @Override
        public StudyReportSummary[] newArray(int size) {
            return new StudyReportSummary[size];
        }
    };
}