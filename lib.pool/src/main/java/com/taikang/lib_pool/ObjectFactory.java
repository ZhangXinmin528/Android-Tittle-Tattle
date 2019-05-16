package com.taikang.lib_pool;

/**
 * Created by ZhangXinmin on 2019/5/15.
 * Copyright (c) 2018 . All rights reserved.
 * ObjectFactory to create an instance.
 */
public interface ObjectFactory<T> {
    /**
     * Create an instance of T.
     *
     * @return
     */
    T createNew();
}
