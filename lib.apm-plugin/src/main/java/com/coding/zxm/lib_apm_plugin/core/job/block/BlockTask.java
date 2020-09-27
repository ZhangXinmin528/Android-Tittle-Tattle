package com.coding.zxm.lib_apm_plugin.core.job.block;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Printer;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.cloud.ArgusApmConfigManager;
import com.coding.zxm.lib_apm_plugin.core.Manager;
import com.coding.zxm.lib_apm_plugin.core.storage.IStorage;
import com.coding.zxm.lib_apm_plugin.core.tasks.BaseTask;
import com.coding.zxm.lib_apm_plugin.core.tasks.ITask;
import com.coding.zxm.lib_apm_plugin.util.AsyncThreadTask;
import com.coding.zxm.lib_apm_plugin.util.LogX;

import static com.coding.zxm.lib_apm_plugin.Env.DEBUG;
import static com.coding.zxm.lib_apm_plugin.Env.TAG;

/**
 * 卡顿模块Task
 *
 * @author ArgusAPM Team
 */
public class BlockTask extends BaseTask {
    private final String SUB_TAG = "BlockTask";
    private HandlerThread mBlockThread = new HandlerThread("blockThread");
    private Handler mHandler;

    private Runnable mBlockRunnable = new Runnable() {
        @Override
        public void run() {
            if (!isCanWork()) {
                return;
            }
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            if (DEBUG) {
                LogX.d(TAG, SUB_TAG, sb.toString());
            }
            saveBlockInfo(sb.toString());
        }
    };

    @Override
    public void start() {
        super.start();
        if (!mBlockThread.isAlive()) { //防止多次调用
            mBlockThread.start();
            mHandler = new Handler(mBlockThread.getLooper());
            Looper.getMainLooper().setMessageLogging(new Printer() {

                private static final String START = ">>>>> Dispatching";
                private static final String END = "<<<<< Finished";

                @Override
                public void println(String x) {
                    if (x.startsWith(START)) {
                        startMonitor();
                    }
                    if (x.startsWith(END)) {
                        removeMonitor();
                    }
                }
            });
        }
    }

    public void startMonitor() {
        mHandler.postDelayed(mBlockRunnable, ArgusApmConfigManager.getInstance().getArgusApmConfigData().funcControl.blockMinTime);
    }

    public void removeMonitor() {
        mHandler.removeCallbacks(mBlockRunnable);
    }

    /**
     * 保存卡顿相关信息
     */
    private void saveBlockInfo(final String stack) {
        AsyncThreadTask.execute(new Runnable() {
            @Override
            public void run() {
                BlockInfo info = new BlockInfo();
                info.blockStack = stack;
                info.blockTime = ArgusApmConfigManager.getInstance().getArgusApmConfigData().funcControl.blockMinTime;
                ITask task = Manager.getInstance().getTaskManager().getTask(ApmTask.TASK_BLOCK);
                if (task != null) {
                    task.save(info);
                } else {
                    if (DEBUG) {
                        LogX.d(TAG, "Client", "BlockInfo task == null");
                    }
                }
            }
        });
    }

    @Override
    protected IStorage getStorage() {
        return new BlockStorage();
    }

    @Override
    public String getTaskName() {
        return ApmTask.TASK_BLOCK;
    }
}
