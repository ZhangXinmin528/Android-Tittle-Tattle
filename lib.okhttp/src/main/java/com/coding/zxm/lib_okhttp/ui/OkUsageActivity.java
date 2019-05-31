package com.coding.zxm.lib_okhttp.ui;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.coding.zxm.lib_okhttp.R;
import com.coding.zxm.libcore.ui.BaseActivity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZhangXinmin on 2019/5/28.
 * Copyright (c) 2018 . All rights reserved.
 */
public class OkUsageActivity extends BaseActivity implements View.OnClickListener {

    private ExecutorService mPool;
    private TextView mResultTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_okhttp_usage;
    }

    @Override
    protected void initParamsAndValues() {
        mPool = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void initViews() {
        mResultTv = findViewById(R.id.tv_result);

        findViewById(R.id.btn_do_get).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_do_get) {
            get();
        } else if (viewId == R.id.btn_do_post) {
            post();
        }
    }

    private void post() {
        final String url = "";

    }

    private String doPost(@NonNull String url, @NonNull String json) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        final OkHttpClient client = new OkHttpClient();
        final RequestBody body = RequestBody.create(JSON, json);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private void get() {
        final String url = "https://publicobject.com/helloworld.txt";
        mPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final String result = doGet(url);
                    Log.i(TAG, "result : " + result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String doGet(String url) throws IOException {

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
