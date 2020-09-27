package com.coding.zxm.lib_apm_plugin.core.job.webview;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.core.storage.IStorage;
import com.coding.zxm.lib_apm_plugin.core.tasks.BaseTask;

/**
 * @author ArgusAPM Team
 */
public class WebTask extends BaseTask {
    @Override
    protected IStorage getStorage() {
        return new WebStorage();
    }

    @Override
    public String getTaskName() {
        return ApmTask.TASK_WEBVIEW;
    }
}
