package com.coding.zxm.android_tittle_tattle.ui.thread;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.adapter.SortAdapter;
import com.coding.zxm.android_tittle_tattle.util.SortDispatcher;
import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/1/30.
 * Copyright (c) 2018 . All rights reserved.
 */
public class ThreadActivity extends BaseActivity implements OnItemClickListener {
    private RecyclerView mRecyclerView;
    private SortAdapter mAdapter;
    private List<String> mDataList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_sort_list;
    }

    @Override
    protected void initParamsAndValues() {
        mDataList = new ArrayList<>();

        final String[] sorts = mResources.getStringArray(R.array.thread_sort_arrays);
        mDataList.addAll(Arrays.asList(sorts));

        mAdapter = new SortAdapter(mDataList);

        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label);
            }
        }
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_sort);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItmeClickListener(this);
    }

    @Override
    public void onItemClick(@NonNull RecyclerView.Adapter adapter, @NonNull View view, int position) {
        if (adapter instanceof SortAdapter) {
            final String item = ((SortAdapter) adapter).getItem(position);
            if (!TextUtils.isEmpty(item)) {
                SortDispatcher.dispatchThreadEvent(mContext, position, item);
            }
        }
    }


}
