package com.zxm.coding.lib_jsoup;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.coding.zxm.libcore.ui.BaseFragment;

/**
 * Created by ZhangXinmin on 2020/3/20.
 * Copyright (c) 2020 . All rights reserved.
 * 第一财经新闻页
 */
public class YiCaiNewsItemFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    @Override
    protected Object setRootLayout() {
        return R.layout.fragment_yicai_news_item;
    }

    @Override
    public void initParamsAndValues() {

    }

    @Override
    protected void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swl_yicai_news);
        mRecyclerView = rootView.findViewById(R.id.rv_yicai_news);

    }
}
