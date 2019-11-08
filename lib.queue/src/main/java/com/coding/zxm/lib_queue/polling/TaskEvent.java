package com.coding.zxm.lib_queue.polling;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class TaskEvent {
    /**
     * Action add task.
     */
    public static final String ACTION_ADD_TASK = "com.coding.zxm.lib_queue.polling.add_task";

    /**
     * Action poll task;
     */
    public static final String ACTION_POLL_TASK = "com.coding.zxm.lib_queue.polling.poll_task";

    /**
     * a task
     */
    public static final String EXTRA_TASK_ENTITY = "com.coding.zxm.lib_queue.polling.task_entity";

    /**
     * Task event state
     */
    public static final String EXTRA_EVENT_STATE = "com.coding.zxm.lib_queue.polling.event_state";

    /**
     * Action polling.
     */
    public static final String ACTION_POLLING = "com.coding.zxm.lib_queue.polling.polling";
}
