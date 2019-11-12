package com.zxm.coding.lib_stacking.model;

import java.io.Serializable;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 * 导演
 */
public final class Staff implements Serializable {
    //网址
    private String alt;
    //图集
    private Images avatars;
    private String name;
    private String id;

    public Staff() {
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Images getAvatars() {
        return avatars;
    }

    public void setAvatars(Images avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "alt='" + alt + '\'' +
                ", avatars=" + avatars +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
