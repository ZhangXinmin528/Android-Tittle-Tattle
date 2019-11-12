package com.zxm.coding.lib_stacking.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/2/25.
 * Copyright (c) 2018 . All rights reserved.
 * 豆瓣电影
 */
public final class DoubanMovie implements Serializable {
    private int count;
    private int start;
    private int total;
    private List<MovieEntity> subjects;
    private String title;

    public DoubanMovie() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<MovieEntity> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<MovieEntity> subjects) {
        this.subjects = subjects;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DoubanMovie{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", subjects=" + subjects +
                ", title='" + title + '\'' +
                '}';
    }
}
