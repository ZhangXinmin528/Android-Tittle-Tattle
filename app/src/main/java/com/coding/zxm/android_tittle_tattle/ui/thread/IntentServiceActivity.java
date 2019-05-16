package com.coding.zxm.android_tittle_tattle.ui.thread;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.service.ProgressService;
import com.coding.zxm.libutil.DisplayUtil;
import com.coding.zxm.libcore.ui.BaseActivity;

import static com.coding.zxm.android_tittle_tattle.service.ProgressService.ACTION_PROGRESS;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 * IntentService
 */
public class IntentServiceActivity extends BaseActivity {

    private ProgressBar mProgressBar;
    private TextView mCurrProgress;
    private ProgressReceiver mProgressReceiver;

    @Override
    protected Object setLayout() {
        return R.layout.activity_intent_service;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
        if (!TextUtils.isEmpty(label)) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(label);
            }
        }

        mProgressReceiver = new ProgressReceiver();
        final IntentFilter update = new IntentFilter();
        update.addAction(ACTION_PROGRESS);
        registerReceiver(mProgressReceiver, update);

    }

    @Override
    protected void initViews() {
        mProgressBar = findViewById(R.id.progress_intent_service);
        mProgressBar.setProgress(0);
        mCurrProgress = findViewById(R.id.tv_current_progress);

        findViewById(R.id.btn_intent_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(mContext, ProgressService.class);
                startService(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mProgressReceiver != null) {
            unregisterReceiver(mProgressReceiver);
        }
        super.onDestroy();
    }

    private class ProgressReceiver extends BroadcastReceiver {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final String action = intent.getAction();
                if (!TextUtils.isEmpty(action)) {
                    switch (action) {
                        case ACTION_PROGRESS:
                            final int progress = intent.getIntExtra(ProgressService.PARAMS_PROGRESS, 0);
                            if (progress != 0) {
                                mProgressBar.setProgress(progress);
                                mCurrProgress.setText(progress + "%");
                            }
                            break;
                    }
                }

            }
        }
    }
}
