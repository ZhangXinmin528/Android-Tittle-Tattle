package com.coding.zxm.lib_pool.pool;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ZhangXinmin on 2019/5/8.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface Pool<T> {

    /**
     * Get an instance from cache pool
     *
     * @return An instance.
     */
    @Nullable
    T get();

    /**
     * Release the instance.
     *
     * @param t An instance.
     * @return The state of operation.
     */
    boolean release(@NonNull T t) throws InterruptedException;

    /**
     * ShutDown the pool and release the resources.
     */
    void shutDown();
}
