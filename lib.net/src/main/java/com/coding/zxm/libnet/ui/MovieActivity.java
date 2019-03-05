package com.coding.zxm.libnet.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libnet.R;
import com.coding.zxm.libnet.adapter.MovieAdapter;
import com.coding.zxm.libnet.model.DoubanMovie;
import com.coding.zxm.libnet.model.MovieEntity;
import com.coding.zxm.libnet.service.MoviceService;
import com.coding.zxm.libutil.Logger;
import com.coding.zxm.libweb.fragment.X5WebViewFragment;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZhangXinmin on 2019/2/22.
 * Copyright (c) 2018 . All rights reserved.
 */
public class MovieActivity extends BaseActivity implements OnItemClickListener {
    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private RecyclerView mRecyclerView;
    private List<MovieEntity> mDataList;
    private MovieAdapter mAdapter;

    @Override
    protected Object setLayout() {
        return R.layout.activity_movie;
    }

    @Override
    protected void initParamsAndValues() {

        mDataList = new ArrayList<>();

        mAdapter = new MovieAdapter(mDataList);

        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra("params_label");
            if (!TextUtils.isEmpty(label)) {
                ActionBar actionBar = getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(label);
                }
            }
        }
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_net_movie);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItmeClickListener(this);

//        requestData();

        seniorRequestData();
    }

    /**
     * Retrofit请求网络数据
     */
    private void requestData() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final MoviceService moviceService = retrofit.create(MoviceService.class);

        Call<DoubanMovie> call = moviceService.getTop250(0, 10);

        if (!mDataList.isEmpty()) {
            mDataList.clear();
        }

        //异步请求网络
        call.enqueue(new Callback<DoubanMovie>() {
            @Override
            public void onResponse(Call<DoubanMovie> call, Response<DoubanMovie> response) {
                if (response != null) {
                    final DoubanMovie doubanMovie = response.body();
                    if (doubanMovie != null) {
                        mDataList.addAll(doubanMovie.getSubjects());
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<DoubanMovie> call, Throwable t) {
                Logger.e(TAG, "onFailure" + t.getMessage());
            }
        });
    }

    @SuppressLint("CheckResult")
    private void seniorRequestData() {

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        final MoviceService moviceService = retrofit.create(MoviceService.class);
        moviceService.getDoubanTop(0, 10)
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
                        Logger.e(TAG, "onFailure" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void onItemClick(@NonNull RecyclerView.Adapter adapter, @NonNull View view, int position) {
        final MovieEntity entity = ((MovieAdapter) adapter).getItem(position);
        if (entity != null) {
            Intent web = new Intent(mContext, MovieWebActivity.class);
            web.putExtra(X5WebViewFragment.PARAMS_WEBVIEW_URL, entity.getAlt());
            startActivity(web);
        }

    }
}
