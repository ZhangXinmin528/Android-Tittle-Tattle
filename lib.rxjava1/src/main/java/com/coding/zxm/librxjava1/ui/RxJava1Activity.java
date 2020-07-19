package com.coding.zxm.librxjava1.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.libcore.route.RoutePath;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.librxjava1.R;
import com.coding.zxm.librxjava1.ui.adapter.RxAdapter;
import com.coding.zxm.libutil.DisplayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/2/20.
 * Copyright (c) 2018 . All rights reserved.
 * RxJava1
 */
@Route(path = RoutePath.ROUTE_RXJAVA_ONE)
public class RxJava1Activity extends BaseActivity implements OnItemClickListener {
    private RecyclerView mRecyclerView;
    private RxAdapter mAdapter;
    private List<String> mDataList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_rxjava1;
    }

    @Override
    protected void initParamsAndValues() {
        mDataList = new ArrayList<>();

        final String[] sorts = mResources.getStringArray(R.array.rxjava1_sort_arrays);
        mDataList.addAll(Arrays.asList(sorts));

        mAdapter = new RxAdapter(mDataList);

        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label, R.id.toolbar_rxjava1);
            }
        }
    }

    @Override
    protected void initViews() {
        mRecyclerView = findViewById(R.id.rv_rxjava1_sort);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItmeClickListener(this);
    }

    @Override
    public void onItemClick(@NonNull RecyclerView.Adapter adapter, @NonNull View view, int position) {
        if (adapter instanceof RxAdapter) {
            final String item = ((RxAdapter) adapter).getItem(position);
            if (!TextUtils.isEmpty(item)) {
                dispatchRxJavaEvent(mContext, position, item);
            }
        }
    }


    public static void dispatchRxJavaEvent(@NonNull Context context, @IntRange(from = 0) int position,
                                           @NonNull String label) {
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(label)) {
            switch (position) {
                case 0:
                    intent.setClass(context, RxJavaABCActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 1:
                    intent.setClass(context, CreatingOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent.setClass(context, TransferingOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 3:
                    intent.setClass(context, FilteringOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 4:
                    intent.setClass(context, CombiningOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 5:
                    intent.setClass(context, ErrorHandlingOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
                case 6:
                    intent.setClass(context, UtilityOperatorActivity.class);
                    intent.putExtra(DisplayUtil.PARAMS_LABEL, label);
                    context.startActivity(intent);
                    break;
            }
        }
    }
}
