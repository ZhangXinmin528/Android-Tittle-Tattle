package com.coding.zxm.lib_queue.polling;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.coding.zxm.lib_queue.polling.loop.TaskLooper;
import com.coding.zxm.lib_queue.polling.model.AbsTask;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class TaskSchduler<T extends AbsTask> extends BroadcastReceiver {

    private static final String TAG = "TaskSchduler";

    private Context context;
    private TaskService<T> taskService;
    private boolean isReceiverRegister = false;

    private OnTaskEventListener<T> eventListener;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            taskService = ((TaskBinder) service).getTaskService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            taskService = null;
        }
    };

    public TaskSchduler(Context context) {
        this.context = context;

        initailizeTaskService();
    }

    private void initailizeTaskService() {
        if (taskService == null) {
            final Intent serviceStartIntent = new Intent();
            serviceStartIntent.setClass(context, TaskService.class);
            final Object service = context.startService(serviceStartIntent);
            if (service == null) {
                throw new NullPointerException("Task service is null~");
            }

            if (serviceConnection != null && context != null) {
                final Intent gattIntent = new Intent(context, TaskService.class);
                context.bindService(gattIntent, serviceConnection, BIND_AUTO_CREATE);
                registerReceiver();
            }
        }
    }

    /**
     * Set task event listener.
     *
     * @param listener
     */
    public void setTaskEventListener(OnTaskEventListener<T> listener) {
        this.eventListener = listener;
    }

    /**
     * Add task to the task queue.
     *
     * @param t
     */
    public void addTask(@NonNull T t) {

        if (t == null) {
            throw new NullPointerException("The task is null~");
        }

        if (taskService == null) {
            throw new NullPointerException("TaskService is null~");
        }

        if (!taskService.initialize()) {
            throw new IllegalStateException("TaskService has not initialized~");
        }

        taskService.addTask(t);
    }

    /**
     * Get a task from task queue.
     * <p>
     * Once using this method,if the queue is not empty,a task which obtained from the queue will
     * delivery from {@link OnTaskEventListener#onTaskObtain(AbsTask)}.
     * </p>
     */
    public void getTask() {
        if (taskService == null) {
            throw new NullPointerException("TaskService is null~");
        }

        if (!taskService.initialize()) {
            throw new IllegalStateException("TaskService has not initialized~");
        }

        taskService.getTask();
    }

    /**
     * Set a new TaskLooper and onStart it.
     *
     * @param looper
     */
    public void setTaskLooper(@NonNull TaskLooper<T> looper) {
        if (taskService != null && looper != null) {
            taskService.setTaskLooper(looper);
        }
    }

    /**
     * Set TaskLooper loop interval.
     *
     * @param mills
     */
    public void setLoopInterval(long mills) {
        if (taskService != null) {
            taskService.setLoopInterval(mills);
        }
    }

    /**
     * Start looper.
     */
    public void startLooper() {
        if (taskService != null) {
            taskService.startLooper();
        }
    }

    /**
     * Pause looper.
     */
    public void pauseLooper() {
        if (taskService != null) {
            taskService.pauseLooper();
        }
    }

    /**
     * Resume looper.
     */
    public void resumeLooper() {
        if (taskService != null) {
            taskService.resumeLooper();
        }
    }

    /**
     * Register BroadcastReceiver and add filter.
     */
    private void registerReceiver() {
        if (context != null && !isReceiverRegister) {
            //注册广播
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(TaskEvent.ACTION_ADD_TASK);
            intentFilter.addAction(TaskEvent.ACTION_POLL_TASK);

            context.registerReceiver(this, intentFilter);
            isReceiverRegister = true;
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (!TextUtils.isEmpty(action)) {
                T task = null;
                switch (action) {
                    case TaskEvent.ACTION_ADD_TASK:
                        task = intent.getParcelableExtra(TaskEvent.EXTRA_TASK_ENTITY);
                        final boolean state = intent.getBooleanExtra(TaskEvent.EXTRA_EVENT_STATE, false);
                        if (eventListener != null) {
                            eventListener.onTaskAdded(task, state);
                        }

                        break;
                    case TaskEvent.ACTION_POLL_TASK:
                        task = intent.getParcelableExtra(TaskEvent.EXTRA_TASK_ENTITY);
                        if (eventListener != null) {
                            eventListener.onTaskObtain(task);
                        }
                        break;
                }
            }
        }
    }

    public void onDestory() {
        if (isReceiverRegister) {
            context.unregisterReceiver(this);
        }

        if (taskService != null) {
            if (serviceConnection != null) {
                context.unbindService(serviceConnection);
                serviceConnection = null;
            }
            taskService = null;
        }
    }
}
