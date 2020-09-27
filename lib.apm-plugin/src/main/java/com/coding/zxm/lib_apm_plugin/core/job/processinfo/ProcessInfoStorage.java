package com.coding.zxm.lib_apm_plugin.core.job.processinfo;

import android.database.Cursor;

import com.coding.zxm.lib_apm_plugin.api.ApmTask;
import com.coding.zxm.lib_apm_plugin.core.IInfo;
import com.coding.zxm.lib_apm_plugin.core.Manager;
import com.coding.zxm.lib_apm_plugin.core.storage.TableStorage;
import com.coding.zxm.lib_apm_plugin.util.IOStreamUtils;
import com.coding.zxm.lib_apm_plugin.util.LogX;

import java.util.LinkedList;
import java.util.List;

import static com.coding.zxm.lib_apm_plugin.Env.DEBUG;
import static com.coding.zxm.lib_apm_plugin.Env.TAG;

/**
 * 进程信息存储类
 *
 * @author ArgusAPM Team
 */
public class ProcessInfoStorage extends TableStorage {
    private final String SUB_TAG = "ProcessInfoStorage";

    @Override
    public String getName() {
        return ApmTask.TASK_PROCESS_INFO;
    }

    @Override
    public List<IInfo> readDb(String selection) {
        List<IInfo> infos = new LinkedList<IInfo>();
        Cursor cursor = null;
        try {
            cursor = Manager.getInstance().getConfig().appContext.getContentResolver()
                    .query(getTableUri(), null, selection, null, null);
            if (null == cursor || !cursor.moveToFirst()) {
                IOStreamUtils.closeQuietly(cursor);
                return infos;
            }
            int indexTimeRecord = cursor.getColumnIndex(ProcessInfo.KEY_TIME_RECORD);
            int indexProcessName = cursor.getColumnIndex(ProcessInfo.DBKey.PROCESS_NAME);
            int indexStartCount = cursor.getColumnIndex(ProcessInfo.DBKey.START_COUNT);
            do {
                ProcessInfo processInfo = new ProcessInfo();
                processInfo.processName = cursor.getString(indexProcessName);
                processInfo.startCount = cursor.getInt(indexStartCount);
                processInfo.setRecordTime(cursor.getLong(indexTimeRecord));
                infos.add(processInfo);
            } while (cursor.moveToNext());
        } catch (Exception e) {
            if (DEBUG) {
                LogX.e(TAG, SUB_TAG, getName() + "; " + e.toString());
            }
        } finally {
            IOStreamUtils.closeQuietly(cursor);
        }
        return infos;
    }

}
