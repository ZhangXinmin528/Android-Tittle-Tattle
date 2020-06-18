package com.coding.zxm.android_tittle_tattle.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.coding.zxm.android_tittle_tattle.R;
import com.coding.zxm.android_tittle_tattle.adapter.SortAdapter;
import com.coding.zxm.android_tittle_tattle.util.SortDispatcher;
import com.coding.zxm.libcore.listender.OnItemClickListener;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.tencent.matrix.AppActiveMatrixDelegate;
import com.tencent.matrix.Matrix;
import com.tencent.matrix.listeners.IAppForeground;
import com.tencent.matrix.plugin.Plugin;
import com.tencent.matrix.trace.TracePlugin;
import com.tencent.matrix.trace.view.FrameDecorator;
import com.tencent.matrix.util.MatrixLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends BaseActivity implements OnItemClickListener,
        IAppForeground {

    private RecyclerView mRecyclerView;
    private SortAdapter mAdapter;
    private List<String> mDataList;
    private FrameDecorator decorator;
    private static final int PERMISSION_REQUEST_CODE = 0x02;

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

        decorator = FrameDecorator.getInstance(this);
        if (!canDrawOverlays()) {
            requestWindowPermission();
        } else {
            decorator.show();
        }

        AppActiveMatrixDelegate.INSTANCE.addListener(this);
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

    private boolean canDrawOverlays() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this);
        } else {
            return true;
        }
    }

    private void requestWindowPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MatrixLog.i(TAG, "requestCode:%s resultCode:%s", requestCode, resultCode);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (canDrawOverlays()) {
                decorator.show();
            } else {
                Toast.makeText(this, "fail to request ACTION_MANAGE_OVERLAY_PERMISSION",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Plugin plugin = Matrix.with().getPluginByClass(TracePlugin.class);
        if (plugin.isPluginStarted()) {
            MatrixLog.i(TAG, "plugin-trace stop");
            plugin.stop();
        }
        if (canDrawOverlays()) {
            decorator.dismiss();
        }
        AppActiveMatrixDelegate.INSTANCE.removeListener(this);
    }

    @Override
    public void onForeground(boolean isForeground) {
        if (!canDrawOverlays()) {
            return;
        }
        if (!isForeground) {
            decorator.dismiss();
        } else {
            decorator.show();
        }
    }
}
