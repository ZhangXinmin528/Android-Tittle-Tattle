package com.coding.zxm.lib_queue.polling.loop;

/**
 * Created by ZhangXinmin on 2019/5/16.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface TaskPollingSender {

    /**
     * Start the polling task.
     */
    void onStart();

    /**
     * Resume the polling task.
     */
    void onResume();

    /**
     * Pause the polling task.
     */
    void onPause();

    /**
     * Stop the polling task.
     */
    void onStop();

    /**
     * Schedule the next task in a certain delay mill seconds.
     *
     * @param delayMillseconds A certain delay mill seconds.
     */
    void schedule(long delayMillseconds);
}
