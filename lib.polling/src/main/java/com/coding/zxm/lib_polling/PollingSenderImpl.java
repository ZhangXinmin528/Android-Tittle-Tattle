package com.coding.zxm.lib_polling;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.coding.zxm.libutil.Logger;

/**
 * Created by ZhangXinmin on 2019/5/16.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PollingSenderImpl implements PollingSender {
    private static final String TAG = "PollingSenderImpl";

    private PollingService service;
    private BroadcastReceiver receiver;
    private String action;
    private PendingIntent pendingIntent;
    private int keepAlive;
    private boolean isStarted;

    public PollingSenderImpl(PollingService service) {
        if (service == null) {
            throw new UnsupportedOperationException("PollingService is null!");
        }
        this.service = service;

        init();
    }

    private void init() {
        receiver = new PollingReceiver();
        action = getClass().getName();
        keepAlive = 2000;
        isStarted = false;
    }

    @Override
    public void start() {
        Logger.i(TAG, "start()~");
        service.registerReceiver(receiver, new IntentFilter(action));

        pendingIntent = PendingIntent.getBroadcast(service, 0, new Intent(
                action), PendingIntent.FLAG_UPDATE_CURRENT);

        schedule(keepAlive);
        isStarted = true;
    }

    @Override
    public void stop() {
        Logger.i(TAG, "stop()~");

        if (isStarted) {
            if (pendingIntent != null) {
                // Cancel Alarm.
                final AlarmManager alarmManager = (AlarmManager) service.getSystemService(Service.ALARM_SERVICE);
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }
            }

            isStarted = false;

            if (service != null && receiver != null) {
                service.unregisterReceiver(receiver);
            }
        }
    }

    @Override
    public void schedule(long delayMillseconds) {
        final long nextAlarmMillseconds = System.currentTimeMillis() + delayMillseconds;

        Logger.i(TAG, "schedule()~");

        AlarmManager alarmManager = (AlarmManager) service
                .getSystemService(Service.ALARM_SERVICE);

        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= 23) {
                // In SDK 23 and above, dosing will prevent setExact, setExactAndAllowWhileIdle will force
                // the device to run this task whilst dosing.
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextAlarmMillseconds,
                        pendingIntent);
            } else if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextAlarmMillseconds,
                        pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, nextAlarmMillseconds,
                        pendingIntent);
            }
        }

    }

    private class PollingReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final String action = intent.getAction();
                if (!TextUtils.isEmpty(action)) {
                    Logger.i(TAG, "onReceive()~");
                }
            }
        }
    }
}
