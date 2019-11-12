package com.coding.zxm.lib_queue;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.StackView;
import android.widget.TextView;

import com.coding.zxm.lib_queue.model.PhoneEntity;
import com.coding.zxm.lib_queue.polling.OnTaskEventListener;
import com.coding.zxm.lib_queue.polling.TaskSchduler;
import com.coding.zxm.lib_queue.polling.util.TaskDebuger;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.zxm.utils.core.time.TimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by ZhangXinmin on 2019/6/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class LinkeBlockingQueueActivity extends BaseActivity implements
        View.OnClickListener, OnTaskEventListener<PhoneEntity> {

    private static final DateFormat DEFAULT_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);

    private TextView mResultTv;

    private TaskSchduler<PhoneEntity> mTaskSchduler;

    @Override
    protected Object setLayout() {
        return R.layout.activity_linked_blocking_queue;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label);
            }
        }

        TaskDebuger.setLogEnable(getApplicationContext(), true);

        mTaskSchduler = new TaskSchduler<>(mContext);

        mTaskSchduler.setTaskEventListener(this);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_produce).setOnClickListener(this);
        findViewById(R.id.btn_loop).setOnClickListener(this);

        mResultTv = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_produce) {
            producePhone();
        } else if (viewId == R.id.btn_loop) {
            startLoop();
        }
    }

    private void producePhone() {
        if (mTaskSchduler != null) {
            final PhoneEntity entity = new PhoneEntity();
            entity.setCreateTime(TimeUtil.getNowString(DEFAULT_FORMAT));
            entity.setBrand("iPhone");
            mTaskSchduler.addTask(entity);

        }
    }

    private void startLoop() {
        if (mTaskSchduler != null) {
            mTaskSchduler.startLooper();
        }
    }

    @Override
    protected void onDestroy() {
        if (mTaskSchduler != null) {
            mTaskSchduler.onDestory();
        }
        super.onDestroy();
    }

    @Override
    public void onTaskAdded(@NonNull PhoneEntity phoneEntity, boolean state) {
        TaskDebuger.i(TAG, "onTaskAdded .. entity : " + phoneEntity.getCreateTime() + "..state : " + state);
    }

    @Override
    public void onTaskObtain(@NonNull PhoneEntity phoneEntity) {
        TaskDebuger.i(TAG, "onTaskObtain .. entity : " + phoneEntity.getCreateTime());
    }
}
