package com.coding.zxm.lib_queue.polling;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.coding.zxm.lib_queue.polling.loop.TaskLooper;
import com.coding.zxm.lib_queue.polling.model.AbsTask;
import com.coding.zxm.lib_queue.polling.util.TaskDebuger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public class TaskService<T extends AbsTask> extends Service {

    private static final String TAG = "TaskService";

    private TaskBinder taskBinder;

    private ExecutorService executor;

    private LinkedBlockingQueue<T> taskQueue;

    //loop
    private TaskLooper<T> taskLooper;

    @Override
    public void onCreate() {
        TaskDebuger.d(TAG, "onCreate()");
        super.onCreate();

        if (taskBinder == null) {
            taskBinder = new TaskBinder(this);
        }

        if (executor == null) {
            executor = Executors.newFixedThreadPool(5);
        }

        if (taskQueue == null) {
            taskQueue = new LinkedBlockingQueue<>();
        }

        if (taskLooper == null) {
            taskLooper = new TaskLooper<>(this);
        }

    }

    public boolean initialize() {
        return taskBinder != null && executor != null && taskQueue != null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        TaskDebuger.d(TAG, "onBind()");
        return taskBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TaskDebuger.d(TAG, "onStartCommand()");
        return Service.START_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();

        return super.onUnbind(intent);
    }

    /**
     * Set a new TaskLooper and start it.
     *
     * @param looper
     */
    public void setTaskLooper(TaskLooper<T> looper) {
        this.taskLooper = looper;
        taskLooper.start();
    }

    /**
     * Start looper.
     */
    public void startLooper() {
        if (taskLooper != null) {
            taskLooper.start();
        }
    }

    /**
     * Set TaskLooper loop interval.
     *
     * @param interval
     */
    public void setLoopInterval(long interval) {
        if (taskLooper != null) {
            taskLooper.setKeepAlive(interval);
        }
    }

    /**
     * Pause looper.
     */
    public void pauseLooper() {
        if (taskLooper != null) {
            if (taskLooper.isPolling()) {
                taskLooper.pause();
            }
        }
    }

    /**
     * Resume looper.
     */
    public void resumeLooper() {
        if (taskLooper != null) {
            if (!taskLooper.isPolling()) {
                taskLooper.resume();
            }
        }
    }

    /**
     * Stop looper.
     */
    public void stopLooper() {
        if (taskLooper != null) {
            taskLooper.stop();
        }
    }

    /**
     * Add a new task to the task queue.
     *
     * @param t
     */
    public void addTask(T t) {
        if (t != null) {
            executor.submit(new TaskProducer<>(taskQueue, t));
        }
    }

    /**
     * Get a task from the queue head element.
     */
    public void getTask() {
        executor.submit(new TaskConsume<>(taskQueue));
    }

    public void clearTaskQueue() {
        taskQueue.clear();
    }

    private void close() {
        if (taskLooper != null) {
            taskLooper.stop();
            taskLooper = null;
        }

        if (executor != null && executor.isShutdown()) {
            executor.shutdown();
            executor = null;
        }

        clearTaskQueue();
    }

    //Produce a product
    private class TaskProducer<T extends AbsTask> implements Runnable {
        private static final String TAG = "TaskProducer";

        private LinkedBlockingQueue<T> queue;
        private T t;

        public TaskProducer(@NonNull LinkedBlockingQueue<T> queue, @NonNull T t) {
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
                TaskDebuger.e(TAG, "Task queue is null~");
                return false;
            }

            if (t == null) {
                TaskDebuger.e(TAG, "The task is null~");
                return false;
            }

            try {
                return queue.offer(t);
            } catch (NullPointerException e) {
                TaskDebuger.e(TAG, "Insert task occur exception : " + e.toString());
                return false;
            }
        }

        private void reportInsertState(boolean state, @NonNull T t) {
            if (t != null) {
                final Intent intent = new Intent(TaskEvent.ACTION_ADD_TASK);
                intent.putExtra(TaskEvent.EXTRA_TASK_ENTITY, t);
                intent.putExtra(TaskEvent.EXTRA_EVENT_STATE, state);
                sendBroadcast(intent);
            }
        }
    }

    //Consume a product
    private class TaskConsume<T extends AbsTask> implements Runnable {

        private LinkedBlockingQueue<T> queue;

        public TaskConsume(LinkedBlockingQueue<T> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            final T t = consume();
            if (t != null) {
                reportPollEvent(t);
                TaskDebuger.i(TAG, "poll a task : " + t.toString());
            } else {
                TaskDebuger.e(TAG, "Task queue is empty~");
            }
        }

        private T consume() {
            if (queue != null) {
                return queue.poll();
            }
            return null;
        }

        private void reportPollEvent(@NonNull T t) {
            if (t != null) {
                final Intent intent = new Intent(TaskEvent.ACTION_POLL_TASK);
                intent.putExtra(TaskEvent.EXTRA_TASK_ENTITY, t);
                sendBroadcast(intent);
            }
        }
    }

}
