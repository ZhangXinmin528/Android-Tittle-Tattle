package com.coding.zxm.lib_queue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.coding.zxm.lib_queue.polling.model.AbsTask;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public class PhoneEntity extends AbsTask {

    public static final Creator<PhoneEntity> CREATOR = new Creator<PhoneEntity>() {
        @Override
        public PhoneEntity createFromParcel(Parcel in) {
            return new PhoneEntity(in);
        }

        @Override
        public PhoneEntity[] newArray(int size) {
            return new PhoneEntity[size];
        }
    };

    private String brand;
    private String createTime;

    public PhoneEntity() {
    }

    protected PhoneEntity(Parcel in) {
        brand = in.readString();
        createTime = in.readString();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(brand);
        dest.writeString(createTime);
    }

    @Override
    public String toString() {
        return "PhoneEntity{" +
                "brand='" + brand + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    @Override
    protected String getKey() {
        return createTime;
    }
}
