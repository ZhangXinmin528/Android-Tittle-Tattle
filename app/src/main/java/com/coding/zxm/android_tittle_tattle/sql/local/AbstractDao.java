package com.coding.zxm.android_tittle_tattle.sql.local;

/**
 * Created by ZhangXinmin on 2019/2/14.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface AbstractDao {

    void insert();

    void delete(String id);

    void update(String id);

    void query();

    void count();
}
