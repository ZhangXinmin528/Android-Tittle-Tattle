package com.coding.zxm.lib_apm_plugin.core.tasks;

import com.coding.zxm.lib_apm_plugin.core.IInfo;

/**
 * ArgusAPM任务接口
 *
 * @author ArgusAPM Team
 */
public interface ITask {
    String getTaskName();

    void start();

    boolean isCanWork();

    void setCanWork(boolean value);

    boolean save(IInfo info);

    void stop();
}