package com.zxm.coding.lib_stacking.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class MovieEntity implements Serializable {
    private String id;
    //电影网页
    private String alt;
    //图集
    private Images images;
    //上映日期
    private String year;
    //导演
    private List<Staff> directors;
    //类型
    private String subtype;
    //original_title
    private String original_title;
    //演员
    private List<Staff> casts;
    //中文名
    private String title;
    //电影类型
    private List<String> genres;
    //评分
    private Rate rating;

    public MovieEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<Staff> getCasts() {
        return casts;
    }

    public void setCasts(List<Staff> casts) {
        this.casts = casts;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Rate getRating() {
        return rating;
    }

    public void setRating(Rate rating) {
        this.rating = rating;
    }

    public List<Staff> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Staff> directors) {
        this.directors = directors;
    }

    @Override
    public String toString() {
        return "MovieEntity{" +
                "id='" + id + '\'' +
                ", alt='" + alt + '\'' +
                ", images=" + images +
                ", year='" + year + '\'' +
                ", directors=" + directors +
                ", subtype='" + subtype + '\'' +
                ", original_title='" + original_title + '\'' +
                ", casts=" + casts +
                ", title='" + title + '\'' +
                ", genres=" + genres +
                ", rating=" + rating +
                '}';
    }
}
