package com.zxm.coding.libmatrix.plugin;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

import java.lang.ref.SoftReference;

/**
 * Created by ZhangXinmin on 2020/6/10.
 * Copyright (c) 2020 . All rights reserved.
 */
public class TrainingPluginListener extends DefaultPluginListener {
    private final static String TAG = TrainingPluginListener.class.getSimpleName();

    public SoftReference<Context> softReference;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public TrainingPluginListener(Context context) {
        super(context);
        softReference = new SoftReference<>(context);
    }


    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());

    }
}
