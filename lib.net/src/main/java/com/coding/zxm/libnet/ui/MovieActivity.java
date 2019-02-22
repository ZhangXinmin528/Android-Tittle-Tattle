package com.coding.zxm.libnet.ui;

import android.support.v7.widget.RecyclerView;

import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libnet.R;
import com.coding.zxm.libnet.model.MovieEntity;

import java.util.List;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public class MovieActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<MovieEntity> mDataList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_movie;
    }

    @Override
    protected void initParamsAndValues() {

    }

    @Override
    protected void initViews() {

    }
}
