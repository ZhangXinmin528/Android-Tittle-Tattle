package com.coding.zxm.android_tittle_tattle.ui;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.adapter.SortAdapter;
import com.coding.zxm.android_tittle_tattle.util.SortDispatcher;
import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.libcore.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends BaseActivity implements OnItemClickListener {

    private RecyclerView mRecyclerView;
    private SortAdapter mAdapter;
    private List<String> mDataList;

    @Override
    protected Object setLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void initParamsAndValues() {

        final Toolbar toolbar = findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(getString(R.string.app_name));
        }

        mDataList = new ArrayList<>();
        final String[] sorts = mResources.getStringArray(R.array.home_sort_arrays);
        mDataList.addAll(Arrays.asList(sorts));

        mAdapter = new SortAdapter(mDataList);
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
                SortDispatcher.dispatchHomeEvent(mContext, position, item);
            }
        }
    }
}
