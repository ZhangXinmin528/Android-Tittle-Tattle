package com.coding.zxm.android_tittle_tattle.app;

import android.app.Application;
import android.content.Intent;

import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.coding.zxm.android_tittle_tattle.BuildConfig;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.batterycanary.BatteryMonitorPlugin;
import com.tencent.matrix.iocanary.IOCanaryPlugin;
import com.tencent.matrix.iocanary.config.IOConfig;
import com.tencent.matrix.resource.ResourcePlugin;
import com.tencent.matrix.resource.config.ResourceConfig;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.config.TraceConfig;
import com.tencent.matrix.util.MatrixLog;
import com.tencent.sqlitelint.SQLiteLint;
import com.tencent.sqlitelint.SQLiteLintPlugin;
import com.tencent.sqlitelint.config.SQLiteLintConfig;
import com.zxm.coding.libmatrix.battery.BatteryCanaryInitHelper;
import com.zxm.coding.libmatrix.plugin.TrainingDynamicConfigImpl;
import com.zxm.coding.libmatrix.plugin.TrainingPluginListener;
import com.zxm.coding.libmatrix.res.ManualDumpActivity;
import com.zxm.utils.core.log.MLogger;

import java.io.File;

/**
 * Created by ZhangXinmin on 2019/3/7.
 * Copyright (c) 2018 . All rights reserved.
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);


        initLogger();

        initMatrix();

        initARouter();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    private void initLogger() {
        final MLogger.LogConfig logConfig =
                new MLogger.LogConfig(this)
                        .setLogSwitch(BuildConfig.DEBUG);
        MLogger.resetLogConfig(logConfig);
    }


    private void initMatrix() {

        TrainingDynamicConfigImpl dynamicConfig = new TrainingDynamicConfigImpl();

        MLogger.i(TAG, "Start Matrix configurations.");

        // Builder. Not necessary while some plugins can be configured separately.
        Matrix.Builder builder = new Matrix.Builder(this);

        // Reporter. Matrix will callback this listener when found issue then emitting it.
        builder.pluginListener(new TrainingPluginListener(this));

        // Configure trace canary.
        TracePlugin tracePlugin = configureTracePlugin(dynamicConfig);
        builder.plugin(tracePlugin);

        // Configure resource canary.
        ResourcePlugin resourcePlugin = configureResourcePlugin(dynamicConfig);
        builder.plugin(resourcePlugin);

        // Configure io canary.
        IOCanaryPlugin ioCanaryPlugin = configureIOCanaryPlugin(dynamicConfig);
        builder.plugin(ioCanaryPlugin);

        // Configure SQLite lint plugin.
        SQLiteLintPlugin sqLiteLintPlugin = configureSQLiteLintPlugin();
        builder.plugin(sqLiteLintPlugin);

        // Configure battery canary.
        BatteryMonitorPlugin batteryMonitorPlugin = configureBatteryCanary();
        builder.plugin(batteryMonitorPlugin);

        Matrix.init(builder.build());

        // Trace Plugin need call start() at the beginning.
        tracePlugin.start();

        MatrixLog.i(TAG, "Matrix configurations done.");

    }

    private TracePlugin configureTracePlugin(TrainingDynamicConfigImpl dynamicConfig) {

        boolean fpsEnable = dynamicConfig.isFPSEnable();
        boolean traceEnable = dynamicConfig.isTraceEnable();
        boolean signalAnrTraceEnable = dynamicConfig.isSignalAnrTraceEnable();

        File traceFileDir = new File(getApplicationContext().getFilesDir(), "matrix_trace");
        if (!traceFileDir.exists()) {
            if (traceFileDir.mkdirs()) {
                MatrixLog.e(TAG, "failed to create traceFileDir");
            }
        }

        File anrTraceFile = new File(traceFileDir, "anr_trace");    // path : /data/user/0/sample.tencent.matrix/files/matrix_trace/anr_trace
        File printTraceFile = new File(traceFileDir, "print_trace");    // path : /data/user/0/sample.tencent.matrix/files/matrix_trace/print_trace

        TraceConfig traceConfig = new TraceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .enableFPS(fpsEnable)
                .enableEvilMethodTrace(traceEnable)
                .enableAnrTrace(traceEnable)
                .enableStartup(traceEnable)
                .enableIdleHandlerTrace(traceEnable)                    // Introduced in Matrix 2.0
                .enableMainThreadPriorityTrace(true)                    // Introduced in Matrix 2.0
                .enableSignalAnrTrace(signalAnrTraceEnable)             // Introduced in Matrix 2.0
                .anrTracePath(anrTraceFile.getAbsolutePath())
                .printTracePath(printTraceFile.getAbsolutePath())
                .splashActivities("sample.tencent.matrix.SplashActivity;")
                .isDebug(true)
                .isDevEnv(false)
                .build();

        //Another way to use SignalAnrTracer separately
        //useSignalAnrTraceAlone(anrTraceFile.getAbsolutePath(), printTraceFile.getAbsolutePath());

        return new TracePlugin(traceConfig);
    }

    private ResourcePlugin configureResourcePlugin(TrainingDynamicConfigImpl dynamicConfig) {
        Intent intent = new Intent();
        ResourceConfig.DumpMode mode = ResourceConfig.DumpMode.MANUAL_DUMP;
        MatrixLog.i(TAG, "Dump Activity Leak Mode=%s", mode);
        intent.setClassName(this.getPackageName(), "com.tencent.mm.ui.matrix.ManualDumpActivity");
        ResourceConfig resourceConfig = new ResourceConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .setAutoDumpHprofMode(mode)
                .setManualDumpTargetActivity(ManualDumpActivity.class.getName())
                .build();
        ResourcePlugin.activityLeakFixer(this);

        return new ResourcePlugin(resourceConfig);
    }

    private IOCanaryPlugin configureIOCanaryPlugin(TrainingDynamicConfigImpl dynamicConfig) {
        return new IOCanaryPlugin(new IOConfig.Builder()
                .dynamicConfig(dynamicConfig)
                .build());
    }

    private SQLiteLintPlugin configureSQLiteLintPlugin() {
        SQLiteLintConfig sqlLiteConfig;

        /*
         * HOOK模式下，SQLiteLint会自己去获取所有已执行的sql语句及其耗时(by hooking sqlite3_profile)
         * @see 而另一个模式：SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY , 则需要调用 {@link SQLiteLint#notifySqlExecution(String, String, int)}来通知
         * SQLiteLint 需要分析的、已执行的sql语句及其耗时
         * @see TestSQLiteLintActivity#doTest()
         */
        // sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.HOOK);

        sqlLiteConfig = new SQLiteLintConfig(SQLiteLint.SqlExecutionCallbackMode.CUSTOM_NOTIFY);
        return new SQLiteLintPlugin(sqlLiteConfig);
    }

    private BatteryMonitorPlugin configureBatteryCanary() {
        // Configuration of battery plugin is really complicated.
        // See it in BatteryCanaryInitHelper.
        return BatteryCanaryInitHelper.createMonitor();
    }
}
