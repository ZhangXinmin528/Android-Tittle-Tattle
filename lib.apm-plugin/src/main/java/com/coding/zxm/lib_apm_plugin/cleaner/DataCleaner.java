package com.coding.zxm.lib_apm_plugin.cleaner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.coding.zxm.lib_apm_plugin.Env;
import com.coding.zxm.lib_apm_plugin.cloud.ArgusApmConfigManager;
import com.coding.zxm.lib_apm_plugin.core.storage.DataHelper;
import com.coding.zxm.lib_apm_plugin.util.AsyncThreadTask;
import com.coding.zxm.lib_apm_plugin.util.LogX;
import com.coding.zxm.lib_apm_plugin.util.PreferenceUtils;


/**
 * 数据清理管理类
 * 用于清理数据库数据
 *
 * @author ArgusAPM Team
 */
public class DataCleaner {
    private Context mContext;
    private static final String SUB_TAG = "DataCleaner";

    public DataCleaner(Context c) {
        mContext = c;
    }

    public void create() {
        try {
            mContext.registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
        } catch (Exception e) {
            LogX.d(Env.TAG, SUB_TAG, "create ex : " + Log.getStackTraceString(e));
        }
    }

    public void destroy() {
        try {
            mContext.unregisterReceiver(mReceiver);
        } catch (Exception e) {
            LogX.d(Env.TAG, SUB_TAG, "destroy ex : " + Log.getStackTraceString(e));
        }

    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, Intent.ACTION_USER_PRESENT)) {
                // 清理逻辑，每天只需要执行几次，不需要每次收到解锁屏幕广播都执行
                if (Env.DEBUG) {
                    LogX.d(Env.TAG, SUB_TAG, "recv ACTION_USER_PRESENT");
                }
                long cur = System.currentTimeMillis();
                long inter = cur - getLastTime();
                long need = ArgusApmConfigManager.getInstance().getArgusApmConfigData().cleanInterval;
                if (Env.DEBUG) {
                    LogX.d(Env.TAG, SUB_TAG, "inter = " + inter + " cur = " + cur + " | last = " + getLastTime() + " | need = " + need);
                }
                if (inter >= need) {
                    if (Env.DEBUG) {
                        LogX.d(Env.TAG, SUB_TAG, "inter = " + inter + " clean db");
                    }
                    cleanDb();
                    setLastTime(cur);
                }
            }
        }
    };

    private void setLastTime(long cur) {
        PreferenceUtils.setLong(mContext, PreferenceUtils.SP_KEY_LAST_CLEAN_TIME, cur);
    }

    private long getLastTime() {
        return PreferenceUtils.getLong(mContext, PreferenceUtils.SP_KEY_LAST_CLEAN_TIME, 0);
    }

    private void cleanDb() {
        AsyncThreadTask.executeDelayed(new Runnable() {
            @Override
            public void run() {
                DataHelper.deleteOld();
            }
        }, 5 * 1000);
    }

}
