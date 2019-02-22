package com.coding.zxm.libnet.model;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class Rate implements Serializable {
    private String max;
    private String average;
    private String stars;
    private String min;

    public Rate() {
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "max='" + max + '\'' +
                ", average='" + average + '\'' +
                ", stars='" + stars + '\'' +
                ", min='" + min + '\'' +
                '}';
    }
}
