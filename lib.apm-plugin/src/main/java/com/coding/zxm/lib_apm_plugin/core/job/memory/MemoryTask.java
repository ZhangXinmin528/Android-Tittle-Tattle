package com.coding.zxm.lib_apm_plugin.core.job.memory;

import android.os.Debug;

import com.coding.zxm.lib_apm_plugin.Env;
import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.cloud.ArgusApmConfigManager;
import com.coding.zxm.lib_apm_plugin.core.Manager;
import com.coding.zxm.lib_apm_plugin.core.TaskConfig;
import com.coding.zxm.lib_apm_plugin.core.storage.IStorage;
import com.coding.zxm.lib_apm_plugin.core.tasks.BaseTask;
import com.coding.zxm.lib_apm_plugin.util.AsyncThreadTask;
import com.coding.zxm.lib_apm_plugin.util.LogX;
import com.coding.zxm.lib_apm_plugin.util.PreferenceUtils;
import com.coding.zxm.lib_apm_plugin.util.ProcessUtils;

import static com.coding.zxm.lib_apm_plugin.Env.DEBUG;
import static com.coding.zxm.lib_apm_plugin.Env.TAG;

/**
 * 内存收集处理类
 *
 * @author ArgusAPM Team
 */
public class MemoryTask extends BaseTask {
    private static final String SUB_TAG = "MemoryTask";

    //定时任务
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if ((!isCanWork()) || (!checkTime())) {
                return;
            }
            MemoryInfo memoryInfo = getMemoryInfo();

            save(memoryInfo);
            updateLastTime();
            if (DEBUG) {
                AsyncThreadTask.getInstance().executeDelayed(runnable, TaskConfig.TEST_INTERVAL);
            } else {
                AsyncThreadTask.getInstance().executeDelayed(runnable, ArgusApmConfigManager.getInstance().getArgusApmConfigData().funcControl.getMemoryIntervalTime());
            }
        }
    };

    /**
     * 获取当前内存信息
     */
    private MemoryInfo getMemoryInfo() {
        // 注意：这里是耗时和耗CPU的操作，一定要谨慎调用
        Debug.MemoryInfo info = new Debug.MemoryInfo();
        Debug.getMemoryInfo(info);
        if (DEBUG) {
            LogX.d(TAG, SUB_TAG,
                    "当前进程:" + ProcessUtils.getCurrentProcessName()
                            + "，内存getTotalPss:" + info.getTotalPss()
                            + " nativeSize:" + info.nativePss
                            + " dalvikPss:" + info.dalvikPss
                            + " otherPss:" + info.otherPss

            );
        }
        return new MemoryInfo(ProcessUtils.getCurrentProcessName(), info.getTotalPss(), info.dalvikPss, info.nativePss, info.otherPss);
    }

    @Override
    public void start() {
        super.start();
        AsyncThreadTask.executeDelayed(runnable, ArgusApmConfigManager.getInstance().getArgusApmConfigData().funcControl.getMemoryDelayTime() + (int) (Math.round(Math.random() * 1000)));
    }

    @Override
    public String getTaskName() {
        return ApmTask.TASK_MEM;
    }

    @Override
    protected IStorage getStorage() {
        return new MemStorage();
    }

    private boolean checkTime() {
        long diff = System.currentTimeMillis() - PreferenceUtils.getLong(Manager.getContext(), PreferenceUtils.SP_KEY_LAST_MEMORY_TIME, 0);
        boolean res = true;
        if (DEBUG) {
            res = diff > TaskConfig.TEST_INTERVAL;
        } else {
            res = diff > ArgusApmConfigManager.getInstance().getArgusApmConfigData().funcControl.getMemoryIntervalTime();
        }
        return res;
    }

    private void updateLastTime() {
        PreferenceUtils.setLong(Manager.getContext(), PreferenceUtils.SP_KEY_LAST_MEMORY_TIME, System.currentTimeMillis());
    }
}