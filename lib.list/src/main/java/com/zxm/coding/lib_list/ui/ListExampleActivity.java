package com.zxm.coding.lib_list.ui;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zxm.coding.lib_list.R;
import com.zxm.coding.lib_list.adapter.ListExampleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2020/4/21.
 * Copyright (c) 2020 . All rights reserved.
 */
public class ListExampleActivity extends BaseActivity {

    private SmartRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<String> mDataList;
    private ListExampleAdapter mAdapter;

    @Override
    protected Object setLayout() {
        return R.layout.activity_list_example;
    }

    @Override
    protected void initParamsAndValues() {
        mDataList = new ArrayList<>();
        mAdapter = new ListExampleAdapter(mDataList);

        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label, R.id.toolbar_list_exp);
            }
        }
    }

    @Override
    protected void initViews() {
        mRefreshLayout = findViewById(R.id.srl_list);
        mRefreshLayout.setDragRate(0.5f);//显示下拉高度/手指真实下拉高度=阻尼效果
        mRefreshLayout.setReboundDuration(300);//回弹动画时长（毫秒）
        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能
        mRefreshLayout.setEnableOverScrollBounce(true);//是否启用越界回弹
        mRefreshLayout.setEnableScrollContentWhenLoaded(true);//是否在加载完成时滚动列表显示新的内容
        mRefreshLayout.setEnableHeaderTranslationContent(true);//是否下拉Header的时候向下平移列表或者内容
        mRefreshLayout.setEnableFooterTranslationContent(true);//是否上拉Footer的时候向上平移列表或者内容
        mRefreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        mRefreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
        mRefreshLayout.autoRefresh(1000);

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData(true);
                        mRefreshLayout.finishRefresh();
                    }
                }, 2000);

            }
        });

        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData(false);

                        mRefreshLayout.finishLoadMore();
                    }
                }, 2000);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerview_list);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initData(boolean isRefresh) {
        if (isRefresh) {
            if (!mDataList.isEmpty()) {
                mDataList.clear();
                mAdapter.notifyDataSetChanged();
            }
        }
        final int size = mDataList.size();
        for (int i = size; i < size + 20; i++) {
            mDataList.add("" + i);
        }
        mAdapter.notifyDataSetChanged();
    }
}
