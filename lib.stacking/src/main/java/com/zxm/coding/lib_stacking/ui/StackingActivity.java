package com.zxm.coding.lib_stacking.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.coding.zxm.libcore.Constants;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.zxm.coding.lib_stacking.R;
import com.zxm.coding.lib_stacking.StackingLayoutManager;
import com.zxm.coding.lib_stacking.adapter.StackingAdapter;
import com.zxm.coding.lib_stacking.model.DoubanMovie;
import com.zxm.coding.lib_stacking.model.MovieEntity;
import com.zxm.coding.lib_stacking.service.MoviceService;
import com.zxm.utils.core.log.MLogger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZhangXinmin on 2019/11/12.
 * Copyright (c) 2019 . All rights reserved.
 * 实现卡片堆叠效果
 */
public class StackingActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private List<MovieEntity> mDataList;
    private StackingAdapter mAdapter;
    private StackingLayoutManager mLayoutManager;

    @Override
    protected Object setLayout() {
        return R.layout.activity_stacking;
    }

    @Override
    protected void initParamsAndValues() {
        mDataList = new ArrayList<>();


        final Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label, R.id.toolbar_stacking);
            }
        }

        mLayoutManager = new StackingLayoutManager(mContext);
        mAdapter = new StackingAdapter(mDataList);
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_stacking);
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        getNewMovies();
    }

    @SuppressLint("CheckResult")
    private void getNewMovies() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_DOUBAN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        final MoviceService moviceService = retrofit.create(MoviceService.class);
        moviceService.getNewMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DoubanMovie>() {
                    @Override
                    public void accept(DoubanMovie doubanMovie) throws Exception {
                        if (doubanMovie != null) {
                            mDataList.addAll(doubanMovie.getSubjects());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        MLogger.e(TAG, "onFailure" + throwable.getMessage());
                    }
                });
    }
}
