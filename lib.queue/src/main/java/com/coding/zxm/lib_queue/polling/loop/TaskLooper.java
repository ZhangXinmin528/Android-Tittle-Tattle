package com.coding.zxm.lib_queue.polling.loop;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.coding.zxm.lib_queue.polling.TaskEvent;
import com.coding.zxm.lib_queue.polling.TaskService;
import com.coding.zxm.lib_queue.polling.model.AbsTask;
import com.coding.zxm.lib_queue.polling.util.TaskDebuger;
import com.zxm.utils.core.time.TimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ZhangXinmin on 2019/11/7.
 * Copyright (c) 2019 . All rights reserved.
 */
public final class TaskLooper<T extends AbsTask> implements TaskPollingSender {

    public static final DateFormat DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);

    public static final long TEN_MSEC = 10 * 1000;

    public static final long HALF_MINUTE = 30 * 1000;

    public static final long ONE_MINUTE = 60 * 1000;

    public static final long TWO_MINUTE = 2 * ONE_MINUTE;

    public static final long FIVE_MINUTE = 5 * ONE_MINUTE;

    private static final String TAG = "TaskLooper";

    private TaskService<T> taskService;

    private BroadcastReceiver receiver;

    private PendingIntent pendingIntent;

    private long keepAlive;

    private boolean isPolling;

    private boolean isReceiverRegistered;

    public TaskLooper(@NonNull TaskService<T> taskService) {
        this(taskService, ONE_MINUTE);
    }

    public TaskLooper(@NonNull TaskService<T> taskService, long keepAlive) {
        this.taskService = taskService;
        this.keepAlive = keepAlive;
    }

    @Override
    public void onStart() {
        TaskDebuger.d(TAG, "onStart()~");

        if (receiver == null) {
            receiver = new TaskPollingReceiver();
        }

        if (!isReceiverRegistered) {
            taskService.registerReceiver(receiver, new IntentFilter(TaskEvent.ACTION_POLLING));
            isReceiverRegistered = true;
        }

        if (pendingIntent == null)
            pendingIntent = PendingIntent.getBroadcast(taskService, 0,
                    new Intent(TaskEvent.ACTION_POLLING), PendingIntent.FLAG_UPDATE_CURRENT);

        isPolling = true;

        schedule(TEN_MSEC);

    }

    @Override
    public void onResume() {
        if (!isPolling) {
            schedule(TEN_MSEC);
            isPolling = true;
        }
    }

    @Override
    public void onPause() {
        if (isPolling) {
            isPolling = false;
        }
    }

    @Override
    public void onStop() {
        TaskDebuger.d(TAG, "onStop()~");

        if (isPolling) {
            if (pendingIntent != null) {
                // Cancel Alarm.
                final AlarmManager alarmManager =
                        (AlarmManager) taskService.getSystemService(Service.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }

            isPolling = false;

            if (taskService != null && receiver != null) {
                taskService.unregisterReceiver(receiver);
                isReceiverRegistered = false;
            }
        }
    }

    @Override
    public void schedule(long delayMillseconds) {
        final long nextAlarmMillseconds = System.currentTimeMillis() + delayMillseconds;

        TaskDebuger.d(TAG, "schedule() next alerm at : " + TimeUtil.getNowString(DATE_FORMAT));

        AlarmManager alarmManager = (AlarmManager) taskService
                .getSystemService(Service.ALARM_SERVICE);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                // In SDK 23 and above, dosing will prevent setExact, setExactAndAllowWhileIdle will force
                // the device to run this task whilst dosing.
                TaskDebuger.d(TAG, "Alarm scheule using setExactAndAllowWhileIdle, next : " + delayMillseconds);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmMillseconds,
                        pendingIntent);
            } else if (Build.VERSION.SDK_INT >= 19) {
                TaskDebuger.d(TAG, "Alarm scheule using setExact, next : "
                        + nextAlarmMillseconds);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, delayMillseconds,
                        pendingIntent);
            } else {
                TaskDebuger.d(TAG, "Alarm scheule using set, next : "
                        + nextAlarmMillseconds);
                alarmManager.set(AlarmManager.RTC_WAKEUP, delayMillseconds,
                        pendingIntent);
            }
        }

        if (taskService != null) {
            taskService.getTask();
        }
    }

    /**
     * Loop time interval.
     *
     * @param keepAlive time mills
     */
    public void setKeepAlive(long keepAlive) {
        this.keepAlive = keepAlive;
    }

    public boolean isPolling() {
        return isPolling;
    }

    public boolean isReceiverRegistered() {
        return isReceiverRegistered;
    }

    private class TaskPollingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final String action = intent.getAction();
                if (!TextUtils.isEmpty(action)) {
                    TaskDebuger.d(TAG,
                            "Sending Ping at : " + TimeUtil.getNowString(DATE_FORMAT));

                    //进行下次定时任务
                    if (isPolling) {
                        schedule(keepAlive);
                    }
                }
            }
        }
    }
}
