package com.coding.zxm.lib_pool.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.coding.zxm.lib_pool.R;
import com.coding.zxm.lib_pool.model.Student;
import com.coding.zxm.lib_pool.model.StudentFactory;
import com.coding.zxm.lib_pool.pool.SynchronizedPool;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

/**
 * Created by ZhangXinmin on 2019/5/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ObjectPoolActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ObjectPoolActivity.class.getSimpleName();

    private SynchronizedPool<Student> mPool;
    private int mSize;

    @Override
    protected Object setLayout() {
        return R.layout.activity_object_pool;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label,R.id.toolbar_pool);
            }
        }

        mSize = 1000;
        mPool = new SynchronizedPool<>(5, new StudentFactory());
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_no_pool).setOnClickListener(this);
        findViewById(R.id.btn_use_pool).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_no_pool) {
            testNoPool();
        } else if (viewId == R.id.btn_use_pool) {
            testUsePool();
        }
    }

    private void testNoPool() {
        for (int i = 0; i < mSize; i++) {
            try {
                final Student student = new Student();
                student.setId(i);
                student.setName(student.getClass().getSimpleName() + ".." + i);
                Log.e(TAG, student.toString());
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void testUsePool() {
        for (int i = 0; i < mSize; i++) {
            try {
                final Student student = mPool.get();
                if (student != null) {
                    student.setId(i);
                    student.setName(student.getClass().getSimpleName() + ".." + i);
                    mPool.release(student);
                    Log.e(TAG, "PoolSize : " + mPool.getElementSize());
                    Log.e(TAG, student.toString());
                }
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        mPool.shutDown();
    }
}
