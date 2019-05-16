package com.taikang.ui;

import android.util.Log;
import android.view.View;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.taikang.lib_pool.R;
import com.taikang.lib_pool.StudentFactory;
import com.taikang.lib_pool.SynchronizedPool;
import com.taikang.model.Student;

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
