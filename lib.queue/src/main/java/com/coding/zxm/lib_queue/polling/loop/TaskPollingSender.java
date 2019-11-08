package com.coding.zxm.lib_queue.polling.loop;

/**
 * Created by ZhangXinmin on 2019/5/16.
 * Copyright (c) 2018 . All rights reserved.
 */
public interface TaskPollingSender {

    /**
     * Start the polling task.
     */
    void start();

    /**
     * Resume the polling task.
     */
    void resume();

    /**
     * Pause the polling task.
     */
    void pause();

    /**
     * Stop the polling task.
     */
    void stop();

    /**
     * Schedule the next task in a certain delay mill seconds.
     *
     * @param delayMillseconds A certain delay mill seconds.
     */
    void schedule(long delayMillseconds);
}
