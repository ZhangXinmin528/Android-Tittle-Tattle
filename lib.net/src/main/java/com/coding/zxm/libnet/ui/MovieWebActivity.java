package com.coding.zxm.libnet.ui;

import android.content.Intent;

import com.coding.zxm.lib_webview.fragment.X5WebViewFragment;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libnet.R;

/**
 * Created by ZhangXinmin on 2019/2/25.
 * Copyright (c) 2018 . All rights reserved.
 */
public class MovieWebActivity extends BaseActivity {

    private String mUrl;

    @Override
    protected Object setLayout() {
        return R.layout.activity_movie_web;
    }

    @Override
    protected void initParamsAndValues() {

        Intent intent = getIntent();
        if (intent != null) {
            mUrl = intent.getStringExtra(X5WebViewFragment.PARAMS_WEBVIEW_URL);
        }
    }

    @Override
    protected void initViews() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_web, X5WebViewFragment.newInstance(mUrl))
                .commit();
    }
}
