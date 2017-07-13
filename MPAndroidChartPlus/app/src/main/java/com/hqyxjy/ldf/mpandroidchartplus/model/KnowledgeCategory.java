package com.hqyxjy.ldf.mpandroidchartplus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ldf on 17/7/13.
 */

public class KnowledgeCategory implements Parcelable{
    private String categoryId;
    private String categoryName;
    private int highFrequencyKnowledgeCount;
    private int totalKnowledgeCount;
    // 为服务端传递数据，与categoryId同时使用，没有什么其它意义
    private String syllabusCategoryId;

    public KnowledgeCategory() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getHighFrequencyKnowledgeCount() {
        return highFrequencyKnowledgeCount;
    }

    public void setHighFrequencyKnowledgeCount(int highFrequencyKnowledgeCount) {
        this.highFrequencyKnowledgeCount = highFrequencyKnowledgeCount;
    }

    public int getTotalKnowledgeCount() {
        return totalKnowledgeCount;
    }

    public void setTotalKnowledgeCount(int totalKnowledgeCount) {
        this.totalKnowledgeCount = totalKnowledgeCount;
    }

    public String getSyllabusCategoryId() {
        return syllabusCategoryId;
    }

    public void setSyllabusCategoryId(String syllabusCategoryId) {
        this.syllabusCategoryId = syllabusCategoryId;
    }


    @Override
    public String toString() {
        return "KnowledgeCategory{" +
                "categoryId='" + categoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", highFrequencyKnowledgeCount=" + highFrequencyKnowledgeCount +
                ", totalKnowledgeCount=" + totalKnowledgeCount +
                ", syllabusCategoryId='" + syllabusCategoryId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.categoryId);
        dest.writeString(this.categoryName);
        dest.writeInt(this.highFrequencyKnowledgeCount);
        dest.writeInt(this.totalKnowledgeCount);
        dest.writeString(this.syllabusCategoryId);
    }

    protected KnowledgeCategory(Parcel in) {
        this.categoryId = in.readString();
        this.categoryName = in.readString();
        this.highFrequencyKnowledgeCount = in.readInt();
        this.totalKnowledgeCount = in.readInt();
        this.syllabusCategoryId = in.readString();
    }

    public static final Parcelable.Creator<KnowledgeCategory> CREATOR = new Parcelable.Creator<KnowledgeCategory>() {
        @Override
        public KnowledgeCategory createFromParcel(Parcel source) {
            return new KnowledgeCategory(source);
        }

        @Override
        public KnowledgeCategory[] newArray(int size) {
            return new KnowledgeCategory[size];
        }
    };
}
