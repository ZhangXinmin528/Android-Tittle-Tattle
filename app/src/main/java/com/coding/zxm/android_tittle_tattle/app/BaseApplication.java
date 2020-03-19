package com.coding.zxm.android_tittle_tattle.app;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.coding.zxm.android_tittle_tattle.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.zxm.utils.core.log.MLogger;

/**
 * Created by ZhangXinmin on 2019/3/7.
 * Copyright (c) 2018 . All rights reserved.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

        initLeakCanary();

        initLogger();
    }

    private void initLogger() {
        final MLogger.LogConfig logConfig =
                new MLogger.LogConfig(this)
                        .setLogSwitch(BuildConfig.DEBUG);
        MLogger.resetLogConfig(logConfig);
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not onStart your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
