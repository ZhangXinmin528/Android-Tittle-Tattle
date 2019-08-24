package com.coding.zxm.lib_xml.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZhangXinmin on 2019/8/24.
 * Copyright (c) 2019 . All rights reserved.
 */
public class QuestionEntity implements Parcelable {
    public static final Creator<QuestionEntity> CREATOR = new Creator<QuestionEntity>() {
        @Override
        public QuestionEntity createFromParcel(Parcel in) {
            return new QuestionEntity(in);
        }

        @Override
        public QuestionEntity[] newArray(int size) {
            return new QuestionEntity[size];
        }
    };
    //序号
    private String id;
    //题干
    private String topic;
    //题目选项
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public QuestionEntity() {
    }

    protected QuestionEntity(Parcel in) {
        id = in.readString();
        topic = in.readString();
        optionA = in.readString();
        optionB = in.readString();
        optionC = in.readString();
        optionD = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    @Override
    public String toString() {
        return "QuestionEntity{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", optionD='" + optionD + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(topic);
        dest.writeString(optionA);
        dest.writeString(optionB);
        dest.writeString(optionC);
        dest.writeString(optionD);
    }
}
