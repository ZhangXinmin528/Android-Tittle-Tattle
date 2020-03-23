package com.zxm.coding.lib_jsoup;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.coding.zxm.libcore.ui.BaseFragment;
import com.zxm.coding.lib_jsoup.model.YiCaiEntity;
import com.zxm.utils.core.log.MLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2020/3/20.
 * Copyright (c) 2020 . All rights reserved.
 * 第一财经新闻页
 */
public class YiCaiNewsItemFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String PARAMS_TYPE = "news_type";

    private String mNewsType;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<YiCaiEntity> mDataList;
    private YiCaiNewsItemAdapter mAdapter;
    private Handler mHandler;

    public static YiCaiNewsItemFragment newInstance(@NonNull String type) {
        final YiCaiNewsItemFragment fragment = new YiCaiNewsItemFragment();
        if (!TextUtils.isEmpty(type)) {
            final Bundle args = new Bundle();
            args.putString(PARAMS_TYPE, type);
            fragment.setArguments(args);
        }
        return fragment;
    }

    @Override
    protected Object setRootLayout() {
        return R.layout.fragment_yicai_news_item;
    }

    @Override
    public void initParamsAndValues() {
        mHandler = new Handler();

        mDataList = new ArrayList<>();
        mAdapter = new YiCaiNewsItemAdapter(mDataList);

        final Bundle args = getArguments();
        if (args != null && args.containsKey(PARAMS_TYPE)) {
            mNewsType = args.getString(PARAMS_TYPE);
        }

        if (TextUtils.isEmpty(mNewsType)) {
            mNewsType = YiCaiConstant.YICAI_NEWS_GUSHI;
        }
    }

    @Override
    protected void initViews(View rootView) {
        mSwipeRefreshLayout = rootView.findViewById(R.id.swl_yicai_news);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.CYAN, Color.RED);

        mRecyclerView = rootView.findViewById(R.id.rv_yicai_news);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        requestData(false);
    }

    @Override
    public void onRefresh() {
        if (mDataList != null) {
            mDataList.clear();
        }
        requestData(true);
    }

    private void requestData(final boolean isRefresh) {
        final List<YiCaiEntity> temp = YiCaiNewsCrawler.getInstance().getYiCaiNews(mNewsType);
        MLogger.d(tag, "requestData..type : " + mNewsType + "..size : " + temp.size());
        if (temp != null && !temp.isEmpty()) {
            mDataList.addAll(temp);
            if (isRefresh) {
                mAdapter.setNewData(mDataList);
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

    }
}
