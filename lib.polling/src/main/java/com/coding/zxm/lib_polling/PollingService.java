package com.coding.zxm.lib_polling;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.zxm.utils.core.log.MLogger;


/**
 * Created by ZhangXinmin on 2019/5/16.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PollingService extends Service {

    private static final String TAG = "PollingService";

    private PollingSenderImpl pollingSender;

    @Override
    public void onCreate() {
        super.onCreate();
        MLogger.i(TAG, "onCreate()~");
        pollingSender = new PollingSenderImpl(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MLogger.i(TAG, "onStartCommand()~");
        pollingSender.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        MLogger.i(TAG, "onBind()~");
        return null;
    }

    @Override
    public void onDestroy() {
        pollingSender.stop();
        MLogger.i(TAG, "onDestroy()~");
        super.onDestroy();
    }
}
