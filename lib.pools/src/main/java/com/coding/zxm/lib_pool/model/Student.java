package com.coding.zxm.lib_pool.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ZhangXinmin on 2019/5/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public class Student implements Parcelable {

    private int id;
    private String name;

    public Student() {

    }

    protected Student(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hashCode='" + hashCode() + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
