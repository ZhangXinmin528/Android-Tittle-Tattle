package com.coding.zxm.lib_queue.polling;

import androidx.annotation.NonNull;

import com.coding.zxm.lib_queue.polling.model.AbsTask;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public interface OnTaskEventListener<T extends AbsTask> {

    /**
     * Method called when a new task was inserted to the task queue.
     *
     * @param t     a new task
     * @param state the state of wheather the task insert into the queue
     */
    void onTaskAdded(@NonNull T t, boolean state);

    /**
     * Method called when a task was polled from the task queue.
     *
     * @param t
     */
    void onTaskObtain(@NonNull T t);
}
