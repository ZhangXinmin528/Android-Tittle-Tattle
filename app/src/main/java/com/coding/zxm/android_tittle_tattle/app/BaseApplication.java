package com.coding.zxm.android_tittle_tattle.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by ZhangXinmin on 2019/3/7.
 * Copyright (c) 2018 . All rights reserved.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initLeakCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
