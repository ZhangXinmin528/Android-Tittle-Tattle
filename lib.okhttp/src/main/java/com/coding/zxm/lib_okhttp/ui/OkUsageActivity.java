package com.coding.zxm.lib_okhttp.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.coding.zxm.lib_okhttp.R;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label);
            }
        }

        mPool = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void initViews() {
        mResultTv = findViewById(R.id.tv_result);

        findViewById(R.id.btn_do_get).setOnClickListener(this);
        findViewById(R.id.btn_do_get).setOnClickListener(this);
        findViewById(R.id.btn_accessing_headers).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_do_get) {
//            asynGet();
            synGet();
        } else if (viewId == R.id.btn_do_post) {
            postString();
        } else if (viewId == R.id.btn_accessing_headers) {
            accessingHeaders();
        }
    }

    private void post() {

    }

    /**
     * 向服务器发送一个字符串
     * 注意:小于1M
     */
    private void postString() {

        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");

        final OkHttpClient client = new OkHttpClient();
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Log.d(TAG, response.body().string());

        } catch (IOException e) {
            Log.e(TAG, "Catch an exception : " + e.toString());
        }
    }

    /**
     * 异步请求
     */
    private void asynGet() {

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        final Call call = client.newCall(request);
        //
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final ResponseBody body = response.body();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexcepted code " + response);
                }
                //请求头
                final Headers headers = response.headers();
                final int count = headers.size();
                for (int i = 0; i < count; i++) {
                    Log.d(TAG, headers.name(i) + " : " + headers.value(i));
                }
                Log.d(TAG, "result : " + body.string());

            }
        });
    }

    /**
     * 同步请求
     * 不能在主线程中操作
     */
    private void synGet() {

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        mPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Headers
     * 不能在主线程中操作
     */
    private void accessingHeaders() {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        mPool.execute(new Runnable() {
            @Override
            public void run() {

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    System.out.println("Server: " + response.header("Server"));
                    System.out.println("Date: " + response.header("Date"));
                    System.out.println("Vary: " + response.headers("Vary"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
