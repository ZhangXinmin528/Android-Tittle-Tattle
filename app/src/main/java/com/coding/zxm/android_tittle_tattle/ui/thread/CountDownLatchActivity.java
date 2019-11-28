package com.coding.zxm.android_tittle_tattle.ui.thread;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.service.latch.Attendees;
import com.coding.zxm.android_tittle_tattle.service.latch.VideoController;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZhangXinmin on 2019/2/14.
 * Copyright (c) 2018 . All rights reserved.
 * 多线程并发等待
 */
public class CountDownLatchActivity extends BaseActivity {

    private ExecutorService mControllerPool;
    private ExecutorService mAttendeePool;

    @Override
    protected Object setLayout() {
        return R.layout.activity_count_down_latch;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(label);
                }
            }
        }

        mControllerPool = Executors.newFixedThreadPool(1);
        mAttendeePool = Executors.newFixedThreadPool(10);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_latch_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int size = 10;

                VideoController controller = new VideoController(size);
//                mControllerPool.execute(controller);
                mControllerPool.submit(controller);

                for (int i = 0; i < size; i++) {
//                    mAttendeePool.execute(new Attendees("Attendees-" + i, controller));
                    mAttendeePool.submit(new Attendees("Attendees-" + i, controller));
                }
            }
        });
    }
}
