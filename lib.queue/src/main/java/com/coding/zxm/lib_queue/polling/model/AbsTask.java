package com.coding.zxm.lib_queue.polling.model;

import android.os.Parcelable;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public abstract class AbsTask implements Parcelable {

    /**
     * unique identification
     */
    protected String key;

    /**
     * get the unique identification
     *
     * @return
     */
   protected abstract String getKey();
}
