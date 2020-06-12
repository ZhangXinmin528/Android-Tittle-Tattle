package com.coding.zxm.lib_polling.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import com.coding.zxm.lib_polling.PollingService;
import com.coding.zxm.lib_polling.R;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

/**
 * Created by ZhangXinmin on 2019/5/16.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PollingActivity extends BaseActivity implements View.OnClickListener {
    private Intent pollingIntent;

    @Override
    protected Object setLayout() {
        return R.layout.activity_polling;
    }

    @Override
    protected void initParamsAndValues() {
        pollingIntent = new Intent(mContext, PollingService.class);

        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label,R.id.toolbar_pooling);
            }
        }
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_start) {
            startService(pollingIntent);
        } else if (viewId == R.id.btn_stop) {
            stopService(pollingIntent);
        }
    }
}
