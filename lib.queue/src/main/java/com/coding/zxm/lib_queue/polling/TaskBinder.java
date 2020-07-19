package com.coding.zxm.lib_queue.polling;

import android.os.Binder;

import androidx.annotation.NonNull;

/**
 * Created by ZhangXinmin on 2019/11/8.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class TaskBinder extends Binder {

    private TaskService taskService;

    public TaskBinder(@NonNull TaskService taskService) {
        this.taskService = taskService;
    }

    public TaskService getTaskService() {
        return taskService;
    }
}
