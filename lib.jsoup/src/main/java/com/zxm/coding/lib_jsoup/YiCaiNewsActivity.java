package com.zxm.coding.lib_jsoup;

import android.content.Intent;
import android.text.TextUtils;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

/**
 * Created by ZhangXinmin on 2020/3/19.
 * Copyright (c) 2020 . All rights reserved.
 */
public class YiCaiNewsActivity extends BaseActivity {
    @Override
    protected Object setLayout() {
        return R.layout.activity_yicai_news;
    }

    @Override
    protected void initParamsAndValues() {
        final Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label);
            }
        }

        YiCaiNewsCrawler.getInstance().getYiCaiNews(YiCaiConstant.YICAI_NEWS_GUSHI);
    }

    @Override
    protected void initViews() {

    }
}
