package com.coding.zxm.android_tittle_tattle.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;

import com.coding.zxm.android_tittle_tattle.util.Logger;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public final class ProgressService extends IntentService {

    public static final String PARAMS_PROGRESS = "params_progress";
    public static final String ACTION_PROGRESS = "com.zxm.coding.progress";

    private static final String TAG = ProgressService.class.getSimpleName();


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public ProgressService() {
        super(TAG);
//        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "ProgressService..onCreate()");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Logger.d(TAG, "ProgressService..onHandleIntent()");
        int progress = 0;
        while (progress < 100) {
            progress += 5;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            updataProgress(progress);

        }
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Logger.d(TAG, "ProgressService..onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Logger.d(TAG, "ProgressService..onDestroy()");
        super.onDestroy();
    }

    /**
     * Update progress
     *
     * @param progress
     */
    private void updataProgress(@IntRange(from = 0, to = 100) int progress) {
        Intent intent = new Intent();
        intent.putExtra(PARAMS_PROGRESS, progress);
        intent.setAction(ACTION_PROGRESS);
        sendBroadcast(intent);
    }
}
