package com.coding.zxm.android_tittle_tattle.ui.thread;

import android.view.View;

import com.coding.zxm.android_tittle_tattle.BaseActivity;
import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.util.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ZhangXinmin on 2019/1/31.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ThreadPoolActivity extends BaseActivity implements View.OnClickListener {
    private ExecutorService mFixedPool;
    private ExecutorService mCachedPool;
    private ExecutorService mSingleExecutor;

    @Override
    protected Object setLayout() {
        return R.layout.activity_thread_pool;
    }

    @Override
    protected void initParamsAndViews() {
        mFixedPool = Executors.newFixedThreadPool(3);
        mCachedPool = Executors.newCachedThreadPool();
        mSingleExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_fixed_pool).setOnClickListener(this);
        findViewById(R.id.brn_cached_pool).setOnClickListener(this);
        findViewById(R.id.brn_single_thread).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fixed_pool:
                addJobToFixedPool();
                break;
            case R.id.brn_cached_pool:
                addJobToCachedPool();
                break;
            case R.id.brn_single_thread:
                addJobToSingleThread();
                break;
        }
    }

    /**
     * SingleThreadExector
     */
    private void addJobToSingleThread() {
        for (int i = 0; i < 50; i++) {
            final int tempIndex = i;
            mSingleExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Logger.e(TAG, "SingleThreadExector..index : " + tempIndex + "..current thread : " + Thread.currentThread().getName());
                }
            });
        }
    }

    /**
     * CachedThreadPool
     */
    private void addJobToCachedPool() {
        for (int i = 0; i < 200; i++) {
            final int tempIndex = i;
            mCachedPool.execute(new Runnable() {
                @Override
                public void run() {
                    Logger.e(TAG, "CachedThreadPool..index : " + tempIndex + "..current thread : " + Thread.currentThread().getName());
                }
            });
        }
    }

    /**
     * FixedThreadPool
     */
    private void addJobToFixedPool() {
        for (int i = 0; i < 200; i++) {
            final int tempIndex = i;
            mFixedPool.execute(new Runnable() {
                @Override
                public void run() {
                    Logger.e(TAG, "FixedThreadPool..index : " + tempIndex + "..current thread : " + Thread.currentThread().getName());
                }
            });
        }
    }
}
