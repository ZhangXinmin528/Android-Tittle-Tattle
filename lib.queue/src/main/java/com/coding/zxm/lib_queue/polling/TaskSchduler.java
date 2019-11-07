package com.coding.zxm.lib_queue.polling;

import android.support.annotation.NonNull;

import com.coding.zxm.lib_queue.polling.model.AbsTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class TaskSchduler<T extends AbsTask> {

    private static final String TAG = "TaskSchduler";

    private static TaskSchduler INSTANCE;

    private LinkedBlockingQueue<T> mTaskQueue;
    private ExecutorService mExecutors;

    private TaskSchduler() {
        mTaskQueue = new LinkedBlockingQueue();
        mExecutors = Executors.newFixedThreadPool(5);
    }

    public static synchronized TaskSchduler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TaskSchduler();
        }
        return INSTANCE;
    }


    public void addTask(@NonNull T t) {
        if (mTaskQueue == null) {
            return;
        }

        if (t == null) {
            return;
        }

    }

}
