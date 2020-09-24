package com.coding.zxm.lib_apm_plugin.core.storage;

import android.text.TextUtils;

import com.coding.zxm.lib_apm_plugin.util.LogX;

import java.util.HashMap;
import java.util.Map;

import static com.coding.zxm.lib_apm_plugin.Env.DEBUG;
import static com.coding.zxm.lib_apm_plugin.Env.TAG;


/**
 * @author ArgusAPM Team
 */
public class DbSwitch {
    static Map<String, Boolean> dbSwitchList;
    private static final String SUB_TAG = "DbSwitch";

    public static void updateSwitch(String taskName, boolean state) {
        synchronized (DbSwitch.class) {
            if (dbSwitchList == null) {
                dbSwitchList = new HashMap<String, Boolean>();
            }
            if (TextUtils.isEmpty(taskName)) {
                return;
            }
            if (DEBUG) {
                LogX.d(TAG, SUB_TAG, "taskName :" + taskName + " state = " + state);
            }
            dbSwitchList.put(taskName, state);
        }
    }

    public static boolean getSwitchState(String taskName) {
        // 默认都为开
        Boolean state = true;
        synchronized (DbSwitch.class) {
            if (TextUtils.isEmpty(taskName)) {
                return state;
            }

            if (dbSwitchList == null) {
                dbSwitchList = new HashMap<String, Boolean>();
                return state;
            }

            state = dbSwitchList.get(taskName);
        }
        if (state == null) {
            return true;
        }
        return state;
    }
}
