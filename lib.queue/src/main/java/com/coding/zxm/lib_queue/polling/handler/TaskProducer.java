package com.coding.zxm.lib_queue.polling.handler;

import android.support.annotation.NonNull;
import android.util.Log;

import com.coding.zxm.lib_queue.polling.model.AbsTask;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 * Task producer to add task to the {@link java.util.concurrent.BlockingQueue}.
 */
public abstract class TaskProducer<T extends AbsTask> implements Runnable {
    private static final String TAG = "TaskProducer";

    private LinkedBlockingQueue<T> queue;
    private T t;

    public TaskProducer(LinkedBlockingQueue<T> queue, T t) {
        this.queue = queue;
        this.t = t;
    }

    @Override
    public void run() {
        final boolean state = addTask(t);
        reportInsertState(state, t);
    }

    private boolean addTask(T t) {
        if (queue == null) {
            Log.e(TAG, "Task queue is null~");
            return false;
        }

        if (t == null) {
            Log.e(TAG, "The task is null~");
            return false;
        }

        try {
            return queue.offer(t);
        } catch (NullPointerException e) {
            Log.e(TAG, "Insert task occur exception : " + e.toString());
            return false;
        }
    }

    /**
     * Report a task insert state.
     *
     * @param state state
     * @param t     a task
     */
    protected abstract void reportInsertState(boolean state, @NonNull T t);
}
