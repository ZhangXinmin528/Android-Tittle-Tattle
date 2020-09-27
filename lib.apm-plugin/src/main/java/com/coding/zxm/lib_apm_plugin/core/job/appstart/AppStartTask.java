package com.coding.zxm.lib_apm_plugin.core.job.appstart;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.core.storage.IStorage;
import com.coding.zxm.lib_apm_plugin.core.tasks.BaseTask;

/**
 * 应用启动Task
 *
 * @author ArgusAPM Team
 */
public class AppStartTask extends BaseTask {

    @Override
    protected IStorage getStorage() {
        return new AppStartStorage();
    }

    @Override
    public String getTaskName() {
        return ApmTask.TASK_APP_START;
    }
}