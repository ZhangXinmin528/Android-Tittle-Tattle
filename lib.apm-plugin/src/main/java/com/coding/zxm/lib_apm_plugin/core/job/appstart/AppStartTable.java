package com.coding.zxm.lib_apm_plugin.core.job.appstart;

import android.text.TextUtils;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.core.storage.DbHelper;
import com.coding.zxm.lib_apm_plugin.core.storage.ITable;

/**
 * 应用启动时间 数据表
 *
 * @author ArgusAPM Team
 */
public class AppStartTable implements ITable {
    @Override
    public String createSql() {
        return TextUtils.concat(
                DbHelper.CREATE_TABLE_PREFIX + getTableName(),
                "(", AppStartInfo.KEY_ID_RECORD, " INTEGER PRIMARY KEY AUTOINCREMENT,",
                AppStartInfo.KEY_TIME_RECORD, DbHelper.DATA_TYPE_INTEGER,
                AppStartInfo.KEY_START_TIME, DbHelper.DATA_TYPE_INTEGER,
                AppStartInfo.KEY_PARAM, DbHelper.DATA_TYPE_TEXT,
                AppStartInfo.KEY_RESERVE_1, DbHelper.DATA_TYPE_TEXT,
                AppStartInfo.KEY_RESERVE_2, DbHelper.DATA_TYPE_TEXT_SUF
        ).toString();
    }

    @Override
    public String getTableName() {
        return ApmTask.TASK_APP_START;
    }
}
