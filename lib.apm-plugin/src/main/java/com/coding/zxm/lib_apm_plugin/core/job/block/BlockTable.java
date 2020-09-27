package com.coding.zxm.lib_apm_plugin.core.job.block;

import android.text.TextUtils;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.core.job.processinfo.ProcessInfo;
import com.coding.zxm.lib_apm_plugin.core.storage.DbHelper;
import com.coding.zxm.lib_apm_plugin.core.storage.ITable;

/**
 * 卡顿模块表
 *
 * @author ArgusAPM Team
 */
public class BlockTable implements ITable {
    @Override
    public String createSql() {
        return TextUtils.concat(
                DbHelper.CREATE_TABLE_PREFIX + getTableName(),
                "(", ProcessInfo.KEY_ID_RECORD, " INTEGER PRIMARY KEY AUTOINCREMENT,",
                BlockInfo.KEY_TIME_RECORD, DbHelper.DATA_TYPE_INTEGER,
                BlockInfo.DBKey.PROCESS_NAME, DbHelper.DATA_TYPE_TEXT,
                BlockInfo.DBKey.BLOCK_STACK, DbHelper.DATA_TYPE_TEXT,
                BlockInfo.DBKey.BLOCK_TIME, DbHelper.DATA_TYPE_INTEGER,
                BlockInfo.KEY_PARAM, DbHelper.DATA_TYPE_TEXT,
                BlockInfo.KEY_RESERVE_1, DbHelper.DATA_TYPE_TEXT,
                BlockInfo.KEY_RESERVE_2, DbHelper.DATA_TYPE_TEXT_SUF
        ).toString();
    }

    @Override
    public String getTableName() {
        return ApmTask.TASK_BLOCK;
    }
}
