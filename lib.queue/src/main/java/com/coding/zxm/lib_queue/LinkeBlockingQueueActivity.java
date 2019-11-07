package com.coding.zxm.lib_queue;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.coding.zxm.lib_queue.model.PhoneEntity;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.zxm.utils.core.time.TimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by ZhangXinmin on 2019/6/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class LinkeBlockingQueueActivity extends BaseActivity implements
        View.OnClickListener {

    private static final DateFormat DEFAULT_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA);

    private TextView mResultTv;
    private LinkedBlockingQueue<PhoneEntity> mDataQueue;

    private ExecutorService mExecutors;

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

        new Handler();

        mDataQueue = new LinkedBlockingQueue<>(5);

        mExecutors = Executors.newFixedThreadPool(5);
    }

    @Override
    protected void initViews() {
        findViewById(R.id.btn_produce).setOnClickListener(this);
        findViewById(R.id.btn_consume).setOnClickListener(this);

        mResultTv = findViewById(R.id.tv_result);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_produce) {
            producePhone();
        } else if (viewId == R.id.btn_consume) {
            consumePhone();
        }
    }

    private void producePhone() {
        mExecutors.submit(new Producer<>(mDataQueue));
    }

    private void consumePhone() {
        mExecutors.submit(new Consumer<>(mDataQueue));
    }

    //生产者
    private final class Producer<T> implements Runnable {

        private BlockingQueue<T> queue;

        public Producer(BlockingQueue<T> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            final boolean state = produce();
            Log.i(TAG, "produce state : " + state);
        }

        public boolean produce() {
            if (queue != null) {
                final PhoneEntity entity = new PhoneEntity();
                entity.setBrand("Apple");
                entity.setCreateTime(TimeUtil.getNowString(DEFAULT_FORMAT));
                try {
                    return mDataQueue.offer(entity);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        }
    }

    //消费者
    private final class Consumer<T> implements Runnable {

        private LinkedBlockingQueue<T> queue;

        public Consumer(LinkedBlockingQueue<T> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            final T t = consume();
            if (t != null) {
                Log.i(TAG, "consume a t : " + t.toString());
            }
        }

        public T consume() {
            if (queue != null) {
                return queue.poll();
            }
            return null;
        }
    }
}
