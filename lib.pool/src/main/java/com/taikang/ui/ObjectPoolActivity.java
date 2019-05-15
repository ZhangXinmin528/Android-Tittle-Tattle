package com.taikang.ui;

import android.util.Log;
import android.view.View;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.taikang.lib_pool.R;
import com.taikang.model.Student;

/**
 * Created by ZhangXinmin on 2019/5/15.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ObjectPoolActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = ObjectPoolActivity.class.getSimpleName();

    @Override
    protected Object setLayout() {
        return R.layout.activity_object_pool;
    }

    @Override
    protected void initParamsAndValues() {

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
        } else if (viewId == R.id.btn_use_pool) {
            testUsePool();
        }
    }

    private void testUsePool() {
        for (int i = 0; i < 100; i++) {
            try {
                final Student student = Student.obtain();
                if (student != null) {
                    student.setId(i);
                    student.setName(student.getClass().getSimpleName() + ".." + i);
                    if (i < 5)
                        student.recycle();
                    Log.e(TAG, "PoolSize : " + Student.getPool().getPoolSize());
                    Log.e(TAG, student.toString());
                }
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
