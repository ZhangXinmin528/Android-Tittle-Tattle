package com.coding.zxm.lib_apm_plugin.core.job.processinfo;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.core.Manager;
import com.coding.zxm.lib_apm_plugin.core.storage.IStorage;
import com.coding.zxm.lib_apm_plugin.core.tasks.BaseTask;
import com.coding.zxm.lib_apm_plugin.core.tasks.ITask;
import com.coding.zxm.lib_apm_plugin.util.AsyncThreadTask;
import com.coding.zxm.lib_apm_plugin.util.LogX;

import static com.coding.zxm.lib_apm_plugin.Env.DEBUG;
import static com.coding.zxm.lib_apm_plugin.Env.TAG;

/**
 * 进程信息任务类
 *
 * @author ArgusAPM Team
 */
public class ProcessInfoTask extends BaseTask {

    @Override
    public void start() {
        super.start();
        saveProcessInfo();
    }

    /**
     * 保存进程相关信息
     */
    private void saveProcessInfo() {
        AsyncThreadTask.executeDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isCanWork()) {
                    return;
                }
                ProcessInfo info = new ProcessInfo();
                ITask task = Manager.getInstance().getTaskManager().getTask(ApmTask.TASK_PROCESS_INFO);
                if (task != null) {
                    task.save(info);
                } else {
                    if (DEBUG) {
                        LogX.d(TAG, "Client", "ProcessInfo task == null");
                    }
                }
            }
        }, 2000 + (int) (Math.round(Math.random() * 1000)));
    }

    @Override
    protected IStorage getStorage() {
        return new ProcessInfoStorage();
    }

    @Override
    public String getTaskName() {
        return ApmTask.TASK_PROCESS_INFO;
    }
}
