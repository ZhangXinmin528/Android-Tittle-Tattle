package com.zxm.coding.libmatrix.plugin;

import android.content.Context;

import com.tencent.matrix.plugin.DefaultPluginListener;
import com.tencent.matrix.report.Issue;
import com.tencent.matrix.util.MatrixLog;

/**
 * Created by ZhangXinmin on 2020/6/10.
 * Copyright (c) 2020 . All rights reserved.
 */
public class TrainingPluginListener extends DefaultPluginListener {
    private static String TAG = TrainingPluginListener.class.getSimpleName();

    public TrainingPluginListener(Context context) {
        super(context);
    }


    @Override
    public void onReportIssue(Issue issue) {
        super.onReportIssue(issue);
        MatrixLog.e(TAG, issue.toString());
        //do something...
    }
}
