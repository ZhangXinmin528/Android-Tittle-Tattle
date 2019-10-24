package com.coding.zxm.android_tittle_tattle.ui.rv;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.adapter.RvAdapter;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.coding.zxm.recyclerviewhelper.AbsRecyclerAdapter;
import com.coding.zxm.recyclerviewhelper.listener.OnItemChildClickListener;
import com.coding.zxm.recyclerviewhelper.listener.OnItemClickListener;
import com.zxm.utils.core.log.MLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/9/1.
 * Copyright (c) 2019 . All rights reserved.
 * <p>
 * 用于RecyclerView演示；
 */
public class RvActivity extends BaseActivity implements
        OnItemClickListener, OnItemChildClickListener {

    private RecyclerView mRecyclerView;
    private List<String> mDataList;
    private RvAdapter mAdapter;

    @Override
    protected Object setLayout() {
        return R.layout.activity_rv;
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

        mDataList = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            mDataList.add("index : " + i);
        }

        mAdapter = new RvAdapter(mDataList);
        Log.d(TAG, " size : " + mDataList.size());
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_sample);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mAdapter.setOnItemClickListener(this);
        mAdapter.setItemChildClickListener(this);
    }

    @Override
    public void onItemClick(AbsRecyclerAdapter adapter, View view, int position) {
        Toast.makeText(mContext, "onItemClick..点击了： " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemChildClick(AbsRecyclerAdapter adapter, View view, int position) {
        Toast.makeText(mContext, "onItemChildClick..点击了： " + position, Toast.LENGTH_SHORT).show();
    }
}
