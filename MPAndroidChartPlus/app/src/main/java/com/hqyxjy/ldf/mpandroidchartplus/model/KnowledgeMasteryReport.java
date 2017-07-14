package com.hqyxjy.ldf.mpandroidchartplus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ldf on 17/6/14.
 */

public class KnowledgeMasteryReport implements Parcelable {
    private String subjectId = "";
    private String homeworkCount = "";//作业次数
    private String practiceCount = "";//练习次数
    private String questionCount = "";//问题次数
    private String allKnowledgeCount = "";//覆盖全部知识点数
    private String weakKnowledgeCount = "";//薄弱知识点数
    private String knowKnowledgeCount = "";//了解知识点数
    private String familiarKnowledgeCount = "";//熟悉知识点数
    private String masterKnowledgeCount = "";//精通知识点数
    public String periodValue = "";

    /*--------------------------------------------------------------------------------------------*/

    public String getHomeworkCount() {
        return homeworkCount;
    }

    public void setHomeworkCount(String homeworkCount) {
        this.homeworkCount = homeworkCount;
    }

    public String getPracticeCount() {
        return practiceCount;
    }

    public void setPracticeCount(String practiceCount) {
        this.practiceCount = practiceCount;
    }

    public String getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(String questionCount) {
        this.questionCount = questionCount;
    }

    public String getAllKnowledgeCount() {
        return allKnowledgeCount;
    }

    public void setAllKnowledgeCount(String allKnowledgeCount) {
        this.allKnowledgeCount = allKnowledgeCount;
    }

    public String getWeakKnowledgeCount() {
        return weakKnowledgeCount;
    }

    public void setWeakKnowledgeCount(String weakKnowledgeCount) {
        this.weakKnowledgeCount = weakKnowledgeCount;
    }

    public String getKnowKnowledgeCount() {
        return knowKnowledgeCount;
    }

    public void setKnowKnowledgeCount(String knowKnowledgeCount) {
        this.knowKnowledgeCount = knowKnowledgeCount;
    }

    public String getFamiliarKnowledgeCount() {
        return familiarKnowledgeCount;
    }

    public void setFamiliarKnowledgeCount(String familiarKnowledgeCount) {
        this.familiarKnowledgeCount = familiarKnowledgeCount;
    }

    public String getMasterKnowledgeCount() {
        return masterKnowledgeCount;
    }

    public void setMasterKnowledgeCount(String masterKnowledgeCount) {
        this.masterKnowledgeCount = masterKnowledgeCount;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getPeriodValue() {
        return periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    /*--------------------------------------------------------------------------------------------*/

    public KnowledgeMasteryReport() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subjectId);
        dest.writeString(this.homeworkCount);
        dest.writeString(this.practiceCount);
        dest.writeString(this.questionCount);
        dest.writeString(this.allKnowledgeCount);
        dest.writeString(this.weakKnowledgeCount);
        dest.writeString(this.knowKnowledgeCount);
        dest.writeString(this.familiarKnowledgeCount);
        dest.writeString(this.masterKnowledgeCount);
        dest.writeString(this.periodValue);
    }

    protected KnowledgeMasteryReport(Parcel in) {
        int tmpPeriodType = in.readInt();
        this.subjectId = in.readString();
        this.homeworkCount = in.readString();
        this.practiceCount = in.readString();
        this.questionCount = in.readString();
        this.allKnowledgeCount = in.readString();
        this.weakKnowledgeCount = in.readString();
        this.knowKnowledgeCount = in.readString();
        this.familiarKnowledgeCount = in.readString();
        this.masterKnowledgeCount = in.readString();
        this.periodValue = in.readString();
    }

    public static final Creator<KnowledgeMasteryReport> CREATOR = new Creator<KnowledgeMasteryReport>() {
        @Override
        public KnowledgeMasteryReport createFromParcel(Parcel source) {
            return new KnowledgeMasteryReport(source);
        }

        @Override
        public KnowledgeMasteryReport[] newArray(int size) {
            return new KnowledgeMasteryReport[size];
        }
    };
}
