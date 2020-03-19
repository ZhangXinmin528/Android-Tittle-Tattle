package com.zxm.coding.lib_database.local.model;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2019/2/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class Student implements Serializable {
    private int id;
    private String name;
    private String province;

    public Student() {
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                '}';
    }
}
