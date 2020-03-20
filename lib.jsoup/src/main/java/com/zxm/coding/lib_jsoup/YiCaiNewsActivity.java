package com.zxm.coding.lib_jsoup;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZhangXinmin on 2020/3/19.
 * Copyright (c) 2020 . All rights reserved.
 * 数据爬虫：第一财经
 */
public class YiCaiNewsActivity extends BaseActivity {

    private TabLayout mTabLayout;
    private List<String> mTabList;
    private YiCaiTabAdapter mTabAdapter;

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
                setTitle(label, "数据来源：第一财经");
            }
        }

        final String[] arr = getResources().getStringArray(R.array.arrays_yicai_news);
        if (arr != null) {
            mTabList = new ArrayList<>(Arrays.asList(arr));
        }

        mTabAdapter = new YiCaiTabAdapter(getSupportFragmentManager(), mTabList);

        YiCaiNewsCrawler.getInstance().getYiCaiNews(YiCaiConstant.YICAI_NEWS_GUSHI);
    }

    @Override
    protected void initViews() {

        final ViewPager viewPager = findViewById(R.id.viewpager_yicai);
        viewPager.setAdapter(mTabAdapter);
        viewPager.setOffscreenPageLimit(2);
        mTabLayout = findViewById(R.id.tablayout_yicai);
        mTabLayout.setupWithViewPager(viewPager);
    }
}
