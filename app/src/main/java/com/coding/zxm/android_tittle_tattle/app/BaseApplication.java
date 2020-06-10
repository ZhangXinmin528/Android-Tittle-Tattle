package com.coding.zxm.android_tittle_tattle.app;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.coding.zxm.android_tittle_tattle.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.zxm.coding.libmatrix.plugin.TrainingDynamicConfigImpl;
import com.zxm.coding.libmatrix.plugin.TrainingPluginListener;
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

        initMatrix();
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
        LeakCanary.enableDisplayLeakActivity(this);
        LeakCanary.install(this);
    }

    private void initMatrix() {
        Matrix.Builder builder = new Matrix.Builder(this);
        builder.patchListener(new TrainingPluginListener(this));

        //trace
        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(new TrainingDynamicConfigImpl())
                .enableFPS(true)
                .enableEvilMethodTrace(true)
                .enableAnrTrace(true)
                .enableStartup(true)
                .splashActivities("com.coding.zxm.android_tittle_tattle.ui.HomeActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        TracePlugin tracePlugin = (new TracePlugin(traceConfig));
        builder.plugin(tracePlugin);
        Matrix.init(builder.build());

        //start only startup tracer, close other tracer.
        tracePlugin.start();
    }
}
